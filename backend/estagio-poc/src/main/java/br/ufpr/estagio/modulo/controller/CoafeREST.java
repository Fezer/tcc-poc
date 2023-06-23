package br.ufpr.estagio.modulo.controller;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ContentDisposition;
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

import com.lowagie.text.DocumentException;

import br.ufpr.estagio.modulo.dto.AgenteIntegradorResumidoDTO;
import br.ufpr.estagio.modulo.dto.ApoliceResumidoDTO;
import br.ufpr.estagio.modulo.dto.DescricaoAjustesDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.SeguradoraResumidoDTO;
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
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.AgenteIntegradorService;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.ApoliceService;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;
import br.ufpr.estagio.modulo.service.ContratanteService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.FichaDeAvaliacaoService;
import br.ufpr.estagio.modulo.service.GeradorDeExcelService;
import br.ufpr.estagio.modulo.service.GeradorDePdfService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/coafe")
public class CoafeREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;

	@Autowired
	private TermoDeRescisaoService termoDeRescisaoService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private EstagioService estagioService;

	@Autowired
	private CertificadoDeEstagioService certificadoDeEstagioService;

	@Autowired
	private FichaDeAvaliacaoService fichaDeAvaliacaoService;

	@Autowired
	private RelatorioDeEstagioService relatorioDeEstagioService;

	@Autowired
	private ContratanteService contratanteService;

	@Autowired
	private AgenteIntegradorService agenteIntegradorService;
	
	@Autowired
	private ApoliceService apoliceService;

	@Autowired
	private GeradorDeExcelService geradorExcelService;

	@Autowired
	private GeradorDePdfService geradorService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/termo/pendenteAprovacaoCoafe")
	public ResponseEntity<Page<TermoDeEstagioDTO>> listarTermosDeCompromissoPendenteAprovacao(
			@RequestParam(value = "page", defaultValue = "0") Integer page) {
		try {
			Page<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermoCompromissoPaginated(
					page,
					Optional.of(EnumStatusTermo.EmAprovacao),
					Optional.of(EnumEtapaFluxo.COAFE),
					Optional.empty(),
					Optional.of(EnumTipoTermoDeEstagio.TermoDeCompromisso),
					Optional.empty());
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class))
						.collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, listaTermos.getPageable(), listaTermos.getTotalElements()));

			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/termo/pendenteAprovacaoCoafeFiltro")
	public ResponseEntity<Object> listarTermosDeCompromissoPendenteAprovacaoPorTipoEstagio(
			@RequestParam String tipoEstagio) {
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService
					.listarTermosDeCompromissoPendenteAprovacaoCoafePorTipoEstagio(tipoEstagio);
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/termo/indeferido")
	public ResponseEntity<Page<TermoDeEstagioDTO>> listarTermosDeCompromissoIndeferidos(
			@RequestParam(defaultValue = "0") Integer page) {
		try {
			Page<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoIndeferidos(page);
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

	@GetMapping("/termoAditivo/pendenteAprovacaoCoafe")
	public ResponseEntity<Object> listarTermosAditivoPendenteAprovacao() {
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosPendenteAprovacaoCoafe();
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/termoAditivo/pendenteAprovacaoCoafeFiltro")
	public ResponseEntity<Object> listarTermosAditivoPendenteAprovacaoPorTipoEstagio(
			@RequestParam String tipoEstagio) {
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService
					.listarTermosAditivosPendenteAprovacaoCoafePorTipoEstagio(tipoEstagio);
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/termoAditivo/indeferido")
	public ResponseEntity<Page<TermoDeEstagioDTO>> listarTermosAditivoIndeferidos(
			@RequestParam(defaultValue = "0") Integer page) {
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

	@GetMapping("/termo/{idTermo}/agenteIntegrador")
	public ResponseEntity<Object> consultarAgenteIntegradorAssociadoAoTermo(
			@PathVariable String idTermo) {
		try {
			long idLong = Long.parseLong(idTermo);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");
			
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idLong);

			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				AgenteIntegrador agente = termo.getAgenteIntegrador();
				AgenteIntegradorResumidoDTO agenteDTO = mapper.map(agente, AgenteIntegradorResumidoDTO.class);
				return new ResponseEntity<>(agenteDTO, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/termo/{idTermo}/seguradora")
	public ResponseEntity<Object> consultarSeguradoraAssociadaAoTermo(@PathVariable String idTermo) {
		try {
			long idLong = Long.parseLong(idTermo);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");
			
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				Seguradora seguradora = termo.getSeguradora();
				SeguradoraResumidoDTO seguradoraDTO = mapper.map(seguradora, SeguradoraResumidoDTO.class);
				return new ResponseEntity<>(seguradoraDTO, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/termo/{idTermo}/apolice")
	public ResponseEntity<Object> consultarApoliceAssociadaAoTermo(@PathVariable String idTermo) {
		try {
			long idLong = Long.parseLong(idTermo);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");
			
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				Apolice apolice = termo.getApolice();
				ApoliceResumidoDTO apoliceDTO = mapper.map(apolice, ApoliceResumidoDTO.class);
				return new ResponseEntity<>(apoliceDTO, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@PutMapping("/termo/{idTermo}/indeferir")
	public ResponseEntity<Object> indeferirTermoDeCompromisso(@PathVariable String idTermo,
			@RequestBody JustificativaDTO justificativa) {
		try {
			long idLong = Long.parseLong(idTermo);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");
			
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.indeferirTermoDeEstagioCoafe(termo, justificativa);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@PutMapping("/termo/{idTermo}/solicitarAjustes")
	public ResponseEntity<Object> solicitarAjutesTermoDeCompromisso(@PathVariable String idTermo,
			@RequestBody DescricaoAjustesDTO descricaoAjustes) {
		try {
			long idLong = Long.parseLong(idTermo);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");
			
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.solicitarAjutesTermoDeEstagioCoafe(termo, descricaoAjustes);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@PutMapping("/termo/{idTermo}/aprovar")
	public ResponseEntity<Object> aprovarTermoDeCompromisso(@PathVariable String idTermo) {
		try {
			long idLong = Long.parseLong(idTermo);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");
			
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.aprovarTermoDeEstagioCoafe(termo);
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/termoDeRescisao/pendenteCiencia")
	public ResponseEntity<Object> listarTermosDeRescisaoPendenteCienciaCoafe() {
		try {
			List<TermoDeRescisao> listaTermosDeRescisao = termoDeRescisaoService
					.listarTermosDeRescisaoPendenteCienciaCoafe();
			if (listaTermosDeRescisao == null || listaTermosDeRescisao.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermosDeRescisao.stream()
						.map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
			}
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

	@PutMapping("/termoDeRescisao/{idTermo}/darCiencia")
	public ResponseEntity<Object> darCienciaTermoDeRescisao(@PathVariable String idTermo) {
		try {
			long idLong = Long.parseLong(idTermo);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");
			
			Optional<TermoDeRescisao> termoOptional = termoDeRescisaoService.buscarPorId(idLong);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeRescisao termo = termoOptional.get();
				termo = termoDeRescisaoService.darCienciaTermoDeRescisaoCoafe(termo);
				TermoDeRescisaoDTO termoDTO = mapper.map(termo, TermoDeRescisaoDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	// 							ARQUIVOS
	// Não lembro como veio parar aqui e estou no meio de outra coisa.
	// Mantido para não quebrar algo, mas acho que pode apagar.
	@GetMapping("/{grrAlunoURL}/download-termo")
	public ResponseEntity<Resource> downloadTermo(@PathVariable String grrAlunoURL,
			@RequestHeader("Authorization") String accessToken) {
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

	/* Métodos para COAFE gerar relatórios */

	@GetMapping("/gerar-relatorio-seguradora-ufpr")
	public ResponseEntity<Object> gerarRelatorioSeguradoraUfprPdf() throws IOException, DocumentException {

		try {

			List<Estagio> estagio = estagioService.buscarEstagioPorSeguradoraUfpr();
	
			byte[] pdf = geradorService.gerarPdfEstagioSeguradoraUfpr(estagio);
	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDisposition(
					ContentDisposition.builder("inline").filename("relatorio-estagios-seguradora-ufpr.pdf").build());
	
			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-seguradora-ufpr-excel")
	public ResponseEntity<Object> gerarRelatorioSeguradoraUfprExcel() throws IOException {
		try {
			List<Estagio> estagios = estagioService.buscarEstagioPorSeguradoraUfpr();

			if (estagios.isEmpty())
				throw new NotFoundException("Não foi encontrado estágio com seguradora UFPR");

			ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelEstagioSeguradoraUfpr(estagios);

			ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment")
					.filename("relatorio-estagios-seguradora-ufpr-excel.xlsx").build());
			headers.set("Content-Encoding", "UTF-8");

			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-empresa/{idContratanteURL}")
	public ResponseEntity<Object> gerarPdfEmpresa(@PathVariable String idContratanteURL)
			throws IOException, DocumentException {

		try {

			if (idContratanteURL.isBlank() || idContratanteURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				long idInt = Long.parseLong(idContratanteURL);
	
				Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idInt);
	
				if (!contratanteFind.isPresent()) {
					throw new NotFoundException("Contratante não encontrado!");
				} else {
	
					Contratante contratante = contratanteFind.get();
	
					byte[] pdf = geradorService.gerarPdfContratante(contratante);
	
					// byte[] pdf = geradorService.gerarPdfSimples();
	
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_PDF);
					headers.setContentDisposition(ContentDisposition.builder("inline")
							.filename("relatorio-contratante" + contratante.getId() + ".pdf").build());
	
					return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-empresa-excel/{idContratanteURL}")
	public ResponseEntity<Object> gerarExcelEmpresa(@PathVariable String idContratanteURL)
			throws IOException, DocumentException {

		try {

			if (idContratanteURL.isBlank() || idContratanteURL.isEmpty()) {
				throw new BadRequestException("Id do contratante não informado!");
			} else {
				long idInt = Long.parseLong(idContratanteURL);

				Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idInt);

				if (!contratanteFind.isPresent()) {
					throw new NotFoundException("Contratante não encontrado!");
				} else {

					Contratante contratante = contratanteFind.get();

					ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelContratante(contratante);

					ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

					HttpHeaders headers = new HttpHeaders();
					headers.setContentDisposition(ContentDisposition.builder("attachment")
							.filename("relatorio-contratante-" + contratante.getId() + "-excel.xlsx").build());
					headers.set("Content-Encoding", "UTF-8");

					return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-agenteIntegrador/{idAgenteURL}")
	public ResponseEntity<Object> gerarPdfAgenteIntegrador(@PathVariable String idAgenteURL)
			throws IOException, DocumentException {

		try {

			if (idAgenteURL.isBlank() || idAgenteURL.isEmpty()) {
				throw new BadRequestException("Id do agente integradornão informado!");
			} else {
				long idInt = Long.parseLong(idAgenteURL);
	
				Optional<AgenteIntegrador> agenteIntegradorFind = agenteIntegradorService.buscarPorId(idInt);
	
				if (!agenteIntegradorFind.isPresent()) {
					throw new NotFoundException("Contratante não encontrado!");
				} else {
	
					AgenteIntegrador agenteIntegrador = agenteIntegradorFind.get();
	
					byte[] pdf = geradorService.gerarPdfAgenteIntegrador(agenteIntegrador);
	
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_PDF);
					headers.setContentDisposition(ContentDisposition.builder("inline")
							.filename("relatorio-agente-integrador-" + agenteIntegrador.getId() + ".pdf").build());
	
					return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-agenteIntegrador-excel/{idAgenteURL}")
	public ResponseEntity<Object> gerarExcelAgenteIntegrador(@PathVariable String idAgenteURL)
			throws IOException, DocumentException {

		try {

			if (idAgenteURL.isBlank() || idAgenteURL.isEmpty()) {
				throw new BadRequestException("Id do agente integrador não informado!");
			} else {
				long idInt = Long.parseLong(idAgenteURL);
	
				Optional<AgenteIntegrador> agenteIntegradorFind = agenteIntegradorService.buscarPorId(idInt);
	
				if (!agenteIntegradorFind.isPresent()) {
					throw new NotFoundException("Agente integrador não encontrado!");
				} else {
	
					AgenteIntegrador agenteIntegrador = agenteIntegradorFind.get();
	
					ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelAgenteIntegrador(agenteIntegrador);
	
					ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
	
					HttpHeaders headers = new HttpHeaders();
					headers.setContentDisposition(ContentDisposition.builder("attachment")
							.filename("relatorio-agente-integrador-" + agenteIntegrador.getId() + "-excel.xlsx").build());
					headers.set("Content-Encoding", "UTF-8");
	
					return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-certificados")
	public ResponseEntity<Object> gerarRelatorioCertificadosPdf() throws IOException, DocumentException {

		try {
			List<CertificadoDeEstagio> certificados = certificadoDeEstagioService.listarTodosCertificadosDeEstagio();

			byte[] pdf = geradorService.gerarPdfCertificadosDeEstagio(certificados);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDisposition(
					ContentDisposition.builder("inline").filename("relatorio-certificados-de-estagio.pdf").build());

			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-certificados-excel")
	public ResponseEntity<Object> gerarRelatorioCertificadosExcel() throws IOException, DocumentException {

		try {
			List<CertificadoDeEstagio> certificados = certificadoDeEstagioService.listarTodosCertificadosDeEstagio();

			if (certificados.isEmpty())
				throw new NotFoundException("Não foram encontrados certificados de estágio.");

			ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelCertificadosDeEstagio(certificados);

			ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment")
					.filename("relatorio-certificados-de-estagio-excel.xlsx").build());
			headers.set("Content-Encoding", "UTF-8");

			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorios-relatorioDeEstagio")
	public ResponseEntity<Object> gerarRelatorioRealtoriosDeEstagioPdf() throws IOException, DocumentException {

		try {
			List<RelatorioDeEstagio> relatorios = relatorioDeEstagioService.listarTodosRelatorios();

			byte[] pdf = geradorService.gerarPdfRelatoriosDeEstagio(relatorios);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDisposition(
					ContentDisposition.builder("inline").filename("relatorio-relatorios-de-estagio.pdf").build());

			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorios-relatorioDeEstagio-excel")
	public ResponseEntity<Object> gerarRelatorioRealtoriosDeEstagioExcel() throws IOException, DocumentException {

		try {
			List<RelatorioDeEstagio> relatorios = relatorioDeEstagioService.listarTodosRelatorios();

			ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelRelatoriosDeEstagio(relatorios);

			ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment")
					.filename("relatorio-relatorios-de-estagio-excel.xlsx").build());
			headers.set("Content-Encoding", "UTF-8");

			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-relatorioDeEstagio/{id}")
	public ResponseEntity<Object> gerarRelatorioRealtorioDeEstagioPdf(@PathVariable String id)
			throws IOException, DocumentException {

		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<RelatorioDeEstagio> relatorio = relatorioDeEstagioService.buscarRelatorioPorId(idLong);

			if (relatorio.isEmpty()) {
				throw new NotFoundException("Relatório não encontrado!");
			}

			byte[] pdf = geradorService.gerarPdfRelatorioDeEstagio(relatorio);

			RelatorioDeEstagio rel = relatorio.get();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDisposition(ContentDisposition.builder("inline")
					.filename("relatorio-relatorio-de-estagio-" + rel.getId() + ".pdf").build());

			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	@GetMapping("/gerar-relatorio-relatorioDeEstagio-excel/{id}")
	public ResponseEntity<Object> gerarRelatorioRealtorioDeEstagioExcel(@PathVariable String id)
			throws IOException, DocumentException {

		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService.buscarRelatorioPorId(idLong);

			if (relatorioFind.isEmpty()) {
				throw new NotFoundException("Relatório não encontrado!");
			}

			RelatorioDeEstagio relatorio = relatorioFind.get();

			ByteArrayOutputStream outputStream = geradorExcelService.gerarExcelRelatorioDeEstagio(relatorio);

			ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment")
					.filename("relatorio-relatorio-de-estagio-" + relatorio.getId() + "-excel.xlsx").build());
			headers.set("Content-Encoding", "UTF-8");

			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
					"O ID não é do tipo de dado esperado!");
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

	/* Métodos para COAFE baixar relatórios upados pelo Aluno */

	@GetMapping("/{grrAlunoURL}/termo-de-compromisso/{id}/download")
	public ResponseEntity<Object> downloadTermoDeCompromissoAluno(@PathVariable String grrAlunoURL,
			@PathVariable String id) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, null);
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
	public ResponseEntity<Object> downloadTermoAditivoAluno(@PathVariable String grrAlunoURL, @PathVariable String id) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, null);
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
			@PathVariable String id) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, null);
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

	@GetMapping("/{grrAlunoURL}/ficha-de-avaliacao-final/{id}/download")
	public ResponseEntity<Object> downloadFichaDeAvaliacaoFinalAluno(@PathVariable String grrAlunoURL,
			@PathVariable String id) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, null);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					Optional<FichaDeAvaliacao> fichaFind = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idLong);

					if (fichaFind.isEmpty()) {
						throw new NotFoundException("A ficha de avaliação final não foi encontrada.");
					}

					FichaDeAvaliacao ficha = fichaFind.get();

					if (ficha.isUpload() == false)
						throw new BadRequestException("O aluno ainda não submeteu a ficha de avaliação final.");

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

	@GetMapping("/{grrAlunoURL}/ficha-de-avaliacao-parcial/{id}/download")
	public ResponseEntity<Object> downloadFichaDeAvaliacaoParcialAluno(@PathVariable String grrAlunoURL,
			@PathVariable String id) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, null);
				if (aluno == null) {
					throw new NotFoundException("Aluno não encontrado!");
				} else {
					long idLong = Long.parseLong(id);

					if (idLong < 1L)
						throw new InvalidFieldException("Id inválido.");

					Optional<FichaDeAvaliacao> fichaFind = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idLong);

					if (fichaFind.isEmpty()) {
						throw new NotFoundException("A ficha de avaliação parcial não foi encontrada.");
					}

					FichaDeAvaliacao ficha = fichaFind.get();

					if (ficha.isUpload() == false)
						throw new BadRequestException("O aluno ainda não submeteu a ficha de avaliação parcial.");

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
			@PathVariable String id) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, null);
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
	
	@PutMapping("/{termoId}/associarApolice/{apoliceId}")
	public ResponseEntity<Object> associarApolice(@PathVariable Long termoId, @PathVariable Long apoliceId) {
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(termoId));
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}
			Optional<Apolice> apoliceFind = apoliceService.buscarPorId(apoliceId);
			if (apoliceFind.isEmpty()) {
				throw new NotFoundException("Apólice não encontrada!");
			} else {
				Apolice apolice = apoliceFind.get();
				TermoDeEstagio termo = termofind.get();
				
				if (!apolice.getSeguradora().isSeguradoraUfpr())
					throw new InvalidFieldException("A seguradora não é uma seguradora UFPR.");
				
				if (!termo.getEstagio().isEstagioUfpr())
					throw new InvalidFieldException("O estágio não será realizado na UFPR.");
				
				TermoDeEstagio termoAtualizado = termoDeEstagioService.associarApoliceAoTermoDeEstagio(termo, apolice);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
			}
		}  catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O ID informado não é do tipo de dado esperado!");
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