package br.ufpr.estagio.modulo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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


import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.TermoPocDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.TermoPoc;
import br.ufpr.estagio.modulo.repository.TermoPocRepository;

@CrossOrigin
@RestController
@RequestMapping("/termo")
public class TermoREST {
	
	@Autowired
	private TermoPocRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("")
	public ResponseEntity<TermoPocDTO> inserir(@RequestBody TermoPocDTO termo){
		try {
		TermoPoc newTermo = repo.save(mapper.map(termo, TermoPoc.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newTermo, TermoPocDTO.class));
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<TermoPocDTO>> listarTodos(){
		try {
			List<TermoPoc> lista = repo.findAll();
			if(lista.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Nenhum termo encontrado!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, TermoPocDTO.class)).collect(Collectors.toList()));
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
	public ResponseEntity<TermoPocDTO> listarTermo(@PathVariable Long id){
		try {
			Optional<TermoPoc> termoOptional = repo.findById(id);
		if(termoOptional.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			TermoPoc termo = termoOptional.get();
			TermoPocDTO termoDTO = mapper.map(termo, TermoPocDTO.class);
			termo.add(linkTo(methodOn(TermoREST.class).listarTermo(id)).withSelfRel());
			//return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termo, TermoPocDTO.class));
			return new ResponseEntity<>(termoDTO, HttpStatus.OK);
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
	public ResponseEntity<TermoPocDTO> atualizar(@PathVariable Long id, @RequestBody TermoPocDTO termo){
		try {
			Optional<TermoPoc> termofind = repo.findById(id);
		if(termofind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			repo.save(mapper.map(termofind, TermoPoc.class));
			termofind = repo.findById(id);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termofind, TermoPocDTO.class));
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
	public ResponseEntity<TermoPocDTO> delete(@PathVariable Long id){
		try {
			Optional<TermoPoc> termofind = repo.findById(id);
		if(termofind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			repo.deleteById(id);
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
	
	@PutMapping("/aprovar/coafe/{id}")
	public ResponseEntity<TermoPocDTO> aprovarCoafe(@PathVariable Long id){
		try {
			Optional<TermoPoc> termofind = repo.findById(id);
			if(termofind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
			} else {
				TermoPoc termo = new TermoPoc();
				termo = termofind.get();
				termo.setStatusTermo("Aprovado");
				termo.setStatusEstagio("Aprovado");
				termo.setParecerCOAFE("Aprovado");
				repo.save(mapper.map(termo, TermoPoc.class));
				termofind = repo.findById(id);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termofind, TermoPocDTO.class));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/reprovar/coafe/{id}")
	public ResponseEntity<TermoPocDTO> reprovarCoafe(@PathVariable Long id, @RequestBody JustificativaDTO requestBody){
		try {
			String justificativa = requestBody.getJustificativa();
			Optional<TermoPoc> termofind = repo.findById(id);
			if(termofind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
			} else {
				if (justificativa.isBlank() || justificativa.isEmpty()){
					throw new PocException(HttpStatus.BAD_REQUEST, "O motivo do indeferimento deve ser informado!");
				} else {
					TermoPoc termo = new TermoPoc();
					termo = termofind.get();
					termo.setStatusTermo("Reprovado");
					termo.setStatusEstagio("Reprovado");
					termo.setParecerCOAFE("Reprovado");
					termo.setMotivoIndeferimento(justificativa);
					repo.save(mapper.map(termo, TermoPoc.class));
					termofind = repo.findById(id);
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termofind, TermoPocDTO.class));
				}
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
