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

import br.ufpr.estagio.modulo.dto.ContratanteDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.service.ContratanteService;


@CrossOrigin
@RestController
@RequestMapping("/contratante")
public class ContratanteREST {
	
	@Autowired
    private ContratanteService contratanteService;
        
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
    
    // TO-DO: Adicionar validação para garantir que contratante tem CPF **OU** CNPJ
    @PostMapping("/")
	public ResponseEntity<ContratanteDTO> criarContratante(@RequestBody ContratanteDTO contratanteDTO){
		try {
			Contratante contratante = mapper.map(contratanteDTO, Contratante.class);
		    
			contratante = contratanteService.criarContratante(contratante);
			
			contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
			
			return new ResponseEntity<>(contratanteDTO, HttpStatus.CREATED);	
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<ContratanteDTO> buscarContratantePorId(@PathVariable Long id) {
	    Optional<Contratante> contratante = contratanteService.buscarPorId(id);
	    ContratanteDTO contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(contratanteDTO);
	}
    
    @GetMapping("/nome/{nomeContratante}")
	public ResponseEntity<ContratanteDTO> buscarContratanteNome(@PathVariable String nomeContratante) {
	    Optional<Contratante> contratanteFind = contratanteService.buscarPorNome(nomeContratante);
		if(contratanteFind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Contratante não encontrado!");
		} else {
		    ContratanteDTO contratanteDTO = mapper.map(contratanteFind.get(), ContratanteDTO.class);
		    return ResponseEntity.status(HttpStatus.OK).body(contratanteDTO);
		}
	}
    
    @GetMapping("/nome/contendo/{nomeContratante}")
	public ResponseEntity<List<ContratanteDTO>> buscarContratantePorNomeSimilar(@PathVariable String nomeContratante) {
	    List<Contratante> contratantesFind = contratanteService.buscarPorNomeContendo(nomeContratante);
		if(contratantesFind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Contratante não encontrado!");
		} else {
		    List<ContratanteDTO> contratantesDTO = contratantesFind.stream()
		            .map(ap -> mapper.map(ap, ContratanteDTO.class))
		            .collect(Collectors.toList());
		    return ResponseEntity.ok().body(contratantesDTO);
		}
	}
    
    @GetMapping("/nome/comecandoPor/{nomeContratante}")
	public ResponseEntity<List<ContratanteDTO>> buscarContratantePorNomeComecandoPor(@PathVariable String nomeContratante) {
	    List<Contratante> contratantesFind = contratanteService.buscarContratantePorNomeComecandoPor(nomeContratante);
		if(contratantesFind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Contratante não encontrado!");
		} else {
		    List<ContratanteDTO> contratantesDTO = contratantesFind.stream()
		            .map(ap -> mapper.map(ap, ContratanteDTO.class))
		            .collect(Collectors.toList());
		    return ResponseEntity.ok().body(contratantesDTO);
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<ContratanteDTO>> listarContratantes() {
	    List<Contratante> contratantes = contratanteService.listarContratantes();
	    List<ContratanteDTO> contratantesDTO = contratantes.stream()
	            .map(ap -> mapper.map(ap, ContratanteDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(contratantesDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContratanteDTO> atualizarContratante(@PathVariable Long id, @RequestBody ContratanteDTO contratanteDTO){
	    Optional<Contratante> contratante = contratanteService.buscarPorId(id);
	    
	    if(contratante.isPresent()) {
	    	Contratante contratanteAtualizado = mapper.map(contratanteDTO, Contratante.class);
	    	contratanteAtualizado.setId(id);
	    	contratanteAtualizado = contratanteService.atualizarContratante(contratanteAtualizado);
	        ContratanteDTO contratanteDTOAtualizado = mapper.map(contratanteAtualizado, ContratanteDTO.class);
	        return ResponseEntity.ok().body(contratanteDTOAtualizado);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirContratante(@PathVariable Long id){
	    Optional<Contratante> contratante = contratanteService.buscarPorId(id);
	    if(contratante.isPresent()) {
	    	contratanteService.excluirContratante(contratante.get());
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
