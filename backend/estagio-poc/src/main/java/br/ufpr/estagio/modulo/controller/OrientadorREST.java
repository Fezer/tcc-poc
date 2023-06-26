package br.ufpr.estagio.modulo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import br.ufpr.estagio.modulo.dto.CertificadoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.dto.RelatorioDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeRescisaoDTO;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.GeradorDeExcelService;
import br.ufpr.estagio.modulo.service.GeradorDePdfService;
import br.ufpr.estagio.modulo.service.OrientadorService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/orientador")
public class OrientadorREST {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private EstagioService estagioService;

	@Autowired
	private OrientadorService orientadorService;

	@Autowired
	private RelatorioDeEstagioService relatorioDeEstagioService;

	@Autowired
	private CertificadoDeEstagioService certificadoDeEstagioService;

	@Autowired
	private TermoDeRescisaoService termoDeRescisaoService;

	@Autowired
	private GeradorDePdfService geradorService;

	@Autowired
	private GeradorDeExcelService geradorExcelService;

	@GetMapping("/{idOrientador}/estagio")
	public ResponseEntity<?> listarEstagiosDeOrientandos(@PathVariable String idOrientador,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) Optional<String> grrAluno,
			@RequestParam(required = false) Optional<EnumStatusEstagio> statusEstagio) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Optional<String> grrOptional = grrAluno == null ? Optional.empty() : grrAluno;
				Optional<EnumStatusEstagio> statusEstagioOptional = statusEstagio == null ? Optional.empty()
						: statusEstagio;

				Orientador orientador = orientadorFind.get();

				Page<Estagio> listaEstagios = estagioService.listarEstagiosPorIdOrientador(orientador.getId(),
						page,
						grrOptional,
						statusEstagioOptional);

				List<EstagioDTO> listaDTO = new ArrayList<EstagioDTO>();
				for (Estagio l : listaEstagios) {
					listaDTO.add(estagioService.toEstagioDTO(l));
				}

				Page<EstagioDTO> listaDTOPage = new PageImpl<EstagioDTO>(listaDTO, listaEstagios.getPageable(),
						listaEstagios.getTotalElements());

				return ResponseEntity.ok(listaDTOPage);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do orientador deve ser um número!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
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

	@GetMapping("/{idOrientador}/estagio/pendenteAprovacao")
	public ResponseEntity<?> listarEstagiosDeOrientandosPendenteAprovacao(
			@PathVariable String idOrientador) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<Estagio> listaEstagios = estagioService
						.listarEstagiosPendenteAprovacaoPorIdOrientador(orientador.getId());
				List<EstagioDTO> listaDTO = new ArrayList<EstagioDTO>();
				for (Estagio l : listaEstagios) {
					listaDTO.add(estagioService.toEstagioDTO(l));
				}
				return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do orientador deve ser um número!");
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

	@GetMapping("/{idOrientador}/estagio/indeferido")
	public ResponseEntity<?> listarEstagiosDeOrientandosIndeferido(@PathVariable String idOrientador) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<Estagio> listaEstagios = estagioService
						.listarEstagiosIndeferidosPorIdOrientador(orientador.getId());
				List<EstagioDTO> listaDTO = new ArrayList<EstagioDTO>();
				for (Estagio l : listaEstagios) {
					listaDTO.add(estagioService.toEstagioDTO(l));
				}
				return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do orientador deve ser um número!");
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

	@GetMapping("/{idOrientador}/relatorioDeEstagio/")
	public ResponseEntity<?> listarRelatoriosDeEstagioDeOrientandos(
			@PathVariable String idOrientador) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<RelatorioDeEstagio> listaRelatorios = relatorioDeEstagioService
						.listarRelatoriosPorIdOrientador(orientador.getId());
				List<RelatorioDeEstagioDTO> listaRelatoriosDTO = listaRelatorios.stream()
						.map(ap -> mapper.map(ap, RelatorioDeEstagioDTO.class)).collect(Collectors.toList());
				return ResponseEntity.ok().body(listaRelatoriosDTO);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do orientador deve ser um número!");
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

	@GetMapping("/{idOrientador}/relatorioDeEstagio/pendenteCiencia")
	public ResponseEntity<?> listarRelatoriosDeEstagioDeOrientandosPendenteCiencia(
			@PathVariable String idOrientador) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<RelatorioDeEstagio> listaRelatorios = relatorioDeEstagioService
						.listarRelatoriosPendenteCienciaPorIdOrientador(orientador.getId());
				List<RelatorioDeEstagioDTO> listaRelatoriosDTO = listaRelatorios.stream()
						.map(ap -> mapper.map(ap, RelatorioDeEstagioDTO.class)).collect(Collectors.toList());
				return ResponseEntity.ok().body(listaRelatoriosDTO);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do orientador deve ser um número!");
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

	@PutMapping("/{idOrientador}/relatorioDeEstagio/{idRelatorio}/darCiencia")
	public ResponseEntity<Object> darCienciaRelatorioDeEstagio(@PathVariable String idOrientador,
			@PathVariable String idRelatorio) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			}

			long idLongRelatorio = Long.parseLong(idRelatorio);

			if (idLongRelatorio < 1) {
				throw new InvalidFieldException("Id do relatório inválido!");
			}

			Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService
					.buscarRelatorioPorId(idLongRelatorio);

			if (relatorioFind.isEmpty()) {
				throw new NotFoundException("Relatório não encontrado!");
			} else {
				RelatorioDeEstagio relatorio = relatorioFind.get();
				relatorio = relatorioDeEstagioService.darCienciaRelatorioDeEstagioOrientador(relatorio);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(relatorio, RelatorioDeEstagioDTO.class));
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id deve ser um número!");
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

	@GetMapping("/{idOrientador}/certificadoDeEstagio/")
	public ResponseEntity<?> listarCertificadosDeEstagio(@PathVariable String idOrientador) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<CertificadoDeEstagio> listaCertificados = certificadoDeEstagioService
						.listarCertificadosPorIdOrientador(orientador.getId());
				List<CertificadoDeEstagioDTO> listaCertificadosDTO = listaCertificados.stream()
						.map(ap -> mapper.map(ap, CertificadoDeEstagioDTO.class)).collect(Collectors.toList());
				return ResponseEntity.ok().body(listaCertificadosDTO);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do orientador deve ser um número!");
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

	@GetMapping("/{idOrientador}/termoDeRescisao/pendenteCiencia")
	public ResponseEntity<?> listarTermosDeRescisaoPendenteCienciaOrientador(
			@PathVariable String idOrientador) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			}
			List<TermoDeRescisao> listaTermosDeRescisao = termoDeRescisaoService
					.listarTermosDeRescisaoPendenteCienciaOrientador(idLongOrientador);
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
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do orientador deve ser um número!");
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

	@PutMapping("/{idOrientador}/termoDeRescisao/{idTermo}/darCiencia")
	public ResponseEntity<Object> darCienciaTermoDeRescisao(@PathVariable String idOrientador,
			@PathVariable String idTermo) {
		try {
			long idLongOrientador = Long.parseLong(idOrientador);

			if (idLongOrientador < 1) {
				throw new InvalidFieldException("Id do orientador inválido!");
			}

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idLongOrientador);

			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			}

			long idLongTermo = Long.parseLong(idTermo);

			if (idLongTermo < 1) {
				throw new InvalidFieldException("Id do termo inválido!");
			}

			Optional<TermoDeRescisao> termoOptional = termoDeRescisaoService.buscarPorId(idLongTermo);

			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeRescisao termo = termoOptional.get();
				termo = termoDeRescisaoService.darCienciaTermoDeRescisaoOrientador(termo);
				TermoDeRescisaoDTO termoDTO = mapper.map(termo, TermoDeRescisaoDTO.class);
				return new ResponseEntity<>(termoDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id deve ser um número!");
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

	@GetMapping("/{idOrientador}/certificado/{idCertificado}/imprimir-certificado")
	public ResponseEntity<Object> gerarCertificadoPdf(@PathVariable String idOrientador,
			@PathVariable String idCertificado, @RequestHeader("Authorization") String accessToken)
			throws IOException, DocumentException {

		try {

			long idOrientadorLong = Long.parseLong(idOrientador);

			if (idOrientadorLong < 1L)
				throw new InvalidFieldException("Id de orientador inválido.");

			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idOrientadorLong);

			long idCertificadoLong = Long.parseLong(idCertificado);

			if (idCertificadoLong < 1L)
				throw new InvalidFieldException("Id de certificado inválido.");

			Optional<CertificadoDeEstagio> certificadoFind = certificadoDeEstagioService
					.buscarCertificadoDeEstagioPorId(idCertificadoLong);

			if (orientadorFind == null) {
				throw new NotFoundException("Orientador não encontrado.");
			} else {
				if (certificadoFind == null) {
					throw new NotFoundException("Certificado não encontrado.");
				} else {
					Orientador orientador = orientadorFind.get();
					CertificadoDeEstagio certificado = certificadoFind.get();

					if (certificado.getEstagio().getOrientador().getId() != orientador.getId()) {
						throw new NotFoundException("Este estágio não foi orientado pelo orientador selecionado.");
					} else {
						if (String.valueOf(certificado.getEstagio().getTipoEstagio())
								.equals(EnumTipoEstagio.Obrigatorio)) {
							throw new InvalidFieldException("O estágio é do tipo 'Obrigatório'");
						} else {
							byte[] pdf = geradorService.gerarPdfCertificadoOrientador(orientador, certificado);

							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_PDF);
							headers.setContentDisposition(ContentDisposition.builder("inline").filename(
									orientador.getId() + "-certificado-de-estagio-" + certificado.getId() + ".pdf")
									.build());

							return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
						}

					}

				}
			}

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do orientador deve ser um inteiro!");
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