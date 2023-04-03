package br.ufpr.estagio.modulo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
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

import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.TermoPocDTO;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoPoc;
import br.ufpr.estagio.modulo.repository.TermoPocRepository;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/estagio")
public class EstagioREST {
	
    private EstagioService estagioService;
    private TermoDeEstagioService termoDeEstagioService;
    private RelatorioDeEstagioService relatorioDeEstagioService;
    
    public EstagioREST(EstagioService estagioService, 
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
	
	@PostMapping("/novo")
	public ResponseEntity<EstagioDTO> novoEstagio(){
		try {
			Estagio estagio = new Estagio();
			estagio = estagioService.novoEstagio(estagio);
			
			TermoDeEstagio termoDeCompromisso = new TermoDeEstagio();
			termoDeCompromisso = termoDeEstagioService.novo(termoDeCompromisso);
			estagio.setTermoDeCompromisso(termoDeCompromisso);
			
			RelatorioDeEstagio relatorioDeEstagio = new RelatorioDeEstagio();
			relatorioDeEstagio = relatorioDeEstagioService.novo(relatorioDeEstagio);
			ArrayList<RelatorioDeEstagio> listRelatorio = new ArrayList<RelatorioDeEstagio>();
			listRelatorio.add(relatorioDeEstagio);
			estagio.setRelatorioDeEstagio(listRelatorio);
			
					
			
			EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
			
			estagioDTO.add(linkTo(methodOn(EstagioREST.class).listarEstagio(estagio.getId())).withSelfRel());
			return new ResponseEntity<>(estagioDTO, HttpStatus.CREATED);			
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<EstagioDTO>> listarTodos(){
		try {
			List<Estagio> lista = estagioService.listAllEstagios();
			if(lista.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Nenhum estágio encontrado!");
			} else {
				List<EstagioDTO> listaDTO = new ArrayList<EstagioDTO>();
				for(Estagio l : lista) {
					listaDTO.add(estagioService.toEstagioDTO(l).add(linkTo(methodOn(EstagioREST.class).listarEstagio(l.getId())).withSelfRel()));
				}
				return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<TermoPocDTO> listarEstagio(@PathVariable Long id){
		try {
			Optional<TermoPoc> termo = repo.findById(id);
		if(termo.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termo, TermoPocDTO.class));
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
}
