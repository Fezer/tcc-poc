package br.ufpr.estagio.poc.controller;

import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.poc.model.TermoPocDTO;
import br.ufpr.estagio.poc.exception.PocException;
import br.ufpr.estagio.poc.model.TermoPoc;
import br.ufpr.estagio.poc.repository.TermoPocRepository;

@CrossOrigin
@RestController
public class TermoPocREST {
	
	@Autowired
	private TermoPocRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/termo")
	public ResponseEntity<TermoPocDTO> inserir(@RequestBody TermoPocDTO termo){
		try {
		repo.save(mapper.map(termo, TermoPoc.class));
		Optional<TermoPoc> newTermo = repo.findByGrrAluno(termo.getGrrAluno());
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newTermo, TermoPocDTO.class));
		}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
}
