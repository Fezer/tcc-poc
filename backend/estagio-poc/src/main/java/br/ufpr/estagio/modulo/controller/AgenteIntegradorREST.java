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

import br.ufpr.estagio.modulo.dto.AgenteIntegradorDTO;
import br.ufpr.estagio.modulo.dto.ApoliceDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.service.AgenteIntegradorService;
import br.ufpr.estagio.modulo.service.ConvenioService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.SeguradoraService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/agente-integrador")
public class AgenteIntegradorREST {
	
	@Autowired
    private AgenteIntegradorService agenteIntegradorService;
    
    @Autowired
    private ConvenioService convenioService;
    
    @Autowired
    private EstagioService estagioService;

    @Autowired
    private TermoDeEstagioService termoDeEstagioService;
    
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/novo")
	public ResponseEntity<AgenteIntegradorDTO> novoAgenteIntegrador(@RequestBody AgenteIntegradorDTO agenteIntegradorDTO){
		try {
			AgenteIntegrador agenteIntegrador = mapper.map(agenteIntegradorDTO, AgenteIntegrador.class);
			
			agenteIntegrador.setConvenio(agenteIntegradorDTO.getConvenio());
			agenteIntegrador.setTermoDeEstagio(agenteIntegradorDTO.getTermoDeEstagio());
			agenteIntegrador.setEstagio(agenteIntegradorDTO.getEstagio());
		    
			agenteIntegrador = agenteIntegradorService.criarAgenteIntegrador(agenteIntegrador);
			
			agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTO.class);
			
			return new ResponseEntity<>(agenteIntegradorDTO, HttpStatus.CREATED);	
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<AgenteIntegradorDTO> buscarAgenteIntegradorPorId(@PathVariable Integer id) {
	    Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(id);
	    AgenteIntegradorDTO agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(agenteIntegradorDTO);
	}

	@GetMapping("/")
	public ResponseEntity<List<ApoliceDTO>> listarApolices() {
	    List<AgenteIntegrador> agentesIntegradores = agenteIntegradorService.listarAgentesIntegradores();
	    List<ApoliceDTO> agentesIntegradoresDTO = agentesIntegradores.stream()
	            .map(ap -> mapper.map(ap, ApoliceDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(agentesIntegradoresDTO);
	}

}
