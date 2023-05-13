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

import br.ufpr.estagio.modulo.dto.FichaDeAvaliacaoDTO;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.service.FichaDeAvaliacaoService;

@CrossOrigin
@RestController
@RequestMapping("/fichaDeAvaliacao")
public class FichaDeAvaliacaoREST {

	@Autowired
    private FichaDeAvaliacaoService fichaDeAvaliacaoService;
        
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/")
	public ResponseEntity<FichaDeAvaliacaoDTO> criarFichaDeAvaliacao() {
	    
    	FichaDeAvaliacao fichaDeAvaliacao = new FichaDeAvaliacao();
    	fichaDeAvaliacao = fichaDeAvaliacaoService.novoFichaDeAvaliacao(fichaDeAvaliacao);
    	
	    FichaDeAvaliacaoDTO fichaDeAvaliacaoDTO = mapper.map(fichaDeAvaliacao, FichaDeAvaliacaoDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(fichaDeAvaliacaoDTO);
	}
        
    @GetMapping("/")
	public ResponseEntity<List<FichaDeAvaliacaoDTO>> listarFichasDeAvaliacao() {
	    List<FichaDeAvaliacao> listaFichasDeAvaliacao = fichaDeAvaliacaoService.listarTodosFichasDeAvaliacao();
	    List<FichaDeAvaliacaoDTO> listaFichasDeAvaliacaoDTO = listaFichasDeAvaliacao.stream()
	            .map(ap -> mapper.map(ap, FichaDeAvaliacaoDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(listaFichasDeAvaliacaoDTO);
	}
    
    @GetMapping("/{idFichaDeAvaliacao}")
	public ResponseEntity<FichaDeAvaliacaoDTO> buscarFichaDeAvaliacaoPorId(@PathVariable long idFichaDeAvaliacao) {
	    Optional<FichaDeAvaliacao> fichaDeAvaliacao = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idFichaDeAvaliacao);
	    
	    if (fichaDeAvaliacao.isEmpty()) {
			throw new NotFoundException("Ficha de avaliação não encontrada!");
		}
	    
	    FichaDeAvaliacaoDTO fichaDeAvaliacaoDTO = mapper.map(fichaDeAvaliacao.get(), FichaDeAvaliacaoDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(fichaDeAvaliacaoDTO);
	}

    @PutMapping("/{idFichaDeAvaliacao}")
    public ResponseEntity<FichaDeAvaliacaoDTO> atualizarFichaDeAvaliacao(@PathVariable long idFichaDeAvaliacao, @RequestBody FichaDeAvaliacaoDTO fichaDeAvaliacaoDTO){
    	
	    Optional<FichaDeAvaliacao> fichaDeAvaliacao = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idFichaDeAvaliacao);
	    
	    if (fichaDeAvaliacao.isEmpty()) {
			throw new NotFoundException("Ficha de avaliação não encontrada!");
		}
        
	    FichaDeAvaliacao fichaDeAvaliacaoAtualizada = fichaDeAvaliacaoService.atualizarFichaAvaliacao(fichaDeAvaliacao.get(), fichaDeAvaliacaoDTO);
	    fichaDeAvaliacaoDTO = mapper.map(fichaDeAvaliacaoAtualizada, FichaDeAvaliacaoDTO.class);
        return ResponseEntity.ok().body(fichaDeAvaliacaoDTO);
    }

    @DeleteMapping("/{idFichaDeAvaliacao}")
    public ResponseEntity<Void> excluirFichaDeAvaliacao(@PathVariable long idFichaDeAvaliacao){
    	
	    Optional<FichaDeAvaliacao> fichaDeAvaliacao = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idFichaDeAvaliacao);
	    
	    if (fichaDeAvaliacao.isEmpty()) {
			throw new NotFoundException("Ficha de avaliação não encontrada!");
		}
	    
	    fichaDeAvaliacaoService.deletarFichaDeAvaliacao(fichaDeAvaliacao.get());
	    
	    return ResponseEntity.noContent().build();
    }
}
