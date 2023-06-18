package br.ufpr.estagio.modulo.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.DescricaoAjustesDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeRescisaoDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoDocumento;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.FichaDeAvaliacaoService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/coordenacao")
public class CoordenacaoREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;

	@Autowired
	private TermoDeRescisaoService termoDeRescisaoService;

	@Autowired
	private RelatorioDeEstagioService relatorioDeEstagioService;

	@Autowired
	private FichaDeAvaliacaoService fichaDeAvaliacaoService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/termo/pendenteAprovacaoCoordenacao")
	public ResponseEntity<Page<TermoDeEstagioDTO>> listarTermosPendenteAprovacao(
			@RequestParam(defaultValue = "0") int page) {
		try {
			Page<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermoCompromissoPaginated(
					page,
					Optional.of(EnumStatusTermo.EmAprovacao),
					Optional.of(EnumEtapaFluxo.Coordenacao),
					Optional.empty(), // Envolve the null value in an Optional
					Optional.of(EnumTipoTermoDeEstagio.TermoDeCompromisso),
					Optional.empty());

			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = listaTermos.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class))
						.collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, listaTermos.getPageable(),
								listaTermos.getTotalElements()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termo/pendenteAprovacaoCoordenacaoFiltro")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosPendenteAprovacaoPorTipoEstagio(
			@RequestParam String tipoEstagio) {
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService
					.listarTermosDeCompromissoPendenteAprovacaoCoordenacaoPorTipoEstagio(tipoEstagio);
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termo/indeferido")
	public ResponseEntity<Page<TermoDeEstagioDTO>> listarTermosIndeferidos(
			@RequestParam(defaultValue = "0") int page) {
		try {
			Page<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoIndeferidos(
					page);
			if (listaTermos.isEmpty()) {
				return null;
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = listaTermos.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class))
						.collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, listaTermos.getPageable(),
								listaTermos.getTotalElements()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termo/pendenteCienciaCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosIndeferidosPendentesCienciaCoordenacao() {
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService
					.listarTermosDeCompromissoIndeferidosPendentesCienciaCoordenacao();
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termoAditivo/pendenteAprovacaoCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivosPendenteAprovacao() {
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosPendenteAprovacaoCoordenacao();
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termoAditivo/pendenteAprovacaoCoordenacaoFiltro")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivosPendenteAprovacaoPorTipoEstagio(
			@RequestParam String tipoEstagio) {
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService
					.listarTermosAditivosPendenteAprovacaoCoordenacaoPorTipoEstagio(tipoEstagio);
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termoAditivo/indeferido")
	public ResponseEntity<Page<TermoDeEstagioDTO>> listarTermosAditivosIndeferidos(
			@RequestParam(defaultValue = "0") int page) {
		try {
			Page<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosIndeferidos(page);
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = listaTermos.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class))
						.collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, listaTermos.getPageable(),
								listaTermos.getTotalElements()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termoAditivo/pendenteCienciaCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosAditivosIndeferidosPendentesCienciaCoordenacao() {
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService
					.listarTermosAditivosIndeferidosPendentesCienciaCoordenacao();
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/termo/{idTermo}/indeferir")
	public ResponseEntity<TermoDeEstagioDTO> indeferirTermoDeCompromisso(@PathVariable Long idTermo,
			@RequestBody JustificativaDTO justificativa) {
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.indeferirTermoDeEstagioCoordenacao(termo, justificativa);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/termo/{idTermo}/solicitarAjustes")
	public ResponseEntity<TermoDeEstagioDTO> solicitarAjutesTermoDeCompromisso(@PathVariable Long idTermo,
			@RequestBody DescricaoAjustesDTO descricaoAjustes) {
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.solicitarAjutesTermoDeEstagioCoordenacao(termo, descricaoAjustes);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/termo/{idTermo}/cienciaFormacaoSupervisor")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaFormacaoSupervisor(@PathVariable Long idTermo) {
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.darCienciaFormacaoSupervisorCoordenacao(termo);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/termo/{idTermo}/cienciaPlanoAtividades")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaPlanoAtividades(@PathVariable Long idTermo) {
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.darCienciaPlanoAtividadesCoordenacao(termo);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/termo/{idTermo}/cienciaIra")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaIra(@PathVariable Long idTermo) {
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.darCienciaIraCoordenacao(termo);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/termo/{idTermo}/cienciaIndeferimento")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaTermoIndeferido(@PathVariable Long idTermo) {
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.darCienciaIndeferimentoCoordenacao(termo);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/termo/{idTermo}/aprovar")
	public ResponseEntity<TermoDeEstagioDTO> aprovarTermoDeCompromisso(@PathVariable Long idTermo) {
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.aprovarTermoDeEstagioCoordenacao(termo);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termoDeRescisao/pendenteCiencia")
	public ResponseEntity<List<TermoDeRescisaoDTO>> listarTermosDeRescisaoPendenteCienciaCoordenacao() {
		try {
			List<TermoDeRescisao> listaTermosDeRescisao = termoDeRescisaoService
					.listarTermosDeRescisaoPendenteCienciaCoordenacao();
			if (listaTermosDeRescisao == null || listaTermosDeRescisao.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermosDeRescisao.stream()
						.map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@PutMapping("/termoDeRescisao/{idTermo}/darCiencia")
	public ResponseEntity<TermoDeRescisaoDTO> darCienciaTermoDeRescisao(@PathVariable Long idTermo) {
		try {
			Optional<TermoDeRescisao> termoOptional = termoDeRescisaoService.buscarPorId(idTermo);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeRescisao termo = termoOptional.get();
				termo = termoDeRescisaoService.darCienciaTermoDeRescisaoCoordenacao(termo);
				TermoDeRescisaoDTO termoDTO = mapper.map(termo, TermoDeRescisaoDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/{grrAlunoURL}/download-termo")
	public ResponseEntity<Resource> downloadTermo(@PathVariable String grrAlunoURL,
			@RequestParam("Authorization") String accessToken) {
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
	}

	/* Métodos para Coordenacao baixar documentos upados pelo Aluno */

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
						throw new BadRequestException("O aluno ainda não submeteu o termo de compromisso.");

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
						throw new BadRequestException("O aluno ainda não submeteu o termo aditivo.");

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
						throw new BadRequestException("O aluno ainda não submeteu o termo de rescisão.");

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
						throw new BadRequestException("A aluno ainda não submeteu a ficha de avaliação.");

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
						throw new BadRequestException("O aluno ainda não submeteu o relatório de estágio.");

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