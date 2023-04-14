package br.ufpr.estagio.modulo.controller;

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
import br.ufpr.estagio.modulo.dto.SeguradoraDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.service.ApoliceService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.SeguradoraService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/seguradora")
public class SeguradoraREST {

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
	public ResponseEntity<SeguradoraDTO> novoSeguradora(@RequestBody SeguradoraDTO seguradoraDTO){
		try {
			Seguradora seguradora = mapper.map(seguradoraDTO, Seguradora.class);
			
		    seguradora.setApolice(seguradoraDTO.getApolice());
		    seguradora.setTermoDeEstagio(seguradoraDTO.getTermoDeEstagio());
		    seguradora.setEstagio(seguradoraDTO.getEstagio());
		    
		    seguradora = seguradoraService.criarSeguradora(seguradora);
			
		    seguradoraDTO = mapper.map(seguradora, SeguradoraDTO.class);
			
			return new ResponseEntity<>(seguradoraDTO, HttpStatus.CREATED);	
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<SeguradoraDTO> buscarPorId(@PathVariable Integer id) {
	    Optional<Seguradora> seguradora = seguradoraService.buscarSeguradoraPorId(id);
	    SeguradoraDTO seguradoraDTO = mapper.map(seguradora, SeguradoraDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(seguradoraDTO);
	}
    
    @GetMapping("/")
	public ResponseEntity<List<SeguradoraDTO>> listarSeguradoras() {
	    List<Seguradora> seguradoras = seguradoraService.listarSeguradoras();
	    List<SeguradoraDTO> seguradorasDTO = seguradoras.stream()
	            .map(ap -> mapper.map(ap, SeguradoraDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(seguradorasDTO);
	}
}
