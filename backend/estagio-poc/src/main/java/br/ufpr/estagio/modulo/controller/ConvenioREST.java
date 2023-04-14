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
import br.ufpr.estagio.modulo.dto.ConvenioDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.service.AgenteIntegradorService;
import br.ufpr.estagio.modulo.service.ConvenioService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

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
	public ResponseEntity<ConvenioDTO> buscarAgenteIntegradorPorId(@PathVariable Integer id) {
	    Optional<Convenio> convenio = convenioService.buscarPorId(id);
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
}
