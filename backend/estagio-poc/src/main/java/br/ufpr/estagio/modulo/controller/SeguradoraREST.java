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
import br.ufpr.estagio.modulo.dto.SeguradoraDTO;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.service.ApoliceService;
import br.ufpr.estagio.modulo.service.SeguradoraService;

@CrossOrigin
@RestController
@RequestMapping("/seguradora")
public class SeguradoraREST {

	@Autowired
    private ApoliceService apoliceService;
    
    @Autowired
    private SeguradoraService seguradoraService;
    
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/novo")
	public ResponseEntity<SeguradoraDTO> novoSeguradora(@RequestBody SeguradoraDTO seguradoraDTO){
		try {
			Seguradora seguradora = mapper.map(seguradoraDTO, Seguradora.class);
			
			if (seguradoraDTO.getNome().isBlank() || seguradoraDTO.getNome().isEmpty())
	    		throw new InvalidFieldException("Nome inválido.");
	    	
	    	if (seguradoraDTO.isSeguradoraUfpr() != true && seguradoraDTO.isSeguradoraUfpr() != false)
	    		throw new InvalidFieldException("Selecione se a seguradora é uma seguradora UFPR.");
			
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
    
    @PostMapping("/")
	public ResponseEntity<SeguradoraDTO> criarSeguradora(@RequestBody SeguradoraDTO seguradoraDTO){
		try {
			Seguradora seguradora = mapper.map(seguradoraDTO, Seguradora.class);
			
			if (seguradoraDTO.getNome().isBlank() || seguradoraDTO.getNome().isEmpty())
	    		throw new InvalidFieldException("Nome inválido.");
	    	
	    	if (seguradoraDTO.isSeguradoraUfpr() != true && seguradoraDTO.isSeguradoraUfpr() != false)
	    		throw new InvalidFieldException("Selecione se a seguradora é uma seguradora UFPR.");
			
	    	if (seguradoraDTO.isAtiva() != true && seguradoraDTO.isAtiva() != false)
	    		seguradoraDTO.setAtiva(true);
	    	
			seguradora = seguradoraService.criarSeguradora(seguradora);
			seguradoraDTO = mapper.map(seguradora, SeguradoraDTO.class);
			return new ResponseEntity<>(seguradoraDTO, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @PostMapping("/{idSeguradora}/apolice")
	public ResponseEntity<ApoliceDTO> criarApolice(@PathVariable Integer idSeguradora, @RequestBody ApoliceDTO apolice){
		try {
			Optional<Seguradora> seguradoraFind = seguradoraService.buscarSeguradoraPorId(idSeguradora);
			
			// copiar de apólice
			
			Seguradora seguradora = seguradoraFind.get();
			Apolice apoliceNovo = mapper.map(apolice, Apolice.class);
			apoliceNovo = apoliceService.criarApolice(apoliceNovo);
			apoliceNovo = apoliceService.associarSeguradoraApolice(apoliceNovo, seguradora);
			apolice = mapper.map(apoliceNovo, ApoliceDTO.class);
			return new ResponseEntity<>(apolice, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<SeguradoraDTO> buscarPorId(@PathVariable Integer id) {
	    Optional<Seguradora> seguradora = seguradoraService.buscarSeguradoraPorId(id);
	    
	    if (seguradora.isEmpty()) {
			throw new NotFoundException("Seguradora não encontrada!");
		}
	    
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
    
    @PutMapping("/{id}")
    public ResponseEntity<SeguradoraDTO> atualizarSeguradora(@PathVariable Integer id, @RequestBody SeguradoraDTO seguradoraDTO){
        Optional<Seguradora> seguradora = seguradoraService.buscarSeguradoraPorId(id);
        
        if(seguradora.isPresent()) {

        	/*if (seguradoraDTO.getNome() == null && (seguradoraDTO.isSeguradoraUfpr() != false && seguradoraDTO.isSeguradoraUfpr() != true)) {
        		Seguradora seguradoraAtualizada = mapper.map(seguradoraDTO, Seguradora.class);
                seguradoraAtualizada.setId(id);
                System.out.println(seguradoraAtualizada.isAtiva());
                seguradoraAtualizada = seguradoraService.ativarDesativarSeguradora(seguradoraAtualizada);
                SeguradoraDTO seguradoraDTOAtualizada = mapper.map(seguradoraAtualizada, SeguradoraDTO.class);
                return ResponseEntity.ok().body(seguradoraDTOAtualizada);
        	}*/
        	
        	Seguradora seguradoraAtualizada = mapper.map(seguradoraDTO, Seguradora.class);
            seguradoraAtualizada.setId(id);
            seguradoraAtualizada = seguradoraService.atualizarSeguradora(seguradoraAtualizada);
            SeguradoraDTO seguradoraDTOAtualizada = mapper.map(seguradoraAtualizada, SeguradoraDTO.class);
            return ResponseEntity.ok().body(seguradoraDTOAtualizada);

        } else {
			throw new NotFoundException("Seguradora não encontrada!");
        }
    }
    
    @PutMapping("/ativar-desativar/{id}")
    public ResponseEntity<SeguradoraDTO> ativarDesativarSeguradora(@PathVariable Integer id, @RequestBody SeguradoraDTO seguradoraDTO){
        Optional<Seguradora> seguradora = seguradoraService.buscarSeguradoraPorId(id);
        
        if(seguradora.isPresent()) {
            Seguradora seguradoraAtualizada = mapper.map(seguradoraDTO, Seguradora.class);
                        
            seguradoraAtualizada.setId(id);
            seguradoraAtualizada = seguradoraService.ativarDesativarSeguradora(seguradoraAtualizada);

            if (seguradoraAtualizada.getError() != null)
            	throw new InvalidFieldException(seguradoraAtualizada.getError());
            
            SeguradoraDTO seguradoraDTOAtualizada = mapper.map(seguradoraAtualizada, SeguradoraDTO.class);
            return ResponseEntity.ok().body(seguradoraDTOAtualizada);
        } else {
			throw new NotFoundException("Seguradora não encontrada!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirSeguradora(@PathVariable Integer id){
        Optional<Seguradora> seguradora = seguradoraService.buscarSeguradoraPorId(id);
        if(seguradora.isPresent()) {
            seguradoraService.excluirSeguradora(seguradora.get());
            return ResponseEntity.noContent().build();
        } else {
        	throw new NotFoundException("Seguradora não encontrada!");
        }
    }
}
