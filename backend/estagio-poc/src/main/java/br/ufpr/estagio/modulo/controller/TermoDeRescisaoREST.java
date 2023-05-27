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

import br.ufpr.estagio.modulo.dto.TermoDeRescisaoDTO;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.service.TermoDeRescisaoService;

@CrossOrigin
@RestController
@RequestMapping("/termoDeRescisao")
public class TermoDeRescisaoREST {
	
	@Autowired
    private TermoDeRescisaoService termoDeRescisaoService;
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("")
	public ResponseEntity<TermoDeRescisaoDTO> inserir(@RequestBody TermoDeRescisaoDTO termo){
		try {
		TermoDeRescisao newTermo = termoDeRescisaoService.novo(mapper.map(termo, TermoDeRescisao.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newTermo, TermoDeRescisaoDTO.class));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    } catch(Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Não foi possível criar um novo termo de rescisão.");
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<TermoDeRescisaoDTO>> listarTodos(){
		List<TermoDeRescisao> lista = termoDeRescisaoService.listarTodos();
		if(lista.isEmpty()) {
			throw new NotFoundException("Nenhum termo encontrado!");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, TermoDeRescisaoDTO.class)).collect(Collectors.toList()));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TermoDeRescisaoDTO> listarTermo(@PathVariable Long id){
		try {
			Optional<TermoDeRescisao> termoOptional = termoDeRescisaoService.buscarPorId(id);
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeRescisao termo = termoOptional.get();
			TermoDeRescisaoDTO termoDTO = mapper.map(termo, TermoDeRescisaoDTO.class);
			return new ResponseEntity<>(termoDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TermoDeRescisaoDTO> atualizar(@PathVariable Long id, @RequestBody TermoDeRescisaoDTO termo){
		try {
			Optional<TermoDeRescisao> termofind = termoDeRescisaoService.buscarPorId(id);
		if(termofind.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeRescisao termoAtualizado = termoDeRescisaoService.atualizarDados(termofind.get(), termo);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeRescisaoDTO.class));
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<TermoDeRescisaoDTO> delete(@PathVariable Long id){
		try {
			Optional<TermoDeRescisao> termofind = termoDeRescisaoService.buscarPorId(id);
		if(termofind.isEmpty()) {
			throw new NotFoundException("Termo de rescisão não encontrado!");
		} else {
			termoDeRescisaoService.deletar(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
}
