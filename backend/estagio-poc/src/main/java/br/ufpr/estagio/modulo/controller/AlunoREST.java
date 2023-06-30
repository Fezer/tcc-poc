package br.ufpr.estagio.modulo.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import br.ufpr.estagio.modulo.dto.CertificadoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.DadosAuxiliaresDTO;
import br.ufpr.estagio.modulo.dto.DadosBancariosDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.dto.FichaDeAvaliacaoDTO;
import br.ufpr.estagio.modulo.dto.PeriodoRecessoDTO;
import br.ufpr.estagio.modulo.dto.RelatorioDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeRescisaoDTO;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import br.ufpr.estagio.modulo.enums.EnumTipoDocumento;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.DadosAuxiliares;
import br.ufpr.estagio.modulo.model.DadosBancarios;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.model.PeriodoRecesso;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;
import br.ufpr.estagio.modulo.service.DadosAuxiliaresService;
import br.ufpr.estagio.modulo.service.DadosBancariosService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.FichaDeAvaliacaoService;
import br.ufpr.estagio.modulo.service.GeradorDePdfService;
import br.ufpr.estagio.modulo.service.PeriodoRecessoService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/aluno")
public class AlunoREST {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private EstagioService estagioService;

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;

	@Autowired
	private RelatorioDeEstagioService relatorioDeEstagioService;

	@Autowired
	private FichaDeAvaliacaoService fichaDeAvaliacaoService;

	@Autowired
	private CertificadoDeEstagioService certificadoDeEstagioService;

	@Autowired
	private TermoDeRescisaoService termoDeRescisaoService;

	@Autowired
	private PeriodoRecessoService periodoRecessoService;

	@Autowired
	private DadosAuxiliaresService dadosService;

	@Autowired
	private DadosBancariosService dadosBancariosService;

	@Autowired
	private GeradorDePdfService geradorService;

	@Autowired
	private ModelMapper mapper;

	@ResponseBody
	@GetMapping("/{grrAlunoURL}")
	public ResponseEntity<Object> buscarAlunoPorGrr(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(aluno, Aluno.class));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/email/{emailAluno}")
	public ResponseEntity<Object> buscarAlunoPorEmail(@PathVariable String emailAluno,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (emailAluno.isBlank() || emailAluno.isEmpty()) {
				throw new BadRequestException("Email do aluno não informado!");
			} else {
				Optional<Aluno> aluno = alunoService.buscarAlunoPorEmail(emailAluno, accessToken);
				if (aluno.isEmpty()) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(aluno.get(), Aluno.class));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O email informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PostMapping("/{grrAlunoURL}/estagio")
	public ResponseEntity<Object> novoEstagio(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Estagio estagio = alunoService.novoEstagio(aluno);
					EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
					return new ResponseEntity<>(estagioDTO, HttpStatus.CREATED);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@DeleteMapping("/{grrAlunoURL}/estagio/{idEstagio}")
	public ResponseEntity<Object> deletarEstagio(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable Long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Estagio estagio = alunoService.buscarEstagioPorId(idEstagio);
					if (estagio == null) {
						throw new NotFoundException("Estágio não encontrado!");
					} else {
						alunoService.cancelarEstagio(estagio);
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PutMapping("/{grrAlunoURL}/estagio/{idEstagio}")
	public ResponseEntity<Object> cancelarEstagio(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable Long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Estagio estagio = alunoService.buscarEstagioPorId(idEstagio);
					if (estagio == null) {
						throw new NotFoundException("Estágio não encontrado!");
					} else {
						if (estagio.getStatusEstagio() == EnumStatusEstagio.Aprovado) {
							return ResponseEntity.status(HttpStatus.BAD_REQUEST)
									.body(new ErrorResponse("Não é possível cancelar um estágio já aprovado!"));
						} else {
							alunoService.cancelarEstagio(estagio);
						}
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/dadosAuxiliares")
	public ResponseEntity<Object> buscarDadosAuxiliares(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Optional<Aluno> alunoOptional = alunoService.buscarAlunoGrr(grrAlunoURL, accessToken);
				if (alunoOptional.isEmpty()) {
					throw new NotFoundException("Aluno não encontrado!");
				}
				Aluno aluno = alunoOptional.get();
				if (aluno.getDadosAuxiliares() == null) {
					throw new NotFoundException("Aluno não possuí dados auxilares cadastrados!");
				}

				DadosAuxiliares dados = dadosService.buscarDadosAuxiliaresPorId(aluno.getDadosAuxiliares().getId());

				if (dados == null) {
					throw new NotFoundException("Dados não encontrados!");
				}
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(dados, DadosAuxiliares.class));
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PutMapping("/{grrAlunoURL}/dadosAuxiliares")
	public ResponseEntity<Object> atualizarDadosAuxiliares(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @RequestBody DadosAuxiliaresDTO dadosDTO) {
		try {
			Optional<Aluno> aluno = alunoService.buscarAlunoGrr(grrAlunoURL, accessToken);
			if (aluno.isPresent()) {
				Aluno alunoAntigo = aluno.get();
				DadosAuxiliares dadosAtualizado = mapper.map(dadosDTO, DadosAuxiliares.class);

				dadosAtualizado.setId(alunoAntigo.getDadosAuxiliares().getId());
				dadosAtualizado.setAluno(alunoAntigo);

				dadosAtualizado = dadosService.atualizarDados(dadosAtualizado);
				DadosAuxiliaresDTO dadosDTOAtualizado = mapper.map(dadosAtualizado, DadosAuxiliaresDTO.class);

				// return ResponseEntity.ok().body(dadosDTOAtualizado);
				return ResponseEntity.status(HttpStatus.OK).body(dadosDTOAtualizado);
			} else {
				throw new NotFoundException("Os dados não foram encontrados.");
			}

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PostMapping("/{grrAlunoURL}/dadosBancarios")
	public ResponseEntity<Object> criarDadosBancarios(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @RequestBody DadosBancariosDTO dadosBancariosDTO) {
		try {
			Optional<Aluno> aluno = alunoService.buscarAlunoGrr(grrAlunoURL, accessToken);
			if (aluno.isPresent()) {
				Aluno alunoAntigo = aluno.get();
				DadosBancarios dadosBancarios = mapper.map(dadosBancariosDTO, DadosBancarios.class);
				dadosBancarios = dadosBancariosService.criarDadosBancarios(alunoAntigo, dadosBancarios);
				dadosBancariosDTO = mapper.map(dadosBancarios, DadosBancariosDTO.class);
				return ResponseEntity.status(HttpStatus.CREATED).body(dadosBancariosDTO);
				// return new ResponseEntity<>(dadosBancariosDTO, HttpStatus.CREATED);
			} else {
				throw new NotFoundException("Aluno não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/dadosBancarios")
	public ResponseEntity<Object> buscarDadosBancarios(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Optional<Aluno> alunoOptional = alunoService.buscarAlunoGrr(grrAlunoURL, accessToken);
				if (alunoOptional.isEmpty()) {
					throw new NotFoundException("Aluno não encontrado!");
				}
				Aluno aluno = alunoOptional.get();
				if (aluno.getDadosBancarios() == null) {
					throw new NotFoundException("Aluno não possuí dados bancários cadastrados!");
				}
				DadosBancarios dados = new DadosBancarios();
				dados.setId(aluno.getDadosBancarios().getId());
				dados = dadosBancariosService.buscarDadosBancariosPorId(dados.getId());

				if (dados == null) {
					throw new NotFoundException("Dados não encontrados!");
				}
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(dados, DadosBancarios.class));
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PutMapping("/{grrAlunoURL}/dadosBancarios")
	public ResponseEntity<Object> atualizarDadosBancarios(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @RequestBody DadosBancariosDTO dadosDTO) {
		try {
			Optional<Aluno> aluno = alunoService.buscarAlunoGrr(grrAlunoURL, accessToken);
			if (aluno.isPresent()) {
				Aluno alunoAntigo = aluno.get();
				DadosBancarios dadosAtualizado = mapper.map(dadosDTO, DadosBancarios.class);

				dadosAtualizado.setId(alunoAntigo.getDadosBancarios().getId());
				dadosAtualizado.setAluno(alunoAntigo);

				dadosAtualizado = dadosBancariosService.atualizarDados(dadosAtualizado);
				DadosBancariosDTO dadosDTOAtualizado = mapper.map(dadosAtualizado, DadosBancariosDTO.class);

				return ResponseEntity.status(HttpStatus.OK).body(dadosDTOAtualizado);
				// return ResponseEntity.ok().body(dadosDTOAtualizado);
			} else {
				throw new NotFoundException("Aluno não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PutMapping("/{grrAlunoURL}/termo/{idTermo}/solicitarAprovacaoTermo")
	public ResponseEntity<Object> solicitarAprovacaoTermo(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable Long idTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<TermoDeEstagio> termofind = Optional
							.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
					if (termofind == null || termofind.isEmpty()) {
						throw new NotFoundException("Termo não encontrado!");
					} else {
						TermoDeEstagio termo = alunoService.solicitarAprovacaoTermo(termofind);
						return ResponseEntity.status(HttpStatus.CREATED)
								.body(mapper.map(termo, TermoDeEstagioDTO.class));
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/estagio/emPreenchimento")
	public ResponseEntity<Object> buscarEstagioEmPreenchimento(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioEmPreenchimentoPorAluno(aluno);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					// return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
					return ResponseEntity.status(HttpStatus.OK).body(listEstagioDTO);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/estagio/emAprovacao")
	public ResponseEntity<Object> buscarEstagioEmAprovacao(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioEmAprovacaoPorAluno(aluno);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					// return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
					return ResponseEntity.status(HttpStatus.OK).body(listEstagioDTO);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * Neste caso, estágio em progresso é um estágio já aprovado, mas ainda não
	 * iniciado ou um estágio já aprovado e já iniciado ou seja, um estágio em
	 * andamento.
	 **/
	@ResponseBody
	@GetMapping("/{grrAlunoURL}/estagio/emProgresso")
	public ResponseEntity<Object> buscarEstagioEmProgresso(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioEmProgressoPorAluno(aluno);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					// return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
					return ResponseEntity.status(HttpStatus.OK).body(listEstagioDTO);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/estagio")
	public ResponseEntity<Object> buscarEstagioPorStatus(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @RequestParam String statusEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioPorStatusEstagio(aluno, statusEstagio);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/estagio/")
	public ResponseEntity<Object> buscarEstagioPorAluno(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioPorAluno(aluno);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/estagio/termoCompromisso")
	public ResponseEntity<Object> buscarEstagioPorStatusTermoCompromisso(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @RequestParam String statusTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioPorStatusTermoCompromisso(aluno, statusTermo);
					List<EstagioDTO> listEstagioDTO = new ArrayList<EstagioDTO>();
					EstagioDTO estagioDTO = new EstagioDTO();
					if (estagio == null || estagio.isEmpty()) {
						estagioDTO = null;
					} else {
						for (Estagio e : estagio) {
							estagioDTO = estagioService.toEstagioDTO(e);
							listEstagioDTO.add(estagioDTO);
						}
					}
					return new ResponseEntity<>(listEstagioDTO, HttpStatus.OK);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/*
	 * @GetMapping("/{grrAlunoURL}/gerar-termo") public ResponseEntity<byte[]>
	 * gerarTermoPdf(@PathVariable String
	 * grrAlunoURL, @RequestHeader("Authorization") String accessToken) throws
	 * IOException {
	 * 
	 * // TO-DO: Jogar dentro de um try-catch
	 * 
	 * if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) { throw new
	 * BadRequestException("GRR do aluno não informado!"); } else { Aluno aluno =
	 * alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken); if (aluno == null)
	 * { throw new NotFoundException("Aluno não encontrado!"); } else {
	 * List<Estagio> estagio = estagioService.buscarEstagioPorAluno(aluno); if
	 * (estagio.get(0) == null) { throw new
	 * NotFoundException("Estágio não encontrado para o aluno " + aluno.getNome());
	 * } else { byte[] pdf = geradorService.gerarPdf(aluno, estagio.get(0));
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_PDF);
	 * headers.setContentDisposition(ContentDisposition.builder("inline").filename(
	 * "arquivo.pdf").build());
	 * 
	 * return new ResponseEntity<>(pdf, headers, HttpStatus.OK); } } } }
	 */

	@ResponseBody
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/relatorioDeEstagio")
	public ResponseEntity<Object> criarRelatorioDeEstagio(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new BadRequestException("Estágio não pertence ao aluno!");
					} else {
						RelatorioDeEstagio relatorioEstagio = relatorioDeEstagioService
								.criarRelatorioDeEstagio(estagio);
						return ResponseEntity.status(HttpStatus.CREATED)
								.body(mapper.map(relatorioEstagio, RelatorioDeEstagioDTO.class));
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PutMapping("/{grrAlunoURL}/estagio/{idEstagio}/relatorioDeEstagio/{idRelatorio}/solicitarCiencia")
	public ResponseEntity<Object> solicitarCienciaRelatorioDeEstagio(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable long idEstagio,
			@PathVariable long idRelatorio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService
							.buscarRelatorioPorId(idRelatorio);
					if (relatorioFind.isEmpty()) {
						throw new NotFoundException("Relatório não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new BadRequestException("Estágio não pertence ao aluno!");
					} else {
						RelatorioDeEstagio relatorioEstagio = relatorioFind.get();
						if (relatorioEstagio.getEstagio().getId() != estagio.getId()) {
							throw new BadRequestException("Relatório de estágio não pertence ao estágio!");
						} else {
							relatorioEstagio = relatorioDeEstagioService
									.solicitarCienciaRelatorioDeEstagioAluno(relatorioEstagio);
							return ResponseEntity.status(HttpStatus.OK)
									.body(mapper.map(relatorioEstagio, RelatorioDeEstagioDTO.class));
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio ou o ID do relatório não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/relatorioDeEstagio")
	public ResponseEntity<Object> listarRelatorioDeEstagioPorAluno(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService
							.listarRelatorioDeEstagioPorAluno(aluno);
					List<RelatorioDeEstagioDTO> listaRelatoriosDTO = relatorioDeEstagio.stream()
							.map(ap -> mapper.map(ap, RelatorioDeEstagioDTO.class)).collect(Collectors.toList());
					return ResponseEntity.ok().body(listaRelatoriosDTO);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio ou o ID do relatório não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/relatorioDeEstagio/pendenteCiencia")
	public ResponseEntity<Object> listarRelatoriosDeEstagioPorAlunoPendenteCiencia(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService
							.listarRelatoriosDeEstagioPorAlunoPendenteCiencia(aluno);
					List<RelatorioDeEstagioDTO> listaRelatoriosDTO = relatorioDeEstagio.stream()
							.map(ap -> mapper.map(ap, RelatorioDeEstagioDTO.class)).collect(Collectors.toList());
					return ResponseEntity.ok().body(listaRelatoriosDTO);
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio ou o ID do relatório não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/fichaDeAvaliacao")
	public ResponseEntity<Object> criarFichaDeAvaliacao(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new NotFoundException("Estágio não pertence ao aluno!");
					} else {
						if (estagio.getFichaDeAvaliacao() != null) {
							throw new BadRequestException("Já existe uma ficha de avaliação criada pra esse estágio!");
						} else {
							FichaDeAvaliacao fichaDeAvaliacao = fichaDeAvaliacaoService.criarFichaDeAvaliacao(estagio);
							return ResponseEntity.status(HttpStatus.CREATED)
									.body(mapper.map(fichaDeAvaliacao, FichaDeAvaliacaoDTO.class));
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/certificadoDeEstagio")
	public ResponseEntity<Object> solicitarCertificadoDeEstagio(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new NotFoundException("Estágio não pertence ao aluno!");
					} else {
						if (estagio.getFichaDeAvaliacao() == null) {
							throw new BadRequestException("Não existe uma ficha de avaliação criada pra esse estágio!");
						} else {
							if (estagio.getCertificadoDeEstagio() != null) {
								throw new BadRequestException("Já existe um certificado criado pra esse estágio!");
							} else {
								CertificadoDeEstagio certificadoDeEstagio = certificadoDeEstagioService
										.criarCertificadoDeEstagio(estagio);
								return ResponseEntity.status(HttpStatus.CREATED)
										.body(mapper.map(certificadoDeEstagio, CertificadoDeEstagioDTO.class));
							}
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/certificadoDeEstagio")
	public ResponseEntity<Object> listarCertificadosDeEstagio(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> listaEstagios = aluno.getEstagio();
					if (listaEstagios == null || listaEstagios.isEmpty()) {
						throw new NotFoundException("O aluno não possui nenhum estágio!");
					} else {
						List<CertificadoDeEstagio> listaCertificados = alunoService
								.listarCertificadosDeEstagioAluno(aluno);

						return ResponseEntity.status(HttpStatus.OK)
								.body(mapper.map(listaCertificados, new TypeToken<List<CertificadoDeEstagioDTO>>() {
								}.getType()));
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/termoAditivo")
	public ResponseEntity<Object> novoTermoAditivo(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new NotFoundException("Estágio não pertence ao aluno!");
					} else {
						TermoDeEstagio termoAditivo = termoDeEstagioService.novoTermoAditivo(estagio);
						return ResponseEntity.status(HttpStatus.CREATED)
								.body(mapper.map(termoAditivo, TermoDeEstagioDTO.class));
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PutMapping("/{grrAlunoURL}/estagio/{idEstagio}/termoAditivo/{idTermo}/cancelarTermoAditivo")
	public ResponseEntity<Object> cancelarTermoAditivo(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable Long idEstagio,
			@PathVariable Long idTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Estagio estagio = alunoService.buscarEstagioPorId(idEstagio);
					if (estagio == null) {
						throw new NotFoundException("Estágio não encontrado!");
					} else {
						TermoDeEstagio termoAditivo = termoDeEstagioService.buscarPorId(idTermo);
						if (termoAditivo == null) {
							throw new NotFoundException("Termo não encontrado!");
						} else {
							if (estagio.getAluno().getId() != aluno.getId()) {
								throw new BadRequestException("O estágio não pertence ao Aluno!");
							}
							if (termoAditivo.getEstagio().getAluno().getId() != aluno.getId()) {
								throw new BadRequestException("O termo não pertence ao Aluno!");
							}
							if (termoAditivo.getStatusTermo() == EnumStatusTermo.Aprovado) {
								throw new BadRequestException("Não é possível cancelar um termo aditivo já aprovado!");
							} else {
								TermoDeEstagio termo = termoDeEstagioService.cancelarTermoAditivo(termoAditivo);
								return ResponseEntity.status(HttpStatus.OK)
										.body(mapper.map(termo, TermoDeEstagioDTO.class));
							}
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio ou o ID do termo não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoAditivo/emPreenchimento")
	public ResponseEntity<Object> buscarTermoAditivoEmPreenchimentoPorAluno(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeEstagio> listTermoAditivo = termoDeEstagioService
							.listarTermosAditivosEmPreenchimentoPorAluno(aluno);
					return ResponseEntity.status(HttpStatus.OK).body(listTermoAditivo.stream()
							.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoAditivo/")
	public ResponseEntity<Object> listarTermosAditivoPorAluno(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeEstagio> listTermoAditivo = termoDeEstagioService.listarTermosAditivosPorAluno(aluno);
					return ResponseEntity.status(HttpStatus.OK).body(listTermoAditivo.stream()
							.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termo-aditivo/{id}")
	public ResponseEntity<Object> listarTermoAditivoPorId(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken) {
		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					TermoDeEstagio termoAditivo = termoDeEstagioService.listarTermoAditivoPorId(aluno, idLong);

					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAditivo, TermoDeEstagioDTO.class));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do termo não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoAditivo/porStatus")
	public ResponseEntity<Object> listarTermosAditivoPorAlunoPorStatusTermo(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @RequestParam String statusTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeEstagio> listTermoAditivo = termoDeEstagioService
							.listarTermosAditivosPorAlunoPorStatus(aluno, statusTermo);
					return ResponseEntity.status(HttpStatus.OK).body(listTermoAditivo.stream()
							.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoDeCompromisso/emPreenchimento")
	public ResponseEntity<Object> buscarTermoDeCompromissoEmPreenchimentoPorAluno(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeEstagio> listTermoAditivo = termoDeEstagioService
							.listarTermosDeCompromissoEmPreenchimentoPorAluno(aluno);
					return ResponseEntity.status(HttpStatus.OK).body(listTermoAditivo.stream()
							.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoDeCompromisso/")
	public ResponseEntity<Object> listarTermosDeCompromissoPorAluno(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeEstagio> listTermoAditivo = termoDeEstagioService
							.listarTermosDeCompromissoPorAluno(aluno);
					return ResponseEntity.status(HttpStatus.OK).body(listTermoAditivo.stream()
							.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoDeCompromisso/porStatus")
	public ResponseEntity<Object> listarTermosDeCompromissoPorAlunoPorStatusTermo(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @RequestParam String statusTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeEstagio> listTermoAditivo = termoDeEstagioService
							.listarTermosDeCompromissoPorAlunoPorStatus(aluno, statusTermo);
					return ResponseEntity.status(HttpStatus.OK).body(listTermoAditivo.stream()
							.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/termoDeRescisao")
	public ResponseEntity<Object> novoTermoDeRescisao(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable long idEstagio) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new BadRequestException("Estágio não pertence ao aluno!");
					} else {
						TermoDeRescisao termoDeRescisao = termoDeRescisaoService.novoTermoDeRescisao(estagio);
						return ResponseEntity.status(HttpStatus.CREATED)
								.body(mapper.map(termoDeRescisao, TermoDeRescisaoDTO.class));
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PutMapping("/{grrAlunoURL}/estagio/{idEstagio}/termoDeRescisao/{idTermo}/solicitarCiencia")
	public ResponseEntity<Object> solicitarCienciaTermoDeRescisao(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable long idEstagio,
			@PathVariable long idTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idEstagio);
					if (estagioFind.isEmpty()) {
						throw new NotFoundException("Estágio não encontrado!");
					}
					Optional<TermoDeRescisao> termoDeRescisaoFind = termoDeRescisaoService.buscarPorId(idTermo);
					if (termoDeRescisaoFind.isEmpty()) {
						throw new NotFoundException("Termo de rescisão não encontrado!");
					}
					Estagio estagio = estagioFind.get();
					if (estagio.getAluno().getId() != aluno.getId()) {
						throw new BadRequestException("Estágio não pertence ao aluno!");
					} else {
						TermoDeRescisao termoDeRescisao = termoDeRescisaoFind.get();
						if (termoDeRescisao.getEstagio().getId() != estagio.getId()) {
							throw new BadRequestException("Termo de rescisão não pertence ao estágio!");
						} else {
							termoDeRescisao = termoDeRescisaoService
									.solicitarCienciaTermoDeRescisaoAluno(termoDeRescisao);
							return ResponseEntity.status(HttpStatus.OK)
									.body(mapper.map(termoDeRescisao, TermoDeRescisaoDTO.class));
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio ou o ID do termo não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PutMapping("/{grrAlunoURL}/estagio/{idEstagio}/termoDeRescisao/{idTermo}/cancelarTermoDeRescisao")
	public ResponseEntity<Object> cancelarTermoDeRescisao(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable Long idEstagio,
			@PathVariable Long idTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Estagio estagio = alunoService.buscarEstagioPorId(idEstagio);
					if (estagio == null) {
						throw new NotFoundException("Estágio não encontrado!");
					} else {
						Optional<TermoDeRescisao> termoDeRescisao = termoDeRescisaoService.buscarPorId(idTermo);
						if (termoDeRescisao.isEmpty()) {
							throw new NotFoundException("Termo de rescisão não encontrado!");
						} else {
							if (estagio.getAluno().getId() != aluno.getId()) {
								throw new BadRequestException("O estágio não pertence ao Aluno!");
							}
							if (termoDeRescisao.get().getEstagio().getAluno().getId() != aluno.getId()) {
								throw new BadRequestException("O termo não pertence ao Aluno!");
							}
							if (termoDeRescisao.get().isCienciaCOAFE() == true) {
								throw new BadRequestException(
										"Não é possível cancelar um termo de rescisão já aprovado.");
							} else {
								termoDeRescisaoService.cancelarTermoDeRescisao(termoDeRescisao.get());
								return ResponseEntity.status(HttpStatus.OK)
										.body("Sucesso ao cancelar termo de rescisão!");
							}
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do estágio ou o ID do termo não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoDeRescisao/etapaAluno")
	public ResponseEntity<Object> buscarTermoDeRescisaoEtapaAlunoPorAluno(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeRescisao> listTermoDeRescisao = termoDeRescisaoService
							.listarTermoDeRescisaoEtapaAlunoPorAluno(aluno);
					return ResponseEntity.status(HttpStatus.OK).body(listTermoDeRescisao.stream()
							.map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoDeRescisao/")
	public ResponseEntity<Object> listarTermosDeRescisaoPorAluno(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeRescisao> listTermosDeRescisao = termoDeRescisaoService
							.listarTermosDeRescisaoPorAluno(aluno);
					return ResponseEntity.status(HttpStatus.OK).body(listTermosDeRescisao.stream()
							.map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoDeRescisao/etapaCiencia")
	public ResponseEntity<Object> listarTermosDeRescisaoPorAlunoEtapaCiencia(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeRescisao> listTermosDeRescisao = termoDeRescisaoService
							.listarTermosDeRescisaoPorAlunoEtapaCiencia(aluno);
					return ResponseEntity.status(HttpStatus.OK).body(listTermosDeRescisao.stream()
							.map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/termoDeRescisao/finalizados")
	public ResponseEntity<Object> listarTermosDeRescisaoPorAlunoProcessoFinalizado(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<TermoDeRescisao> listTermosDeRescisao = termoDeRescisaoService
							.listarTermosDeRescisaoPorAlunoProcessoFinalizado(aluno);
					return ResponseEntity.status(HttpStatus.OK).body(listTermosDeRescisao.stream()
							.map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O GRR informado para o aluno não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@PostMapping("/{grrAlunoURL}/estagio/{idEstagio}/termoDeRescisao/{idTermo}/periodoRecesso")
	public ResponseEntity<Object> novoPeriodoDeRecesso(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable long idEstagio,
			@PathVariable Long idTermo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Estagio estagio = alunoService.buscarEstagioPorId(idEstagio);
					if (estagio == null) {
						throw new NotFoundException("Estágio não encontrado!");
					} else {
						Optional<TermoDeRescisao> termoDeRescisao = termoDeRescisaoService.buscarPorId(idTermo);
						if (termoDeRescisao.isEmpty()) {
							throw new NotFoundException("Termo de rescisão não encontrado!");
						} else {
							if (estagio.getAluno().getId() != aluno.getId()) {
								throw new BadRequestException("O estágio não pertence ao Aluno!");
							}
							if (termoDeRescisao.get().getEstagio().getAluno().getId() != aluno.getId()) {
								throw new BadRequestException("O termo não pertence ao Aluno!");
							}
							if (termoDeRescisao.get().isCienciaCOAFE() == true) {
								throw new BadRequestException(
										"Não é possível adicionar um período de recesso a um termo de rescisão já aprovado.");
							} else {
								PeriodoRecesso newPeriodoRecesso = periodoRecessoService
										.novoPeriodoRecesso(termoDeRescisao.get());
								return ResponseEntity.status(HttpStatus.CREATED)
										.body(mapper.map(newPeriodoRecesso, PeriodoRecessoDTO.class));
							}
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID do estágio ou ID do termo não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@DeleteMapping("/{grrAlunoURL}/estagio/{idEstagio}/termoDeRescisao/{idTermo}/periodoRecesso/{idPeriodo}")
	public ResponseEntity<Object> removerPeriodoDeRecesso(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken, @PathVariable Long idEstagio,
			@PathVariable Long idTermo, @PathVariable Long idPeriodo) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Estagio estagio = alunoService.buscarEstagioPorId(idEstagio);
					if (estagio == null) {
						throw new NotFoundException("Estágio não encontrado!");
					} else {
						Optional<TermoDeRescisao> termoDeRescisao = termoDeRescisaoService.buscarPorId(idTermo);
						if (termoDeRescisao.isEmpty()) {
							throw new NotFoundException("Termo de rescisão não encontrado!");
						} else {
							Optional<PeriodoRecesso> periodoRecesso = periodoRecessoService.buscarPorId(idPeriodo);
							if (periodoRecesso.isEmpty()) {
								throw new NotFoundException("Período de recesso não encontrado!");
							} else {
								if (estagio.getAluno().getId() != aluno.getId()) {
									throw new BadRequestException("O estágio não pertence ao Aluno!");
								}
								if (termoDeRescisao.get().getEstagio().getAluno().getId() != aluno.getId()) {
									throw new BadRequestException("O termo não pertence ao Aluno!");
								}
								if (termoDeRescisao.get().isCienciaCOAFE() == true) {
									throw new BadRequestException(
											"Não é possível deletar um período de recesso associado a um termo de rescisão já aprovado.");
								} else {
									periodoRecessoService.deletarPeriodoRecesso(periodoRecesso.get());
									return ResponseEntity.status(HttpStatus.OK).body("Sucesso!");
								}
							}
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID do estágio ou ID do termo não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/* Métodos para Aluno gerar arquivos */

	@GetMapping("/{grrAlunoURL}/gerar-termo")
	public ResponseEntity<Object> gerarTermoPdf(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) throws IOException, DocumentException {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					List<Estagio> estagio = estagioService.buscarEstagioPorAluno(aluno);
					if (estagio.get(0) == null) {
						throw new NotFoundException("Estágio não encontrado para o aluno " + aluno.getNome());
					} else {
						byte[] pdf = geradorService.gerarPdf(aluno, estagio.get(0));

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_PDF);
						headers.setContentDisposition(
								ContentDisposition.builder("inline").filename("arquivo.pdf").build());

						return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ResponseBody
	@GetMapping("/{grrAlunoURL}/{id}/gerar-ficha")
	public ResponseEntity<Object> gerarFichaPdf(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken) throws IOException, DocumentException {

		try {
			long idLong = Long.parseLong(id);
			if (idLong < 1L) {
				throw new InvalidFieldException("Id inválido.");
			}
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new InvalidFieldException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<FichaDeAvaliacao> fichaFind = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idLong);
					if (fichaFind == null) {
						throw new NotFoundException("Ficha não encontrada.");
					} else {
						FichaDeAvaliacao ficha = fichaFind.get();

						byte[] pdf = geradorService.gerarPdfFicha(aluno, ficha);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_PDF);
						headers.setContentDisposition(ContentDisposition.builder("inline")
								.filename(aluno.getMatricula() + "-ficha-de-avaliacao-" + ficha.getId() + ".pdf")
								.build());

						return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{grrAlunoURL}/relatorio/{id}/gerar-relatorio")
	public ResponseEntity<Object> gerarRelatorioPdf(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken) throws IOException, DocumentException {
		try {
			long idLong = Long.parseLong(id);
			if (idLong < 1L) {
				throw new InvalidFieldException("Id inválido.");
			}
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new InvalidFieldException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService.buscarRelatorioPorId(idLong);
					if (relatorioFind == null) {
						throw new NotFoundException("Ficha não encontrada.");
					} else {
						RelatorioDeEstagio relatorio = relatorioFind.get();

						byte[] pdf = geradorService.gerarPdfAlunoRelatorioDeEstagio(aluno, relatorio);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_PDF);
						headers.setContentDisposition(ContentDisposition.builder("inline")
								.filename(aluno.getMatricula() + "-relatorio-de-estagio-" + relatorio.getId() + ".pdf")
								.build());

						return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{grrAlunoURL}/termo-aditivo/{id}/gerar-termo-aditivo")
	public ResponseEntity<Object> gerarTermoAditivoPdf(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken) throws IOException, DocumentException {
		try {
			long idLong = Long.parseLong(id);
			if (idLong < 1L) {
				throw new InvalidFieldException("Id inválido.");
			}
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new InvalidFieldException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					TermoDeEstagio termo = termoDeEstagioService.listarTermoAditivoPorId(aluno, idLong);
					if (termo == null) {
						throw new NotFoundException("Ficha não encontrada.");
					} else {
						// TO-DO: Revisar essa implementação.
						if (termo.getEstagio().isEstagioUfpr()) {
							Contratante contratante = new Contratante();
							contratante.setAtivo(true);
							contratante.setCnpj("123123123123");
							contratante.setNome("UFPR");
							contratante.setRepresentanteEmpresa("UFPR");
							contratante.setTipo(EnumTipoContratante.PessoaJuridica);
							termo.setContratante(contratante);
						}
						byte[] pdf = geradorService.gerarPdfAlunoTermoAditivo(termo);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_PDF);
						headers.setContentDisposition(ContentDisposition.builder("inline")
								.filename(aluno.getMatricula() + "-termo-aditivo-" + termo.getId() + ".pdf").build());

						return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{grrAlunoURL}/termo-rescisao/{id}/gerar-termo-rescisao")
	public ResponseEntity<Object> gerarTermoDeRescisaoPdf(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken) throws IOException, DocumentException {
		try {
			long idLong = Long.parseLong(id);
			if (idLong < 1L) {
				throw new InvalidFieldException("Id inválido.");
			}
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new InvalidFieldException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<TermoDeRescisao> termoFind = termoDeRescisaoService.buscarPorId(idLong);
					if (termoFind == null) {
						throw new NotFoundException("Ficha não encontrada.");
					} else {
						TermoDeRescisao termo = termoFind.get();

						byte[] pdf = geradorService.gerarPdfRescisao(aluno, termo);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_PDF);
						headers.setContentDisposition(ContentDisposition.builder("inline")
								.filename(aluno.getMatricula() + "-termo-de-rescisao-" + termo.getId() + ".pdf")
								.build());

						return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{grrAlunoURL}/certificado-de-estagio/{id}/gerar")
	public ResponseEntity<Object> gerarCertificadoDeEstagioPdf(@PathVariable String grrAlunoURL,
			@PathVariable String id,
			@RequestHeader("Authorization") String accessToken) throws IOException, DocumentException {
		try {
			long idLong = Long.parseLong(id);
			if (idLong < 1L) {
				throw new InvalidFieldException("Id inválido.");
			}
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new InvalidFieldException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Optional<CertificadoDeEstagio> certificadoFind = certificadoDeEstagioService
							.buscarCertificadoDeEstagioPorId(idLong);

					if (certificadoFind.isEmpty()) {
						throw new NotFoundException("Certificado de Estágio não encontrado.");
					} else {
						CertificadoDeEstagio certificado = certificadoFind.get();
						byte[] pdf = geradorService.gerarPdfAlunoCertificadoDeEstagio(certificado);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_PDF);
						headers.setContentDisposition(ContentDisposition.builder("inline")
								.filename(aluno.getMatricula() + "-certificado-de-estagio-" + certificado.getId()
										+ ".pdf")
								.build());

						return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/* Métodos para Aluno submeter arquivos */

	@PostMapping("/{grrAlunoURL}/termo-de-compromisso/{id}/upload")
	public ResponseEntity<Object> uploadTermo(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken, @RequestParam("file") MultipartFile file) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new InvalidFieldException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					if (file.isEmpty()) {
						throw new InvalidFieldException("Arquivo não informado!");
					} else {
						if (!file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
							throw new InvalidFieldException(
									"Tipo de arquivo inválido! Apenas arquivos PDF são aceitos.");
						}

						long idLong = Long.parseLong(id);

						if (idLong < 1L)
							throw new InvalidFieldException("Id inválido.");

						TermoDeEstagio termo = termoDeEstagioService.buscarPorId(idLong);

						if (termo == null) {
							throw new NotFoundException("O termo de compromisso não foi encontrado.");
						}
						try {

							Path diretorioAtual = Paths.get("").toAbsolutePath();

							String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

							String nomeArquivo = grrAlunoURL + "-" + EnumTipoDocumento.TermoDeCompromisso + "-"
									+ idLong;
							Path destino = Paths.get(diretorioDestino + nomeArquivo);

							Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

							termoDeEstagioService.uploadTermoDeEstagio(termo, nomeArquivo);

							return ResponseEntity.ok("Termo de compromisso salvo com sucesso!");
						} catch (Exception e) {
							e.printStackTrace();
							throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do termo de compromisso não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/{grrAlunoURL}/ficha-de-avaliacao/{id}/upload/{tipoFicha}")
	public ResponseEntity<Object> uploadFicha(@PathVariable String grrAlunoURL, @PathVariable String id,
			@PathVariable String tipoFicha, @RequestHeader("Authorization") String accessToken,
			@RequestParam("file") MultipartFile file) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					Optional<FichaDeAvaliacao> fichaFind = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idLong);

					if (fichaFind.isEmpty() || fichaFind == null) {
						throw new NotFoundException("A ficha não foi encontrada.");
					}

					FichaDeAvaliacao ficha = fichaFind.get();

					if (file.isEmpty()) {
						throw new InvalidFieldException("Arquivo não informado!");
					} else {
						try {

							Path diretorioAtual = Paths.get("").toAbsolutePath();

							String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

							String nomeArquivo;

							if (tipoFicha.equals(String.valueOf(EnumTipoDocumento.FichaDeAvaliacaoParcial))) {
								nomeArquivo = grrAlunoURL + "-" + EnumTipoDocumento.FichaDeAvaliacaoParcial;
								Path destino = Paths.get(diretorioDestino + nomeArquivo + "-" + idLong);
								Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
							} else if (tipoFicha.equals(String.valueOf(EnumTipoDocumento.FichaDeAvaliacaoFinal))) {
								nomeArquivo = grrAlunoURL + "-" + EnumTipoDocumento.FichaDeAvaliacaoFinal;
								Path destino = Paths.get(diretorioDestino + nomeArquivo + "-" + idLong);
								Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
							} else {
								throw new InvalidFieldException(
										"O tipo de documento deve ser 'FichaDeAvaliacaoParcial' ou 'FichaDeAvaliacaoFinal'.");
							}
							fichaDeAvaliacaoService.uploadFichaDeAvaliacao(ficha, (nomeArquivo + '-' + idLong));

							return ResponseEntity.ok("Ficha de avaliação salva com sucesso!");
						} catch (NotFoundException ex) {
							ErrorResponse response = new ErrorResponse(ex.getMessage());
							return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
						} catch (InvalidFieldException ex) {
							ErrorResponse response = new ErrorResponse(ex.getMessage());
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
						} catch (Exception e) {
							e.printStackTrace();
							throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/{grrAlunoURL}/relatorio-de-estagio/{id}/upload")
	public ResponseEntity<Object> uploadRelatorio(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken, @RequestParam("file") MultipartFile file) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService.buscarRelatorioPorId(idLong);

					if (relatorioFind.isEmpty() || relatorioFind == null) {
						throw new NotFoundException("O relatório de estágio não foi encontrado.");
					}

					RelatorioDeEstagio relatorio = relatorioFind.get();

					if (file.isEmpty()) {
						throw new InvalidFieldException("Arquivo não informado!");
					} else {
						if (!file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
							throw new InvalidFieldException(
									"Tipo de arquivo inválido! Apenas arquivos PDF são aceitos.");
						}
						try {

							Path diretorioAtual = Paths.get("").toAbsolutePath();

							String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

							String nomeArquivo = grrAlunoURL + "-RelatorioDeEstagio" + "-" + idLong;
							Path destino = Paths.get(diretorioDestino + nomeArquivo);
							Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

							relatorioDeEstagioService.uploadRelatorioDeEstagio(relatorio, nomeArquivo);

							return ResponseEntity.ok("Relatório de estágio salvo com sucesso!");
						} catch (NotFoundException ex) {
							ErrorResponse response = new ErrorResponse(ex.getMessage());
							return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
						} catch (InvalidFieldException ex) {
							ErrorResponse response = new ErrorResponse(ex.getMessage());
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
						} catch (Exception e) {
							e.printStackTrace();
							throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/{grrAlunoURL}/termo-aditivo/{id}/upload")
	public ResponseEntity<Object> uploadTermoAditivo(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken, @RequestParam("file") MultipartFile file) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					if (file.isEmpty()) {
						throw new InvalidFieldException("Arquivo não informado!");
					} else {
						long idLong = Long.parseLong(id);

						if (idLong < 1L)
							throw new InvalidFieldException("Id inválido.");

						TermoDeEstagio termo = termoDeEstagioService.buscarPorId(idLong);

						if (termo == null) {
							throw new NotFoundException("O termo aditivo não foi encontrado.");
						}

						if (!file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
							throw new InvalidFieldException(
									"Tipo de arquivo inválido! Apenas arquivos PDF são aceitos.");
						}

						try {

							Path diretorioAtual = Paths.get("").toAbsolutePath();

							String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

							String nomeArquivo = grrAlunoURL + "-TermoAditivo" + "-" + idLong;
							Path destino = Paths.get(diretorioDestino + nomeArquivo);
							Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

							termoDeEstagioService.uploadTermoDeEstagio(termo, nomeArquivo);

							return ResponseEntity.ok("Termo aditivo salvo com sucesso!");
						} catch (NotFoundException ex) {
							ErrorResponse response = new ErrorResponse(ex.getMessage());
							return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
						} catch (InvalidFieldException ex) {
							ErrorResponse response = new ErrorResponse(ex.getMessage());
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
						} catch (Exception e) {
							e.printStackTrace();
							throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/{grrAlunoURL}/termo-de-rescisao/{id}/upload")
	public ResponseEntity<Object> uploadTermoRescisao(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken, @RequestParam("file") MultipartFile file) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					if (file.isEmpty()) {
						throw new InvalidFieldException("Arquivo não informado!");
					} else {
						long idLong = Long.parseLong(id);

						if (idLong < 1L)
							throw new InvalidFieldException("Id inválido.");

						Optional<TermoDeRescisao> termoFind = termoDeRescisaoService.buscarPorId(idLong);

						if (termoFind.isEmpty() || termoFind == null) {
							throw new NotFoundException("O termo de rescisão não foi encontrado.");
						}

						if (!file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
							throw new InvalidFieldException(
									"Tipo de arquivo inválido! Apenas arquivos PDF são aceitos.");
						}

						TermoDeRescisao termo = termoFind.get();
						try {

							Path diretorioAtual = Paths.get("").toAbsolutePath();

							String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

							String nomeArquivo = grrAlunoURL + "-TermoDeRescisao" + "-" + idLong;
							Path destino = Paths.get(diretorioDestino + nomeArquivo);
							Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

							termoDeRescisaoService.uploadTermoDeRescisao(termo, nomeArquivo);

							return ResponseEntity.ok("Termo de rescisão salvo com sucesso!");
						} catch (NotFoundException ex) {
							ErrorResponse response = new ErrorResponse(ex.getMessage());
							return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
						} catch (InvalidFieldException ex) {
							ErrorResponse response = new ErrorResponse(ex.getMessage());
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
						} catch (Exception e) {
							e.printStackTrace();
							throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
						}
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ID não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/* Métodos para Aluno baixar documentos upados por ele mesmo */
	// provavelmente não usado
	@GetMapping("/{grrAlunoURL}/download-termo")
	public ResponseEntity<Resource> downloadTermo(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					Path diretorioAtual = Paths.get("").toAbsolutePath();
					String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

					String nomeArquivo = grrAlunoURL + "-" + EnumTipoDocumento.TermoDeCompromisso;
					Path arquivo = Paths.get(diretorioDestino + nomeArquivo);

					try {
						Resource resource = new UrlResource(arquivo.toUri());

						if (resource.exists()) {
							return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
									.header(HttpHeaders.CONTENT_DISPOSITION,
											"attachment; filename=\"" + resource.getFilename() + "\"")
									.body(resource);
						} else {
							throw new NotFoundException("Arquivo não encontrado!");
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter o arquivo!");
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{grrAlunoURL}/termo-de-compromisso/{id}/download")
	public ResponseEntity<Object> downloadTermoDeCompromissoAluno(@PathVariable String grrAlunoURL,
			@PathVariable String id,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					TermoDeEstagio termo = termoDeEstagioService.buscarPorId(idLong);

					if (termo == null) {
						throw new NotFoundException("O termo de compromisso não foi encontrado.");
					}

					if (termo.isUploadCompromisso() == false)
						throw new BadRequestException("O termo de compromisso ainda não foi submetido.");

					Path diretorioAtual = Paths.get("").toAbsolutePath();
					String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

					int tamanho = termo.getArquivos().size();

					String nomeArquivo = termo.getArquivos().get(tamanho - 1);

					Path arquivo = Paths.get(diretorioDestino + nomeArquivo);

					try {
						Resource resource = new UrlResource(arquivo.toUri());

						if (resource.exists()) {
							return ResponseEntity.ok()
									.contentType(MediaType.APPLICATION_PDF)
									.header(HttpHeaders.CONTENT_DISPOSITION,
											"attachment; filename=\"" + resource.getFilename() + "\"")
									.body(resource);
						} else {
							throw new NotFoundException("Arquivo não encontrado!");
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter o arquivo!");
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do termo de compromisso não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{grrAlunoURL}/termo-aditivo/{id}/download")
	public ResponseEntity<Object> downloadTermoAditivoAluno(@PathVariable String grrAlunoURL, @PathVariable String id,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					TermoDeEstagio termo = termoDeEstagioService.buscarPorId(idLong);

					if (termo == null) {
						throw new NotFoundException("O termo aditivo não foi encontrado.");
					}

					if (termo.isUploadAditivo() == false)
						throw new BadRequestException("O termo aditivo ainda não foi submetido.");

					Path diretorioAtual = Paths.get("").toAbsolutePath();
					String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

					int tamanho = termo.getArquivos().size();

					String nomeArquivo = termo.getArquivos().get(tamanho - 1);

					Path arquivo = Paths.get(diretorioDestino + nomeArquivo);

					try {
						Resource resource = new UrlResource(arquivo.toUri());

						if (resource.exists()) {
							return ResponseEntity.ok()
									.contentType(MediaType.APPLICATION_PDF)
									.header(HttpHeaders.CONTENT_DISPOSITION,
											"attachment; filename=\"" + resource.getFilename() + "\"")
									.body(resource);
						} else {
							throw new NotFoundException("Arquivo não encontrado!");
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter o arquivo!");
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do termo aditivo não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{grrAlunoURL}/termo-de-rescisao/{id}/download")
	public ResponseEntity<Object> downloadTermoDeRescisaoAluno(@PathVariable String grrAlunoURL,
			@PathVariable String id,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					Optional<TermoDeRescisao> termoFind = termoDeRescisaoService.buscarPorId(idLong);

					if (termoFind.isEmpty()) {
						throw new NotFoundException("O termo de rescisão não foi encontrado.");
					}

					TermoDeRescisao termo = termoFind.get();

					if (termo.isUpload() == false)
						throw new BadRequestException("O termo de rescisão ainda não foi submetido.");

					Path diretorioAtual = Paths.get("").toAbsolutePath();
					String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

					// String nomeArquivo = grrAlunoURL + "-" + EnumTipoDocumento.TermoDeRescisao;
					// Path arquivo = Paths.get(diretorioDestino + nomeArquivo);

					int tamanho = termo.getArquivos().size();

					String nomeArquivo = termo.getArquivos().get(tamanho - 1);

					Path arquivo = Paths.get(diretorioDestino + nomeArquivo);

					try {
						Resource resource = new UrlResource(arquivo.toUri());

						if (resource.exists()) {
							return ResponseEntity.ok()
									.contentType(MediaType.APPLICATION_PDF)
									.header(HttpHeaders.CONTENT_DISPOSITION,
											"attachment; filename=\"" + resource.getFilename() + "\"")
									.body(resource);
						} else {
							throw new NotFoundException("Arquivo não encontrado!");
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter o arquivo!");
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do termo de rescisão não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{grrAlunoURL}/ficha-de-avaliacao/{id}/download")
	public ResponseEntity<Object> downloadFichaDeAvaliacaoAluno(@PathVariable String grrAlunoURL,
			@PathVariable String id,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					Optional<FichaDeAvaliacao> fichaFind = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idLong);

					if (fichaFind.isEmpty()) {
						throw new NotFoundException("A ficha de avaliação não foi encontrada.");
					}

					FichaDeAvaliacao ficha = fichaFind.get();

					if (ficha.isUpload() == false)
						throw new BadRequestException("A ficha de avaliação ainda não foi submetida.");

					Path diretorioAtual = Paths.get("").toAbsolutePath();
					String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

					int tamanho = ficha.getArquivos().size();

					String nomeArquivo = ficha.getArquivos().get(tamanho - 1);

					Path arquivo = Paths.get(diretorioDestino + nomeArquivo);

					try {
						Resource resource = new UrlResource(arquivo.toUri());

						if (resource.exists()) {
							return ResponseEntity.ok()
									.contentType(MediaType.APPLICATION_PDF)
									.header(HttpHeaders.CONTENT_DISPOSITION,
											"attachment; filename=\"" + resource.getFilename() + "\"")
									.body(resource);
						} else {
							throw new NotFoundException("Arquivo não encontrado!");
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter o arquivo!");
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID da ficha de avaliação não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{grrAlunoURL}/relatorio-de-estagio/{id}/download")
	public ResponseEntity<Object> downloadRelatorioDeEstagioAluno(@PathVariable String grrAlunoURL,
			@PathVariable String id,
			@RequestHeader("Authorization") String accessToken) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, accessToken);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService.buscarRelatorioPorId(idLong);

					if (relatorioFind.isEmpty()) {
						throw new NotFoundException("O relatório de estágio não foi encontrado.");
					}

					RelatorioDeEstagio relatorio = relatorioFind.get();

					if (relatorio.isUploadFinal() == false && relatorio.isUploadParcial() == false)
						throw new BadRequestException("O relatório de estágio ainda não foi submetido.");

					Path diretorioAtual = Paths.get("").toAbsolutePath();
					String diretorioDestino = diretorioAtual + "/src/main/resources/arquivos/";

					int tamanho = relatorio.getArquivos().size();

					String nomeArquivo = relatorio.getArquivos().get(tamanho - 1);

					Path arquivo = Paths.get(diretorioDestino + nomeArquivo);

					try {
						Resource resource = new UrlResource(arquivo.toUri());

						if (resource.exists()) {
							return ResponseEntity.ok()
									.contentType(MediaType.APPLICATION_PDF)
									.header(HttpHeaders.CONTENT_DISPOSITION,
											"attachment; filename=\"" + resource.getFilename() + "\"")
									.body(resource);
						} else {
							throw new NotFoundException("Arquivo não encontrado!");
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter o arquivo!");
					}
				}
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"O GRR informado para o aluno ou o ID do relatório de estágio não é do tipo de dado esperado!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}