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

import br.ufpr.estagio.modulo.dto.PeriodoRecessoDTO;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.PeriodoRecesso;
import br.ufpr.estagio.modulo.service.PeriodoRecessoService;

@CrossOrigin
@RestController
@RequestMapping("/periodoRecesso")
public class PeriodoRecessoREST {
	
	@Autowired
    private PeriodoRecessoService periodoRecessoService;
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("")
	public ResponseEntity<PeriodoRecessoDTO> inserir(@RequestBody PeriodoRecessoDTO periodoRecesso){
		try {
		PeriodoRecesso newPeriodoRecesso = periodoRecessoService.novo(mapper.map(periodoRecesso, PeriodoRecesso.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newPeriodoRecesso, PeriodoRecessoDTO.class));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
	    } catch(Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Não foi possível criar um novo periodo de recesso!.");
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<PeriodoRecessoDTO>> listarTodos(){
		List<PeriodoRecesso> lista = periodoRecessoService.listarTodos();
		if(lista.isEmpty()) {
			throw new NotFoundException("Nenhum período de recesso encontrado!");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, PeriodoRecessoDTO.class)).collect(Collectors.toList()));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PeriodoRecessoDTO> listarPeriodoRecesso(@PathVariable Long id){
		try {
			Optional<PeriodoRecesso> periodoRecessoOptional = periodoRecessoService.buscarPorId(id);
		if(periodoRecessoOptional.isEmpty()) {
			throw new NotFoundException("Período de recesso não encontrado!");
		} else {
			PeriodoRecesso periodoRecesso = periodoRecessoOptional.get();
			PeriodoRecessoDTO periodoRecessoDTO = mapper.map(periodoRecesso, PeriodoRecessoDTO.class);
			return new ResponseEntity<>(periodoRecessoDTO, HttpStatus.OK);
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
	public ResponseEntity<PeriodoRecessoDTO> atualizar(@PathVariable Long id, @RequestBody PeriodoRecessoDTO periodoRecesso){
		try {
			Optional<PeriodoRecesso> periodoRecessofind = periodoRecessoService.buscarPorId(id);
		if(periodoRecessofind.isEmpty()) {
			throw new NotFoundException("Periodo de recesso não encontrado!");
		} else {
			PeriodoRecesso periodoRecessoAtualizado = periodoRecessoService.atualizarDados(periodoRecessofind.get(), periodoRecesso);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(periodoRecessoAtualizado, PeriodoRecessoDTO.class));
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
	public ResponseEntity<PeriodoRecessoDTO> delete(@PathVariable Long id){
		try {
			Optional<PeriodoRecesso> periodoRecessofind = periodoRecessoService.buscarPorId(id);
		if(periodoRecessofind.isEmpty()) {
			throw new NotFoundException("Período de recesso não encontrado!");
		} else {
			periodoRecessoService.deletar(id);
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
