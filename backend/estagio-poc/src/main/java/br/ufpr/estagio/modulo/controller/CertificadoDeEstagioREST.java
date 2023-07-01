package br.ufpr.estagio.modulo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.CertificadoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/certificadoDeEstagio")
public class CertificadoDeEstagioREST {

	@Autowired
	private CertificadoDeEstagioService certificadoDeEstagioService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/")
	public ResponseEntity<Object> criarCertificadoDeEstagio() {
		try {
			CertificadoDeEstagio certificadoDeEstagio = new CertificadoDeEstagio();
			certificadoDeEstagio = certificadoDeEstagioService.novoCertificadoDeEstagio(certificadoDeEstagio);

			CertificadoDeEstagioDTO certificadoDeEstagioDTO = mapper.map(certificadoDeEstagio,
					CertificadoDeEstagioDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(certificadoDeEstagioDTO);
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

	@GetMapping("/")
	public ResponseEntity<Object> listarCertificadosDeEstagio(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) Optional<String> grrAluno,
			@RequestParam(required = false) Optional<EnumEtapaFluxo> etapaFluxo

	) {
		try {
			Optional<String> grrOptional = grrAluno == null ? Optional.empty() : grrAluno;
			Optional<EnumEtapaFluxo> etapaFluxoOptional = etapaFluxo == null ? Optional.empty() : etapaFluxo;

			Page<CertificadoDeEstagio> listaCertificadosDeEstagio = certificadoDeEstagioService
					.listarCertificadoPaginated(
							page,
							grrOptional,
							etapaFluxoOptional);

			if (listaCertificadosDeEstagio.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}

			List<CertificadoDeEstagioDTO> listaCertificadosDeEstagioDTO = listaCertificadosDeEstagio.stream()
					.map(ap -> mapper.map(ap, CertificadoDeEstagioDTO.class))
					.collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(
					new PageImpl<>(listaCertificadosDeEstagioDTO, listaCertificadosDeEstagio.getPageable(),
							listaCertificadosDeEstagio.getTotalElements()));
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

	@GetMapping("/{idCertificadoDeEstagio}")
	public ResponseEntity<Object> buscarCertificadoDeEstagioPorId(@PathVariable String idCertificadoDeEstagio) {
		try {
			long idLongCertificadoDeEstagio = Long.parseLong(idCertificadoDeEstagio);

			if (idLongCertificadoDeEstagio < 1) {
				throw new InvalidFieldException("Id do certificado de estágio inválido!");
			}

			Optional<CertificadoDeEstagio> certificadoDeEstagio = certificadoDeEstagioService
					.buscarCertificadoDeEstagioPorId(idLongCertificadoDeEstagio);

			if (certificadoDeEstagio.isEmpty()) {
				throw new NotFoundException("Certificado de estágio não encontrado!");
			}

			CertificadoDeEstagioDTO certificadoDeEstagioDTO = mapper.map(certificadoDeEstagio.get(),
					CertificadoDeEstagioDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(certificadoDeEstagioDTO);
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do estágio deve ser um número!");
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

	@DeleteMapping("/{idCertificadoDeEstagio}")
	public ResponseEntity<?> excluirCertificadoDeEstagio(@PathVariable String idCertificadoDeEstagio) {
		try {
			long idLongCertificadoDeEstagio = Long.parseLong(idCertificadoDeEstagio);

			if (idLongCertificadoDeEstagio < 1) {
				throw new InvalidFieldException("Id do certificado de estágio inválido!");
			}

			Optional<CertificadoDeEstagio> certificadoDeEstagio = certificadoDeEstagioService
					.buscarCertificadoDeEstagioPorId(idLongCertificadoDeEstagio);

			if (certificadoDeEstagio.isEmpty()) {
				throw new NotFoundException("Certificado de estágio não encontrado!");
			}

			certificadoDeEstagioService.deletarCertificadoDeEstagio(certificadoDeEstagio.get());

			return ResponseEntity.noContent().build();

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("Id do estágio deve ser um número!");
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
