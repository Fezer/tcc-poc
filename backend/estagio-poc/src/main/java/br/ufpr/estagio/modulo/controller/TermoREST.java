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
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoPocDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoPoc;
import br.ufpr.estagio.modulo.repository.TermoPocRepository;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/termo")
public class TermoREST {
	
    private EstagioService estagioService;
    private TermoDeEstagioService termoDeEstagioService;
    private RelatorioDeEstagioService relatorioDeEstagioService;
    
    public TermoREST(EstagioService estagioService, 
    		TermoDeEstagioService termoDeEstagioService, 
    		RelatorioDeEstagioService relatorioDeEstagioService) {
        this.estagioService = estagioService;
        this.termoDeEstagioService = termoDeEstagioService;
        this.relatorioDeEstagioService = relatorioDeEstagioService;
    }
	
	@Autowired
	private TermoPocRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("")
	public ResponseEntity<TermoDeEstagioDTO> inserir(@RequestBody TermoDeEstagioDTO termo){
		try {
		TermoDeEstagio newTermo = termoDeEstagioService.salvar(mapper.map(termo, TermoDeEstagio.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newTermo, TermoDeEstagioDTO.class));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
	        throw new PocException(HttpStatus.BAD_REQUEST, "Dados inválidos: " + e.getMessage());
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTodos(){
		try {
			List<TermoDeEstagio> lista = termoDeEstagioService.listarTodos();
			if(lista.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Nenhum termo encontrado!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
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
	public ResponseEntity<TermoDeEstagioDTO> listarTermo(@PathVariable Long id){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(id));
		if(termoOptional.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
			termo.add(linkTo(methodOn(TermoREST.class).listarTermo(id)).withSelfRel());
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
	public ResponseEntity<TermoDeEstagioDTO> atualizar(@PathVariable Long id, @RequestBody TermoDeEstagioDTO termo){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(id));
		if(termofind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			termoDeEstagioService.salvar(mapper.map(termofind, TermoDeEstagio.class));
			termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(id));
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termofind, TermoDeEstagioDTO.class));
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
	public ResponseEntity<TermoDeEstagioDTO> delete(@PathVariable Long id){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(id));
		if(termofind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			termoDeEstagioService.deletar(id);
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
