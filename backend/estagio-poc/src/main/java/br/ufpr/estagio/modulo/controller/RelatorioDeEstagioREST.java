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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.RelatorioDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoRelatorio;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/relatorioDeEstagio")
public class RelatorioDeEstagioREST {

	@Autowired
    private RelatorioDeEstagioService relatorioDeEstagioService;
        
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/")
	public ResponseEntity<RelatorioDeEstagioDTO> criarRelatorioDeEstagio() {
	    
    	RelatorioDeEstagio relatorioDeEstagio = new RelatorioDeEstagio();
    	relatorioDeEstagio = relatorioDeEstagioService.novoRelatorio(relatorioDeEstagio);
    	
	    RelatorioDeEstagioDTO relatorioDeEstagioDTO = mapper.map(relatorioDeEstagio, RelatorioDeEstagioDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(relatorioDeEstagioDTO);
	}
        
    @GetMapping("/")
	public ResponseEntity<List<RelatorioDeEstagioDTO>> listarRelatoriosDeEstagio() {
	    List<RelatorioDeEstagio> listaRelatorios = relatorioDeEstagioService.listarTodosRelatorios();
	    List<RelatorioDeEstagioDTO> listaRelatoriosDTO = listaRelatorios.stream()
	            .map(ap -> mapper.map(ap, RelatorioDeEstagioDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(listaRelatoriosDTO);
	}
    
    @GetMapping("/{idRelatorio}")
	public ResponseEntity<RelatorioDeEstagioDTO> buscarRelatorioDeEstagioPorId(@PathVariable long idRelatorio) {
	    Optional<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorio);
	    
	    if (relatorioDeEstagio.isEmpty()) {
			throw new NotFoundException("Relatório de estágio não encontrado!");
		}
	    
	    RelatorioDeEstagioDTO relatorioDeEstagioDTO = mapper.map(relatorioDeEstagio.get(), RelatorioDeEstagioDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(relatorioDeEstagioDTO);
	}

    @PutMapping("/{idRelatorio}")
    public ResponseEntity<RelatorioDeEstagioDTO> atualizarRelatorioDeEstagioAvaliacao(@PathVariable long idRelatorio, @RequestBody RelatorioDeEstagioDTO relatorioDeEstagioDTO){
    	
	    Optional<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorio);
	    	    
	    if (relatorioDeEstagio.isEmpty()) {
			throw new NotFoundException("Relatório de estágio não encontrado!");
		}
        
	    RelatorioDeEstagio relatorioDeEstagioAtualizado = relatorioDeEstagioService.atualizarRelatorioAvaliacao(relatorioDeEstagio.get(), relatorioDeEstagioDTO);
	    relatorioDeEstagioDTO = mapper.map(relatorioDeEstagioAtualizado, RelatorioDeEstagioDTO.class);
        return ResponseEntity.ok().body(relatorioDeEstagioDTO);
    }
    
    @PutMapping("/{idRelatorio}/definirTipo")
    public ResponseEntity<RelatorioDeEstagioDTO> definirTipoDeRelatorioDeEstagio(@PathVariable long idRelatorio, @RequestParam EnumTipoRelatorio tipoRelatorio){
    	
	    Optional<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorio);
	    	    
	    if (relatorioDeEstagio.isEmpty()) {
			throw new NotFoundException("Relatório de estágio não encontrado!");
		}
        
	    RelatorioDeEstagio relatorioDeEstagioAtualizado = relatorioDeEstagioService.definirTipoDeRelatorioDeEstagio(relatorioDeEstagio.get(), tipoRelatorio);
	    RelatorioDeEstagioDTO relatorioDeEstagioDTO = mapper.map(relatorioDeEstagioAtualizado, RelatorioDeEstagioDTO.class);
        return ResponseEntity.ok().body(relatorioDeEstagioDTO);
    }

    @DeleteMapping("/{idRelatorio}")
    public ResponseEntity<Void> excluirRelatorioDeEstagio(@PathVariable long idRelatorio){
    	Optional<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorio);
	    
	    if (relatorioDeEstagio.isEmpty()) {
			throw new NotFoundException("Relatório de estágio não encontrado!");
		}
	    
	    relatorioDeEstagioService.deletarRelatorioDoEstagio(relatorioDeEstagio.get());
	    
	    return ResponseEntity.noContent().build();
    }
}
