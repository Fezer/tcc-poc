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
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.service.ConvenioService;

@CrossOrigin
@RestController
@RequestMapping("/convenio")
public class ConvenioREST {
    
    @Autowired
    private ConvenioService convenioService;
    
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/novo")
	public ResponseEntity<ConvenioDTO> novoConvenio(@RequestBody ConvenioDTO convenioDTO){
		try {
			Convenio convenio = mapper.map(convenioDTO, Convenio.class);
			
			convenio.setAgenteIntegrador(convenioDTO.getAgenteIntegrador());
		    
			convenio = convenioService.criarConvenio(convenio);
			
			convenioDTO = mapper.map(convenio, ConvenioDTO.class);
			
			return new ResponseEntity<>(convenioDTO, HttpStatus.CREATED);	
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
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
			ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
		    ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um número!");
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
		    ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<ConvenioDTO>> listarApolices() {
	    try {
	    	List<Convenio> convenios = convenioService.listarConvenios();
	    	List<ConvenioDTO> conveniosDTO = convenios.stream()
	    			.map(ap -> mapper.map(ap, ConvenioDTO.class))
	    			.collect(Collectors.toList());
	    	return ResponseEntity.ok().body(conveniosDTO);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
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
			ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
		    ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um número!");
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
		    ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirConvenio(@PathVariable String id){
	    try {
	    	long idLong = Long.parseLong(id);	
	    	
			if (idLong < 1) {
				throw new InvalidFieldException("Id do convênio inválido!");
			}
	    
			Optional<Convenio> convenio = convenioService.buscarPorId(idLong);
			
		    if(convenio.isPresent()) {
		    	convenioService.excluirConvenio(convenio.get());
		        return ResponseEntity.noContent().build();
		    } else {
		    	throw new NotFoundException("Convênio não encontrado!");
		    }
	    } catch (NotFoundException ex) {
			ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (NumberFormatException ex) {
		    ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um número!");
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
		    ErrorResponse response = new ErrorResponse(ex.getMessage());
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
}
