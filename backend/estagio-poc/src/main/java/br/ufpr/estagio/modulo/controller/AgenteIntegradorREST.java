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

import br.ufpr.estagio.modulo.dto.AgenteIntegradorDTO;
import br.ufpr.estagio.modulo.dto.AgenteIntegradorDTOv2;
import br.ufpr.estagio.modulo.dto.ConvenioDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.service.AgenteIntegradorService;
import br.ufpr.estagio.modulo.service.ConvenioService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/agente-integrador")
public class AgenteIntegradorREST {

	@Autowired
	private AgenteIntegradorService agenteIntegradorService;
	
	@Autowired
	private EstagioService estagioService;
	
	@Autowired
	private TermoDeEstagioService termoDeEstagioService;

	@Autowired
	private ConvenioService convenioService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/")
	public ResponseEntity<Object> criarAgenteIntegrador(@RequestBody AgenteIntegradorDTOv2 agenteIntegradorDTO) {
		try {
			if (agenteIntegradorDTO.getNome() == null || agenteIntegradorDTO.getNome().isEmpty()) {
				throw new InvalidFieldException("Nome inválido.");
			}
			if (agenteIntegradorDTO.getTelefone() == null || agenteIntegradorDTO.getTelefone().isEmpty()) {
				throw new InvalidFieldException("Telefone inválido.");
			}
			if (agenteIntegradorDTO.getCnpj() == null || agenteIntegradorDTO.getCnpj().isEmpty()) {
				throw new InvalidFieldException("CNPJ inválido.");
			}
			AgenteIntegrador agenteIntegrador = mapper.map(agenteIntegradorDTO, AgenteIntegrador.class);
			agenteIntegrador = agenteIntegradorService.criarAgenteIntegrador(agenteIntegrador);
			agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTOv2.class);
			// return new ResponseEntity<>(agenteIntegradorDTO, HttpStatus.CREATED);
			return ResponseEntity.status(HttpStatus.CREATED).body(agenteIntegradorDTO);
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
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

	@PostMapping("/{idAgente}/convenio")
	public ResponseEntity<Object> criarConvenio(@PathVariable String idAgente, @RequestBody ConvenioDTO convenio) {
		try {
			int idInt = Integer.parseInt(idAgente);

			if (convenio.getNumero() < 1) {
				throw new InvalidFieldException("Número inválido.");
			}
			if (convenio.getDescricao() == null || convenio.getDescricao().isEmpty()) {
				throw new InvalidFieldException("Preencha a descrição.");
			}
			if (convenio.getDataInicio() == null) {
				throw new InvalidFieldException("Insira uma data de início.");
			}
			if (convenio.getDataFim() == null) {
				throw new InvalidFieldException("Insira uma data de fim.");
			}
			if (convenio.getDataFim().before(convenio.getDataInicio())) {
				throw new InvalidFieldException("A data de fim deve ser posterior à data de início");
			}

			Optional<AgenteIntegrador> agenteFind = agenteIntegradorService.buscarPorId(idInt);

			if (agenteFind.isEmpty()) {
				throw new NotFoundException("Agente integrador não encontrado!");
			}

			AgenteIntegrador agente = agenteFind.get();
			Convenio convenioNovo = mapper.map(convenio, Convenio.class);
			convenioNovo = convenioService.criarConvenio(convenioNovo);
			convenioNovo = convenioService.associarAgenteAoConvenio(convenioNovo, agente);
			convenio = mapper.map(convenioNovo, ConvenioDTO.class);
			// return new ResponseEntity<>(convenio, HttpStatus.CREATED);
			return ResponseEntity.status(HttpStatus.CREATED).body(convenio);

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
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

	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarAgenteIntegradorPorId(@PathVariable String id) {
		try {
			int idInt = Integer.parseInt(id);

			if (idInt < 1) {
				throw new InvalidFieldException("Id do agente integrador inválido!");
			}

			Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(idInt);

			if (agenteIntegrador.isEmpty()) {
				throw new NotFoundException("Agente integrador não encontrado!");
			}

			AgenteIntegradorDTOv2 agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTOv2.class);
			return ResponseEntity.status(HttpStatus.OK).body(agenteIntegradorDTO);

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
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

	@GetMapping("/")
	public ResponseEntity<Object> listarAgentesIntegradores(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false) Optional<String> nome,
			@RequestParam(required = false) Optional<String> cnpj) {
		try {
			Optional<String> nomeOptional = nome == null ? Optional.empty() : nome;
			Optional<String> cnpjOptional = cnpj == null ? Optional.empty() : cnpj;

			Page<AgenteIntegrador> agentesIntegradores = agenteIntegradorService.listarAgentesPaginated(
					page, nomeOptional, cnpjOptional);

			if (agentesIntegradores.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}

			List<AgenteIntegradorDTO> agentesDTO = agentesIntegradores.stream()
					.map(ap -> mapper.map(ap, AgenteIntegradorDTO.class))
					.collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(
					new PageImpl<>(agentesDTO, agentesIntegradores.getPageable(),
							agentesIntegradores.getTotalElements()));

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
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

	@GetMapping("/todos")
	public ResponseEntity<Object> lsitarTodosAgentesIntegradores() {
		try {
			List<AgenteIntegrador> agentesIntegradores = agenteIntegradorService.listarAgentesIntegradores();

			if (agentesIntegradores.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}

			List<AgenteIntegradorDTO> agentesDTO = agentesIntegradores.stream()
					.map(ap -> mapper.map(ap, AgenteIntegradorDTO.class))
					.collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(agentesDTO);

		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
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
	public ResponseEntity<Object> atualizarAgenteIntegrador(@PathVariable String id,
			@RequestBody AgenteIntegradorDTO agenteIntegradorDTO) {
		try {
			int idInt = Integer.parseInt(id);

			if (idInt < 1) {
				throw new InvalidFieldException("Id da seguradora inválido!");
			}

			Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(idInt);

			if (agenteIntegrador.isPresent()) {
				if (agenteIntegradorDTO.getNome().isEmpty())
					throw new InvalidFieldException("Nome inválido.");

				if (agenteIntegradorDTO.getTelefone().isEmpty())
					throw new InvalidFieldException("Telefone inválido.");

				if (agenteIntegradorDTO.getCnpj().isEmpty())
					throw new InvalidFieldException("CNPJ inválido.");

				AgenteIntegrador agenteIntegradorAtualizado = mapper.map(agenteIntegradorDTO, AgenteIntegrador.class);
				agenteIntegradorAtualizado.setId(idInt);
				agenteIntegradorAtualizado = agenteIntegradorService
						.atualizarAgenteIntegrador(agenteIntegradorAtualizado);
				AgenteIntegradorDTO agenteIntegradorDTOAtualizado = mapper.map(agenteIntegradorAtualizado,
						AgenteIntegradorDTO.class);
				return ResponseEntity.ok().body(agenteIntegradorDTOAtualizado);
			} else {
				throw new NotFoundException("Agente integrador não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
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
	
	@PutMapping("/{id}/alterar-status")
	public ResponseEntity<Object> alterarStatusAgenteIntegrador(@PathVariable String id) {
		try {
			int idInt = Integer.parseInt(id);

			if (idInt < 1) {
				throw new InvalidFieldException("Id da seguradora inválido!");
			}

			Optional<AgenteIntegrador> agenteIntegradorFind = agenteIntegradorService.buscarPorId(idInt);
			
			if (agenteIntegradorFind.isPresent()) {
				AgenteIntegrador agenteIntegrador = agenteIntegradorFind.get();
				
				if (agenteIntegrador.isAtivo())
					agenteIntegrador.setAtivo(false);
				
				else if (!agenteIntegrador.isAtivo())
					agenteIntegrador.setAtivo(true);

				//AgenteIntegrador agenteIntegradorAtualizado = mapper.map(agenteIntegrador, AgenteIntegrador.class);
				agenteIntegrador.setId(idInt);
				agenteIntegrador = agenteIntegradorService
						.atualizarAgenteIntegrador(agenteIntegrador);
				
				AgenteIntegradorDTO agenteIntegradorDTOAtualizado = mapper.map(agenteIntegrador,
						AgenteIntegradorDTO.class);
				return ResponseEntity.ok().body(agenteIntegradorDTOAtualizado);
			} else {
				throw new NotFoundException("Agente integrador não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirAgenteIntegrador(@PathVariable String id) {
		try {
			int idInt = Integer.parseInt(id);

			if (idInt < 1) {
				throw new InvalidFieldException("Id da seguradora inválido!");
			}

			Optional<AgenteIntegrador> agenteIntegradorFind = agenteIntegradorService.buscarPorId(idInt);
			if (agenteIntegradorFind.isPresent()) {
				AgenteIntegrador agenteIntegrador = agenteIntegradorFind.get();
				
				boolean presenteEmTermosDeEstagio = termoDeEstagioService.listarTermosDeEstagioPorAgenteIntegrador(agenteIntegrador);
				boolean presenteEmEstagios = estagioService.listarEstagiosPorAgenteIntegrador(agenteIntegrador);
				
				if (presenteEmTermosDeEstagio)
					throw new InvalidFieldException("Não é possível excluir um agente integrador presente em termo de estágio.");
				
				if (presenteEmEstagios)
					throw new InvalidFieldException("Não é possível excluir um agente integrador presente em estágio.");
				
				agenteIntegradorService.excluirAgenteIntegrador(agenteIntegrador);
				return ResponseEntity.noContent().build();
			} else {
				throw new NotFoundException("Agente integrador não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse("O id deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
