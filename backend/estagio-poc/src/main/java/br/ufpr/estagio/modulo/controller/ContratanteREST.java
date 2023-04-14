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

import br.ufpr.estagio.modulo.dto.ContratanteDTO;
import br.ufpr.estagio.modulo.dto.ConvenioDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.service.ContratanteService;
import br.ufpr.estagio.modulo.service.EstagioService;

@CrossOrigin
@RestController
@RequestMapping("/contratante")
public class ContratanteREST {
	
	@Autowired
    private ContratanteService contratanteService;
    
    @Autowired
    private EstagioService estagioService;
    
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/novo")
	public ResponseEntity<ContratanteDTO> novoContratante(@RequestBody ContratanteDTO contratanteDTO){
		try {
			Contratante contratante = mapper.map(contratanteDTO, Contratante.class);
			
			contratante.setEstagio(contratanteDTO.getEstagio());
		    
			contratante = contratanteService.criarContratante(contratante);
			
			contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
			
			return new ResponseEntity<>(contratanteDTO, HttpStatus.CREATED);	
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<ContratanteDTO> buscarContratantePorId(@PathVariable Integer id) {
	    Optional<Contratante> contratante = contratanteService.buscarPorId(id);
	    ContratanteDTO contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(contratanteDTO);
	}

	@GetMapping("/")
	public ResponseEntity<List<ContratanteDTO>> listarContratantes() {
	    List<Contratante> contratantes = contratanteService.listarContratantes();
	    List<ContratanteDTO> contratantesDTO = contratantes.stream()
	            .map(ap -> mapper.map(ap, ContratanteDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(contratantesDTO);
	}
}
