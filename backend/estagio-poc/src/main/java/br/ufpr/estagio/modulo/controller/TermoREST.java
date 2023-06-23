package br.ufpr.estagio.modulo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.PlanoDeAtividadesDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
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
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("")
	public ResponseEntity<Page<TermoDeEstagioDTO>> listarTermoDeCompromissoFiltro(
			@RequestParam(defaultValue = "0") int page,
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

			Page<TermoDeEstagio> paginaTermos = termoDeEstagioService
					.listarTermoCompromissoPaginated(page,
							statusTermo,
							etapaTermo,
							tipoEstagioTermo,
							tipoTermoEstagio,
							grrTermo);

			if (paginaTermos.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				List<TermoDeEstagioDTO> listaTermosDTO = paginaTermos.getContent().stream()
						.map(e -> mapper.map(e, TermoDeEstagioDTO.class))
						.collect(Collectors.toList());

				return ResponseEntity.status(HttpStatus.OK).body(
						new PageImpl<>(listaTermosDTO, paginaTermos.getPageable(), paginaTermos.getTotalElements()));
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> listarTermo(@PathVariable Long id) {
		try {
			Optional<TermoDeEstagio> termoOptional = termoDeEstagioService.buscarPorIdOptional(id);
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
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody TermoDeEstagioDTO termo) {
		try {
			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(id);
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
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/{id}/planoAtividades")
	public ResponseEntity<Object> atualizarPlanoAtividades(@PathVariable Long id,
			@RequestBody PlanoDeAtividadesDTO planoAtividades) {
		try {
			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(id);
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
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/{termoId}/associarOrientador/{orientadorId}")
	public ResponseEntity<Object> associarOrientador(@PathVariable Long termoId, @PathVariable Long orientadorId) {
		try {
			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(termoId);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}
			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(orientadorId);
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
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/{termoId}/associarAgenteIntegrador/{agenteId}")
	public ResponseEntity<Object> associarAgenteIntegrador(@PathVariable Long termoId, @PathVariable Long agenteId) {
		try {
			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(termoId);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}
			Optional<AgenteIntegrador> agenteFind = agenteIntegradorService.buscarPorId(agenteId);
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
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/{termoId}/associarApolice/{apoliceId}")
	public ResponseEntity<Object> associarApolice(@PathVariable Long termoId, @PathVariable Long apoliceId) {
		try {
			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(termoId);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}
			Optional<Apolice> apoliceFind = apoliceService.buscarPorId(apoliceId);
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
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/{termoId}/associarContratante/{contratanteId}")
	public ResponseEntity<Object> associarContratante(@PathVariable Long termoId, @PathVariable Long contratanteId) {
		try {
			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(termoId);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}
			Optional<Contratante> contratanteFind = contratanteService.buscarPorId(contratanteId);
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
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		try {
			Optional<TermoDeEstagio> termofind = termoDeEstagioService.buscarPorIdOptional(id);
			if (termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			} else {
				termoDeEstagioService.deletar(id);
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse response = new ErrorResponse(
					"Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
