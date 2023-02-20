package br.ufpr.estagio.poc.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.poc.model.TermoPocDTO;
import br.ufpr.estagio.poc.exception.PocException;
import br.ufpr.estagio.poc.model.JustificativaDTO;
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
	
	@GetMapping("/termo")
	public ResponseEntity<List<TermoPocDTO>> listarTodos(){
		try {
			List<TermoPoc> lista = repo.findAll();
			if(lista.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Nenhum termo encontrado!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, TermoPocDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			throw e;
		}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termo/{id}")
	public ResponseEntity<TermoPocDTO> listarTermo(@PathVariable Long id){
		try {
			Optional<TermoPoc> termo = repo.findById(id);
		if(termo.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termo, TermoPocDTO.class));
		}
		}catch(PocException e) {
			throw e;
		}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/termo/{id}")
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
			throw e;
		}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@DeleteMapping("/termo/{id}")
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
			throw e;
		}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/termo/aprovar/coafe/{id}")
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
			throw e;
		}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/termo/reprovar/coafe/{id}")
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
			throw e;
		}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
}
