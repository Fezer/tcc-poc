package br.ufpr.estagio.modulo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.ApoliceDTO;
import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.ApoliceService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.SeguradoraService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

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
	public ResponseEntity<ApoliceDTO> novoApolice(@RequestBody ApoliceDTO apoliceDTO){
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
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApoliceDTO> buscarApolicePorId(@PathVariable Integer id) {
	    Optional<Apolice> apolice = apoliceService.buscarPorId(id);
	    ApoliceDTO apoliceDTO = mapper.map(apolice, ApoliceDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(apoliceDTO);
	}

	@GetMapping("/")
	public ResponseEntity<List<ApoliceDTO>> listarApolices() {
	    List<Apolice> apolices = apoliceService.listarApolices();
	    List<ApoliceDTO> apolicesDTO = apolices.stream()
	            .map(ap -> mapper.map(ap, ApoliceDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(apolicesDTO);
	}
	
}
