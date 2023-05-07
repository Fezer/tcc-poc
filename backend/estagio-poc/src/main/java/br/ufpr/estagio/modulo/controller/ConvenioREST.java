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
	public ResponseEntity<ConvenioDTO> buscarConvenioPorId(@PathVariable Long id) {
	    Optional<Convenio> convenio = convenioService.buscarPorId(id);
	    if(convenio.isEmpty()) {
            throw new NotFoundException("Convênio não encontrado!");
        }
	    ConvenioDTO convenioDTO = mapper.map(convenio, ConvenioDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(convenioDTO);
	}

	@GetMapping("/")
	public ResponseEntity<List<ConvenioDTO>> listarApolices() {
	    List<Convenio> convenios = convenioService.listarConvenios();
	    List<ConvenioDTO> conveniosDTO = convenios.stream()
	            .map(ap -> mapper.map(ap, ConvenioDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(conveniosDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ConvenioDTO> atualizarConvenio(@PathVariable Long id, @RequestBody ConvenioDTO convenioDTO){
	    Optional<Convenio> agenteIntegrador = convenioService.buscarPorId(id);
	    
	    if(agenteIntegrador.isPresent()) {
	    	Convenio convenioAtualizado = mapper.map(convenioDTO, Convenio.class);
	    	convenioAtualizado.setId(id);
	    	convenioAtualizado = convenioService.atualizarConvenio(convenioAtualizado);
	    	ConvenioDTO convenioDTOAtualizado = mapper.map(convenioAtualizado, ConvenioDTO.class);
	        return ResponseEntity.ok().body(convenioDTOAtualizado);
	    } else {
//	        return ResponseEntity.notFound().build();
	        throw new NotFoundException("Convênio não encontrado!");
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirConvenio(@PathVariable Long id){
	    Optional<Convenio> convenio = convenioService.buscarPorId(id);
	    if(convenio.isPresent()) {
	    	convenioService.excluirConvenio(convenio.get());
	        return ResponseEntity.noContent().build();
	    } else {
	//        return ResponseEntity.notFound().build();
	    	throw new NotFoundException("Convênio não encontrado!");
	    }
	}
}
