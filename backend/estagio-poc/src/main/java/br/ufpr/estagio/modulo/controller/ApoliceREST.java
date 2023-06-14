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

import br.ufpr.estagio.modulo.dto.ApoliceDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.service.ApoliceService;

@CrossOrigin
@RestController
@RequestMapping("/apolice")
public class ApoliceREST {
    
    @Autowired
    private ApoliceService apoliceService;
        
    @Autowired
	private ModelMapper mapper;
	
	@PostMapping("/novo")
	public ResponseEntity<Object> novoApolice(@RequestBody ApoliceDTO apoliceDTO){
		if (apoliceDTO.getNumero() < 1)
			throw new InvalidFieldException("Número inválido.");
		
		if (apoliceDTO.getDataInicio() == null)
			throw new InvalidFieldException("Data de início inválida.");
		
		if (apoliceDTO.getDataFim() == null)
			throw new InvalidFieldException("Data de fim inválida.");
		
		try {
			Apolice apolice = mapper.map(apoliceDTO, Apolice.class);
			
			//Long seguradoraId = apoliceDTO.getSeguradora() != null ? apoliceDTO.getSeguradora().getId() : null;
			//Seguradora seguradora = seguradoraId != null ? seguradoraService.buscarSeguradoraPorId(seguradoraId) : new Seguradora();
			
		    //TermoDeEstagio termoDeEstagio = termoDeEstagioService.buscarPorId(apoliceDTO.getTermoDeEstagio().getId());
		    //Estagio estagio = estagioService.buscarEstagioPorId(apoliceDTO.getEstagio().getId());
			
		    apolice.setSeguradora(apoliceDTO.getSeguradora());
		    apolice.setTermoDeEstagio(apoliceDTO.getTermoDeEstagio());
		    apolice.setEstagio(apoliceDTO.getEstagio());
		    
			apolice = apoliceService.criarApolice(apolice);
			
			apoliceDTO = mapper.map(apolice, ApoliceDTO.class);
			
			return new ResponseEntity<>(apoliceDTO, HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	/*@GetMapping("/{id}")
	public ResponseEntity<ApoliceDTO> buscarApolicePorId(@PathVariable Long id) {
		try {
			Optional<Apolice> apolice = apoliceService.buscarPorId(id);

			if (apolice.isEmpty() || apolice == null)
		    	throw new NotFoundException("A apólice não foi encontrada.");
		    	
		    ApoliceDTO apoliceDTO = mapper.map(apolice, ApoliceDTO.class);
		    
		    return ResponseEntity.status(HttpStatus.OK).body(apoliceDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar apólice!");
		}
	    
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarApolicePorId(@PathVariable String id) {
	    try {
	    	long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1L)
        		throw new InvalidFieldException("Id inválido.");
	    	
	        Optional<Apolice> apolice = apoliceService.buscarPorId(idLong);

	        if (apolice.isEmpty() || apolice == null) {
	            throw new NotFoundException("A apólice não foi encontrada.");
	        }

	        ApoliceDTO apoliceDTO = mapper.map(apolice, ApoliceDTO.class);

	        return ResponseEntity.ok(apoliceDTO);
	    } catch (NotFoundException ex) {
	    	ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id da apólice deve ser um inteiro!");
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
			List<Apolice> apolices = apoliceService.listarApolices();
			List<ApoliceDTO> apolicesDTO = apolices.stream()
					.map(ap -> mapper.map(ap, ApoliceDTO.class))
					.collect(Collectors.toList());
			
			return ResponseEntity.ok().body(apolicesDTO);
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
	public ResponseEntity<Object> atualizarApolice(@PathVariable String id, @RequestBody ApoliceDTO apoliceDTO){
	    
		try {
			long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1L)
        		throw new InvalidFieldException("Id inválido.");
	    	
			Optional<Apolice> apolice = apoliceService.buscarPorId(idLong);
	    
			if(apolice.isPresent()) {
				
				Apolice apoliceAtualizada = mapper.map(apoliceDTO, Apolice.class);
				apoliceAtualizada.setId(idLong);
				
				if (apoliceAtualizada.getNumero() < 1)
					throw new InvalidFieldException("Número inválido.");
				
				if (apoliceAtualizada.getDataInicio() == null)
					throw new InvalidFieldException("Data de início inválida.");
				
				if (apoliceAtualizada.getDataFim() == null)
					throw new InvalidFieldException("Data de fim inválida.");
				
				apoliceAtualizada = apoliceService.atualizarApolice(apoliceAtualizada);
				ApoliceDTO apoliceDTOAtualizada = mapper.map(apoliceAtualizada, ApoliceDTO.class);
	        
				return ResponseEntity.ok().body(apoliceDTOAtualizada);
			} else {
				throw new NotFoundException("A apólice não foi encontrada.");
			}
		} catch (NotFoundException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id da apólice deve ser um inteiro!");
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
	public ResponseEntity<?> excluirApolice(@PathVariable String id){
		try {
			long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1L)
	    		throw new InvalidFieldException("Id inválido.");
	    	
		    Optional<Apolice> apolice = apoliceService.buscarPorId(idLong);
		    
		    if(apolice.isPresent()) {
		        apoliceService.excluirApolice(apolice.get());
		        return ResponseEntity.noContent().build();
		    } else {
		    	throw new NotFoundException("A apólice não foi encontrada.");
		    }
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id da apólice deve ser um inteiro!");
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
