package br.ufpr.estagio.modulo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.ApoliceService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.SeguradoraService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import jakarta.persistence.PersistenceException;

@CrossOrigin
@RestController
@RequestMapping("/apolice")
public class ApoliceREST {
    
    @Autowired
    private ApoliceService apoliceService;
    
    @Autowired
    private SeguradoraService seguradoraService;
    
    @Autowired
    private EstagioService estagioService;

    @Autowired
    private TermoDeEstagioService termoDeEstagioService;
    
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
	public ResponseEntity<ApoliceDTO> buscarApolicePorId(@PathVariable Long id) {
	    try {
	        Optional<Apolice> apolice = apoliceService.buscarPorId(id);

	        if (apolice.isEmpty() || apolice == null) {
	        	// Não é legal, mas do jeito legal não funcionou
	            throw new PocException(HttpStatus.NOT_FOUND, "A apólice não foi encontrada.");
	        }

	        ApoliceDTO apoliceDTO = mapper.map(apolice, ApoliceDTO.class);

	        return ResponseEntity.ok(apoliceDTO);
	    } catch (PocException e) {
	    	// GATO (PRETO!!)... aparentemente, para utilizar mais de uma classe `ControllerAdvice`, eh necessario
	    	// utilizar mais uma anotação (@Order)
	        throw new NotFoundException("A apólice não foi encontrada.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar apólice!");
	    }
	}


	@GetMapping("/")
	public ResponseEntity<List<ApoliceDTO>> listarApolices() {
		try {
			List<Apolice> apolices = apoliceService.listarApolices();
			List<ApoliceDTO> apolicesDTO = apolices.stream()
					.map(ap -> mapper.map(ap, ApoliceDTO.class))
					.collect(Collectors.toList());
			
			return ResponseEntity.ok().body(apolicesDTO);
		} // TO-DO: Adicionar exceção de sql
			catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar todas as apólices!");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApoliceDTO> atualizarApolice(@PathVariable Long id, @RequestBody ApoliceDTO apoliceDTO){
	    
		try {
			Optional<Apolice> apolice = apoliceService.buscarPorId(id);
	    
			if(apolice.isPresent()) {
				
				Apolice apoliceAtualizada = mapper.map(apoliceDTO, Apolice.class);
				apoliceAtualizada.setId(id);
				
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar apólice!");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirApolice(@PathVariable Long id){
	    Optional<Apolice> apolice = apoliceService.buscarPorId(id);
	    
	    if(apolice.isPresent()) {
	        apoliceService.excluirApolice(apolice.get());
	        return ResponseEntity.noContent().build();
	    } else {
	    	throw new NotFoundException("A apólice não foi encontrada.");
	    }
	}

	
}
