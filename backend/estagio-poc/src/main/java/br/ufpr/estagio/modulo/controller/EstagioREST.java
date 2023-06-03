package br.ufpr.estagio.modulo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/estagio")
public class EstagioREST {
	
	@Autowired
    private EstagioService estagioService;
	
	@Autowired
    private TermoDeEstagioService termoDeEstagioService;
	
	/*@PostMapping("/novo")
	public ResponseEntity<EstagioDTO> novoEstagio(){
		try {
			Estagio estagio = new Estagio();
			TermoDeEstagio termoDeCompromisso = new TermoDeEstagio();
			estagio.setTermoDeCompromisso(termoDeCompromisso);
			termoDeCompromisso.setEstagio(estagio);
			
			estagio = estagioService.novoEstagio(estagio);
			termoDeCompromisso = termoDeEstagioService.novo(termoDeCompromisso);

			EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
			
			estagioDTO.add(linkTo(methodOn(EstagioREST.class).listarEstagio(estagio.getId())).withSelfRel());
			return new ResponseEntity<>(estagioDTO, HttpStatus.CREATED);			
			}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}*/
	
	@GetMapping("/")
	public ResponseEntity<List<EstagioDTO>> listarTodos(){
		try {
			List<Estagio> lista = estagioService.listarTodosEstagios();
			if(lista.isEmpty()) {
				throw new NotFoundException("Nenhum estágio encontrado!");
			} else {
				List<EstagioDTO> listaDTO = new ArrayList<EstagioDTO>();
				for(Estagio l : lista) {
					String idString = String.valueOf(l.getId());
					listaDTO.add(estagioService.toEstagioDTO(l).add(linkTo(methodOn(EstagioREST.class).listarEstagio(idString)).withSelfRel()));
				}
				return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> listarEstagio(@PathVariable String id){
		try {
			long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1) {
	    		throw new InvalidFieldException("Id do estágio inválido!");
	    	}
			
	    	Optional<Estagio> estagio = estagioService.buscarEstagioPorId(idLong);
			
	    	if(estagio.isEmpty()) {
				throw new NotFoundException("Estágio não encontrado!");
			} else {
				EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio.get());
				return new ResponseEntity<>(estagioDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/tipo/{id}")
	public ResponseEntity<Object> definirTipoEstagio(@PathVariable String id, @RequestParam String tipoEstagio){
		
		try {
			if(tipoEstagio == null) {
				throw new InvalidFieldException("Tipo do estágio não informado");
			}
			
			long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1) {
	    		throw new InvalidFieldException("Id do estágio inválido!");
	    	}
			
	    	Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idLong);
			if(estagioFind.isEmpty()) {
				throw new NotFoundException("Estágio não encontrado!");
			} else {
				EnumTipoEstagio enumTipoEstagio = EnumTipoEstagio.valueOf(tipoEstagio);
				
				Estagio estagio = estagioFind.get();
				estagio = estagioService.definirTipoEstagio(estagio, enumTipoEstagio);
				EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
				return new ResponseEntity<>(estagioDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (IllegalArgumentException ex) {
			ErrorResponse response = new ErrorResponse("Valor para definir tipo do estágio inválido.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/ufpr/{id}")
	public ResponseEntity<Object> isUfpr(@PathVariable String id, @RequestParam String estagioUfpr){
		
		try {
			if(estagioUfpr == null) {
				throw new InvalidFieldException("isUfpr não informado");
			}
			
			if(!estagioUfpr.equalsIgnoreCase("true") && (!estagioUfpr.equalsIgnoreCase("false"))) {
				throw new InvalidFieldException("isUfpr deve assumir os valores 'true' ou 'false'.");
			}
			
			long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1) {
	    		throw new InvalidFieldException("Id do estágio inválido!");
	    	}
			
	    	Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idLong);
			
	    	if(estagioFind.isEmpty()) {
				throw new NotFoundException("Estágio não encontrado!");
			} else {
				boolean booleanEstagioUfpr = Boolean.valueOf(estagioUfpr);
				
				Estagio estagio = estagioFind.get();
				estagio = estagioService.definirEstagioUfpr(estagio, booleanEstagioUfpr);
				EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
				return new ResponseEntity<>(estagioDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (IllegalArgumentException ex) {
			ErrorResponse response = new ErrorResponse("Valor para definir se o estágio é na UFPR inválido.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/seed/{id}")
	public ResponseEntity<Object> isSeed(@PathVariable String id, @RequestParam String estagioSeed){
		try {
			if(estagioSeed == null) {
				throw new InvalidFieldException("isSeed não informado");
			}
			
			if(!estagioSeed.equalsIgnoreCase("true") && (!estagioSeed.equalsIgnoreCase("false"))) {
				throw new InvalidFieldException("isUfpr deve assumir os valores 'true' ou 'false'.");
			}
			
			long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1) {
	    		throw new InvalidFieldException("Id do estágio inválido!");
	    	}
			
	    	Optional<Estagio> estagioFind = estagioService.buscarEstagioPorId(idLong);
			
	    	if(estagioFind.isEmpty()) {
				throw new NotFoundException("Estágio não encontrado!");
			} else {
				boolean booleanEstagioSeed = Boolean.valueOf(estagioSeed);
				
				Estagio estagio = estagioFind.get();
				estagio = estagioService.definirEstagioSeed(estagio, booleanEstagioSeed);
				EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
				return new ResponseEntity<>(estagioDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (IllegalArgumentException ex) {
			ErrorResponse response = new ErrorResponse("Valor para definir se o estágio é da SEED inválido.");
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
