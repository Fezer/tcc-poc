package br.ufpr.estagio.modulo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.ConvenioDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.service.ConvenioService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/convenio")
public class ConvenioREST {
    
    @Autowired
    private ConvenioService convenioService;
    
    @Autowired
	private EstagioService estagioService;

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;
        
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/novo")
	public ResponseEntity<Object> novoConvenio(@RequestBody ConvenioDTO convenioDTO){
		try {
			Convenio convenio = mapper.map(convenioDTO, Convenio.class);
			
			convenio.setAgenteIntegrador(convenioDTO.getAgenteIntegrador());
		    
			convenio = convenioService.criarConvenio(convenio);
			
			convenioDTO = mapper.map(convenio, ConvenioDTO.class);
			
			return new ResponseEntity<>(convenioDTO, HttpStatus.CREATED);	
			
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
    
    @GetMapping("/{id}")
	public ResponseEntity<Object> buscarConvenioPorId(@PathVariable String id) {
    	try {
    		long idLong = Long.parseLong(id);
	    	
		    if (idLong < 1) {
		   		throw new InvalidFieldException("Id do convênio inválido!");
		   	}
		    
		    Optional<Convenio> convenio = convenioService.buscarPorId(idLong);
		    
		    if(convenio.isEmpty()) {
	            throw new NotFoundException("Convênio não encontrado!");
	        }
		    
		    ConvenioDTO convenioDTO = mapper.map(convenio, ConvenioDTO.class);
		    return ResponseEntity.status(HttpStatus.OK).body(convenioDTO);
		    
    	} catch (NotFoundException ex) {
    		ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		    ErrorResponse response = new ErrorResponse("Id do convênio deve ser um número!");
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

	@GetMapping("/")
	public ResponseEntity<Object> listarApolices() {
	    try {
	    	List<Convenio> convenios = convenioService.listarConvenios();
	    	List<ConvenioDTO> conveniosDTO = convenios.stream()
	    			.map(ap -> mapper.map(ap, ConvenioDTO.class))
	    			.collect(Collectors.toList());
	    	return ResponseEntity.ok().body(conveniosDTO);
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
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarConvenio(@PathVariable String id, @RequestBody ConvenioDTO convenioDTO){
		try {
			long idLong = Long.parseLong(id);	
			if (idLong < 1) {
				throw new InvalidFieldException("Id do convênio inválido!");
			}
			Optional<Convenio> convenio = convenioService.buscarPorId(idLong);
	    
			if(convenio.isPresent()) {
				if (convenioDTO.getNumero() < 1)
	        		throw new InvalidFieldException("Número inválido.");
	        	
	        	if (convenioDTO.getDescricao() == null || convenioDTO.getDescricao().isEmpty())
	        		throw new InvalidFieldException("Preencha a descrição.");
	        	
	        	if (convenioDTO.getDataInicio() == null)
	        		throw new InvalidFieldException("Insira uma data de início.");
	        	
	        	if (convenioDTO.getDataFim() == null)
	        		throw new InvalidFieldException("Insira uma data de fim.");
	        	
	        	if (convenioDTO.getDataFim().before(convenioDTO.getDataInicio()))
	        		throw new InvalidFieldException("A data de fim deve ser posterior à data de início");
	        	
				Convenio convenioAtualizado = mapper.map(convenioDTO, Convenio.class);
				convenioAtualizado.setId(idLong);
				convenioAtualizado = convenioService.atualizarConvenio(convenioAtualizado);
				ConvenioDTO convenioDTOAtualizado = mapper.map(convenioAtualizado, ConvenioDTO.class);
				return ResponseEntity.ok().body(convenioDTOAtualizado);
			} else {
				throw new NotFoundException("Convênio não encontrado!");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		    ErrorResponse response = new ErrorResponse("Id do convênio deve ser um número!");
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
	
	@PutMapping("/{id}/alterar-status")
	public ResponseEntity<Object> alterarStatusConvenio(@PathVariable String id) {
		try {
			Long idLong = Long.parseLong(id);

			if (idLong < 1) {
				throw new InvalidFieldException("Id do convênio inválido!");
			}

			Optional<Convenio> convenioFind = convenioService.buscarPorId(idLong);
			
			if (convenioFind.isPresent()) {
				Convenio convenio = convenioFind.get();
				
				if (convenio.isAtivo())
					convenio.setAtivo(false);
				
				else if (!convenio.isAtivo())
					convenio.setAtivo(true);

				convenio.setId(idLong);
				convenio = convenioService
						.atualizarConvenio(convenio);
				
				ConvenioDTO convenioDTOAtualizado = mapper.map(convenio,
						ConvenioDTO.class);
				return ResponseEntity.ok().body(convenioDTOAtualizado);
			} else {
				throw new NotFoundException("Convênio não encontrado!");
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
	public ResponseEntity<?> excluirConvenio(@PathVariable String id){
	    try {
	    	long idLong = Long.parseLong(id);	
	    	
			if (idLong < 1) {
				throw new InvalidFieldException("Id do convênio inválido!");
			}
	    
			Optional<Convenio> convenioFind = convenioService.buscarPorId(idLong);
			
		    if(convenioFind.isPresent()) {
		    	Convenio convenio = convenioFind.get();
		    	
		    	boolean presenteEmTermosDeEstagio = termoDeEstagioService.listarTermosDeEstagioPorConvenio(convenio);
				boolean presenteEmEstagios = estagioService.listarEstagiosPorConvenio(convenio);

				if (presenteEmTermosDeEstagio)
					throw new InvalidFieldException("Não é possível excluir um convênio presente em termo de estágio.");

				if (presenteEmEstagios)
					throw new InvalidFieldException("Não é possível excluir um convênio presente em estágio.");
				
		    	convenioService.excluirConvenio(convenio);
		        return ResponseEntity.noContent().build();
		    } else {
		    	throw new NotFoundException("Convênio não encontrado!");
		    }
	    } catch (NotFoundException ex) {
	    	ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		    ErrorResponse response = new ErrorResponse("Id do convênio deve ser um número!");
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
