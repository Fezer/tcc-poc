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

import br.ufpr.estagio.modulo.dto.SupervisorDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Supervisor;
import br.ufpr.estagio.modulo.service.SupervisorService;

@CrossOrigin
@RestController
@RequestMapping("/supervisor")
public class SupervisorREST {
	
	@Autowired
    private SupervisorService supervisorService;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/")
	public ResponseEntity<SupervisorDTO> inserir(@RequestBody SupervisorDTO supervisor){
		try {
			Supervisor newSupervisor = supervisorService.salvarSupervisor(mapper.map(supervisor, Supervisor.class));
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newSupervisor, SupervisorDTO.class));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
	        throw new PocException(HttpStatus.BAD_REQUEST, "Dados inválidos: " + e.getMessage());
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<SupervisorDTO>> listarTodos(){
		try {
			List<Supervisor> lista = supervisorService.listarTodosSupervisors();
			if(lista.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Nenhum supervisor encontrado!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, SupervisorDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SupervisorDTO> listarSupervisor(@PathVariable Long id){
		try {
			Optional<Supervisor> supervisorOptional = supervisorService.buscarSupervisorPorId(id);
		if(supervisorOptional.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Supervisor não encontrado!");
		} else {
			Supervisor supervisor = supervisorOptional.get();
			SupervisorDTO supervisorDTO = mapper.map(supervisor, SupervisorDTO.class);
			return new ResponseEntity<>(supervisorDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SupervisorDTO> atualizar(@PathVariable Long id, @RequestBody SupervisorDTO supervisor){
		try {
			Optional<Supervisor> supervisorFind = supervisorService.buscarSupervisorPorId(id);
		if(supervisorFind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Supervisor não encontrado!");
		} else {
			Supervisor supervisorAtualizado = supervisorService.atualizarDados(supervisorFind.get(), supervisor);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(supervisorAtualizado, SupervisorDTO.class));
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
	public ResponseEntity<SupervisorDTO> delete(@PathVariable Long id){
		try {
			Optional<Supervisor> supervisorFind = supervisorService.buscarSupervisorPorId(id);
		if(supervisorFind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Supervisor não encontrado!");
		} else {
			supervisorService.deletarSupervisor(id);
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
