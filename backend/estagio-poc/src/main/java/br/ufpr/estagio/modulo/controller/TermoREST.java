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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.PlanoDeAtividadesDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.AgenteIntegradorService;
import br.ufpr.estagio.modulo.service.ApoliceService;
import br.ufpr.estagio.modulo.service.ContratanteService;
import br.ufpr.estagio.modulo.service.OrientadorService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/termo")
public class TermoREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;
	@Autowired
	private OrientadorService orientadorService;
	@Autowired
	private AgenteIntegradorService agenteIntegradorService;
	@Autowired
	private ApoliceService apoliceService;
	@Autowired
	private ContratanteService contratanteService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("")
	public ResponseEntity<Object> inserir(@RequestBody TermoDeEstagioDTO termo) {
		try {
			TermoDeEstagio newTermo = termoDeEstagioService.salvar(mapper.map(termo, TermoDeEstagio.class));
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newTermo, TermoDeEstagioDTO.class));
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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

	@GetMapping("")
	public ResponseEntity<Object> listarTermoDeCompromissoFiltro(@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) Optional<EnumStatusTermo> status,
			@RequestParam(required = false) Optional<EnumEtapaFluxo> etapa,
			@RequestParam(required = false) Optional<EnumTipoEstagio> tipoEstagio,
			@RequestParam(required = false) Optional<EnumTipoTermoDeEstagio> tipoTermo,
			@RequestParam(required = false) Optional<String> grr) {
		try {
			Optional<EnumStatusTermo> statusTermo = status == null ? Optional.empty() : status;
			Optional<EnumEtapaFluxo> etapaTermo = etapa == null ? Optional.empty() : etapa;
			Optional<EnumTipoEstagio> tipoEstagioTermo = tipoEstagio == null ? Optional.empty() : tipoEstagio;
			Optional<EnumTipoTermoDeEstagio> tipoTermoEstagio = tipoTermo == null ? Optional.empty() : tipoTermo;
			Optional<String> grrTermo = grr == null ? Optional.empty() : grr;

			Page<TermoDeEstagio> paginaTermos = termoDeEstagioService.listarTermoCompromissoPaginated(page, statusTermo,
					etapaTermo, tipoEstagioTermo, tipoTermoEstagio, grrTermo);

			if (paginaTermos.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = paginaTermos.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, paginaTermos.getPageable(), paginaTermos.getTotalElements()));
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

	@GetMapping("/{id}")
	public ResponseEntity<Object> listarTermo(@PathVariable String id) {
		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termoOptional.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termo = termoOptional.get();
				TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
				termoDTO.setAluno(termo.getEstagio().getAluno().getNome());
				termoDTO.setGrrAluno(termo.getEstagio().getAluno().getMatricula());
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
			ErrorResponse response = new ErrorResponse("O ID não é do tipo de dado esperado!");
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

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody TermoDeEstagioDTO termo) {
		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService.atualizarDados(termofind, termo);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
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
			ErrorResponse response = new ErrorResponse("O ID não é do tipo de dado esperado!");
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

	@PutMapping("/{id}/planoAtividades")
	public ResponseEntity<Object> atualizarPlanoAtividades(@PathVariable String id,
			@RequestBody PlanoDeAtividadesDTO planoAtividades) {
		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService.atualizarPlanoAtividades(termofind,
						planoAtividades);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
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
			ErrorResponse response = new ErrorResponse("O ID não é do tipo de dado esperado!");
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

	@PutMapping("/{termoId}/associarOrientador/{orientadorId}")
	public ResponseEntity<Object> associarOrientador(@PathVariable String termoId, @PathVariable String orientadorId) {
		try {
			long idLong = Long.parseLong(termoId);

			if (idLong < 1L)
				throw new InvalidFieldException("Id do termo inválido.");

			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}

			long idOrientadorLong = Long.parseLong(orientadorId);

			if (idOrientadorLong < 1L)
				throw new InvalidFieldException("Id do orientador inválido.");
			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idOrientadorLong);
			if (orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService
						.associarOrientadorAoTermoDeEstagio(termofind.get(), orientadorFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
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
			ErrorResponse response = new ErrorResponse("O ID não é do tipo de dado esperado!");
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

	@PutMapping("/{termoId}/associarAgenteIntegrador/{agenteId}")
	public ResponseEntity<Object> associarAgenteIntegrador(@PathVariable String termoId,
			@PathVariable String agenteId) {
		try {
			long idLong = Long.parseLong(termoId);

			if (idLong < 1L)
				throw new InvalidFieldException("Id do termo inválido.");

			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}

			long idAgenteLong = Long.parseLong(agenteId);

			if (idAgenteLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<AgenteIntegrador> agenteFind = agenteIntegradorService.buscarPorId(idAgenteLong);
			if (agenteFind.isEmpty()) {
				throw new NotFoundException("Agente integrador não encontrado!");
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService
						.associarAgenteIntegradorAoTermoDeEstagio(termofind.get(), agenteFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
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
			ErrorResponse response = new ErrorResponse("O ID não é do tipo de dado esperado!");
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
	public ResponseEntity<Object> associarApolice(@PathVariable String termoId, @PathVariable String apoliceId) {
		try {
			long idLong = Long.parseLong(termoId);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}
			long idApoliceLong = Long.parseLong(apoliceId);

			if (idApoliceLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<Apolice> apoliceFind = apoliceService.buscarPorId(idApoliceLong);
			if (apoliceFind.isEmpty()) {
				throw new NotFoundException("Apolice não encontrada!");
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService.associarApoliceAoTermoDeEstagio(termofind.get(),
						apoliceFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
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
			ErrorResponse response = new ErrorResponse("O ID não é do tipo de dado esperado!");
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

	@PutMapping("/{termoId}/associarContratante/{contratanteId}")
	public ResponseEntity<Object> associarContratante(@PathVariable String termoId,
			@PathVariable String contratanteId) {
		try {
			long idLong = Long.parseLong(termoId);

			if (idLong < 1L)
				throw new InvalidFieldException("Id do termo inválido.");

			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}

			long idContratanteLong = Long.parseLong(contratanteId);

			if (idContratanteLong < 1L)
				throw new InvalidFieldException("Id do contratante inválido.");

			Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idContratanteLong);
			if (contratanteFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService
						.associarContratanteAoTermoDeEstagio(termofind.get(), contratanteFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
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
			ErrorResponse response = new ErrorResponse("O ID não é do tipo de dado esperado!");
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

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable String id) {
		try {
			long idLong = Long.parseLong(id);

			if (idLong < 1L)
				throw new InvalidFieldException("Id inválido.");

			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(idLong);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				termoDeEstagioService.deletar(idLong);
				return ResponseEntity.status(HttpStatus.OK).body(null);
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
			ErrorResponse response = new ErrorResponse("O ID não é do tipo de dado esperado!");
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
