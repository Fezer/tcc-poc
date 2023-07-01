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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.CertificadoDeEstagioDTO;
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
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;
import br.ufpr.estagio.modulo.service.FichaDeAvaliacaoService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/coe")
public class CoeREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;

	@Autowired
	private CertificadoDeEstagioService certificadoDeEstagioService;

	@Autowired
	private RelatorioDeEstagioService relatorioDeEstagioService;

	@Autowired
	private FichaDeAvaliacaoService fichaDeAvaliacaoService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private TermoDeRescisaoService termoDeRescisaoService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/termo/pendenteAprovacaoCoe")
	public ResponseEntity<Object> listarTermosDeCompromissoPendenteAprovacaoCoe(
			@RequestParam(defaultValue = "0") int page) {
		try {
			Page<TermoDeEstagio> paginaTermos = termoDeEstagioService.listarTermoCompromissoPaginated(page,
					Optional.of(EnumStatusTermo.EmAprovacao), Optional.of(EnumEtapaFluxo.COE),
					Optional.of(EnumTipoEstagio.NaoObrigatorio), Optional.of(EnumTipoTermoDeEstagio.TermoDeCompromisso),
					Optional.empty());

			if (paginaTermos.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = paginaTermos.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, paginaTermos.getPageable(), paginaTermos.getTotalElements()));
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

	@GetMapping("/termo/indeferido")
	public ResponseEntity<Object> listarTermosDeCompromissoIndeferidos(@RequestParam(defaultValue = "0") int page) {
		try {
			Page<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosDeCompromissoIndeferidos(page);
			if (listaTermos.isEmpty()) {
				return null;
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = listaTermos.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, listaTermos.getPageable(), listaTermos.getTotalElements()));
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

	@GetMapping("/termoAditivo/pendenteAprovacaoCoe")
	public ResponseEntity<Object> listarTermosAditivoPendenteAprovacaoCoe(@RequestParam(defaultValue = "0") int page) {
		try {
			Page<TermoDeEstagio> listaTermosPaginated = termoDeEstagioService
					.listarTermosAditivoPendenteAprovacaoCoe(page);

			if (listaTermosPaginated.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = listaTermosPaginated.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(listaTermosDTO,
						listaTermosPaginated.getPageable(), listaTermosPaginated.getTotalElements()));
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

	@GetMapping("/termoAditivo/indeferido")
	public ResponseEntity<Object> listarTermosAditivoIndeferidos(@RequestParam(defaultValue = "0") int page) {
		try {
			Page<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosAditivosIndeferidos(page);
			if (listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = listaTermos.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, listaTermos.getPageable(), listaTermos.getTotalElements()));
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

	@PutMapping("/termo/{idTermo}/indeferir")
	public ResponseEntity<Object> indeferirTermoDeCompromisso(@PathVariable Long idTermo,
			@RequestBody JustificativaDTO justificativa) {
		try {
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idTermo);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.indeferirTermoDeEstagioCoe(termo, justificativa);
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

	@PutMapping("/termo/{idTermo}/solicitarAjustes")
	public ResponseEntity<Object> solicitarAjutesTermoDeCompromisso(@PathVariable Long idTermo,
			@RequestBody DescricaoAjustesDTO descricaoAjustes) {
		try {
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idTermo);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.solicitarAjutesTermoDeEstagioCoe(termo, descricaoAjustes);
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

	@PutMapping("/termo/{idTermo}/aprovar")
	public ResponseEntity<Object> aprovarTermoDeCompromisso(@PathVariable Long idTermo) {
		try {
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idTermo);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				termo = termoDeEstagioService.aprovarTermoDeEstagioCoe(termo);
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

	@GetMapping("/certificado/pendenteAprovacaoCoe")
	public ResponseEntity<Object> listarCertificadosPendenteAprovacao() {
		try {
			List<CertificadoDeEstagio> listaCertificados = certificadoDeEstagioService
					.listarCertificadosPendentesAprovacaoCoe();
			if (listaCertificados == null || listaCertificados.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaCertificados.stream()
						.map(e -> mapper.map(e, CertificadoDeEstagioDTO.class)).collect(Collectors.toList()));
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

	@PutMapping("/certificado/{idCertificado}/aprovar")
	public ResponseEntity<Object> aprovarCertificadoDeEstagio(@PathVariable Long idCertificado) {
		try {
			Optional<CertificadoDeEstagio> certificadoOptional = certificadoDeEstagioService
					.buscarCertificadoDeEstagioPorId(idCertificado);
			if (certificadoOptional.isEmpty()) {
				throw new NotFoundException("Certificado de estágio não encontrado!");
			} else {
				CertificadoDeEstagio certificado = certificadoOptional.get();
				certificado = certificadoDeEstagioService.aprovarCertificadoDeEstagioCoe(certificado);
				CertificadoDeEstagioDTO certificadoDTO = mapper.map(certificado, CertificadoDeEstagioDTO.class);
				return new ResponseEntity<>(certificadoDTO, HttpStatus.OK);
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

	@PutMapping("/certificado/{idCertificado}/reprovar")
	public ResponseEntity<Object> reprovarCertificadoDeEstagio(@PathVariable Long idCertificado,
			@RequestBody JustificativaDTO justificativa) {
		try {
			Optional<CertificadoDeEstagio> certificadoOptional = certificadoDeEstagioService
					.buscarCertificadoDeEstagioPorId(idCertificado);
			if (certificadoOptional.isEmpty()) {
				throw new NotFoundException("Certificado de estágio não encontrado!");
			} else {
				CertificadoDeEstagio certificado = certificadoOptional.get();
				certificado = certificadoDeEstagioService.reprovarCertificadoDeEstagioCoe(certificado, justificativa);
				CertificadoDeEstagioDTO certificadoDTO = mapper.map(certificado, CertificadoDeEstagioDTO.class);
				return new ResponseEntity<>(certificadoDTO, HttpStatus.OK);
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

	@GetMapping("/termoDeRescisao/pendenteCiencia")
	public ResponseEntity<Object> listarTermosDeRescisaoPendenteCienciaCoe() {
		try {
			List<TermoDeRescisao> listaTermosDeRescisao = termoDeRescisaoService
					.listarTermosDeRescisaoPendenteCienciaCoe();
			if (listaTermosDeRescisao == null || listaTermosDeRescisao.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermosDeRescisao.stream()
						.map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
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

	@PutMapping("/termoDeRescisao/{idTermo}/darCiencia")
	public ResponseEntity<Object> darCienciaTermoDeRescisao(@PathVariable Long idTermo) {
		try {
			Optional<TermoDeRescisao> termoOptional = termoDeRescisaoService.buscarPorId(idTermo);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeRescisao termo = termoOptional.get();
				termo = termoDeRescisaoService.darCienciaTermoDeRescisaoCoe(termo);
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

	/* Métodos para Coe baixar documentos upados pelo Aluno */
	@GetMapping("/{grrAlunoURL}/download-termo")
	public ResponseEntity<Object> downloadTermo(@PathVariable String grrAlunoURL) {
		try {
			if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL, null);
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

					int tamanho = termo.getArquivos().size();

					String nomeArquivo = termo.getArquivos().get(tamanho - 1);

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