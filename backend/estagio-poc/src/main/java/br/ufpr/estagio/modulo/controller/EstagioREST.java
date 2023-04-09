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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.TermoPocDTO;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
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
			TermoDeEstagio termoDeCompromisso = new TermoDeEstagio();
			estagio.setTermoDeCompromisso(termoDeCompromisso);
			termoDeCompromisso.setEstagio(estagio);
			
			estagio = estagioService.novoEstagio(estagio);
			termoDeCompromisso = termoDeEstagioService.novo(termoDeCompromisso);

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
	public ResponseEntity<EstagioDTO> listarEstagio(@PathVariable Long id){
		try {
			Estagio estagio = estagioService.getEstagio(id);
		if(estagio == null) {
			throw new PocException(HttpStatus.NOT_FOUND, "Estágio não encontrado!");
		} else {
			EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
			return new ResponseEntity<>(estagioDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/tipo/{id}")
	public ResponseEntity<EstagioDTO> definirTipoEstagio(@PathVariable Long id, @RequestParam EnumTipoEstagio tipoEstagio){
		if(tipoEstagio == null) {
			throw new PocException(HttpStatus.BAD_REQUEST, "Tipo do estágio não informado");
		}
		try {
			Estagio estagio = estagioService.getEstagio(id);
		if(estagio == null) {
			throw new PocException(HttpStatus.NOT_FOUND, "Estágio não encontrado!");
		} else {
			estagio = estagioService.definirTipoEstagio(estagio, tipoEstagio);
			EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
			return new ResponseEntity<>(estagioDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/ufpr/{id}")
	public ResponseEntity<EstagioDTO> isUfpr(@PathVariable Long id, @RequestParam Boolean estagioUfpr){
		if(estagioUfpr == null) {
			throw new PocException(HttpStatus.BAD_REQUEST, "isUfpr não informado");
		}
		try {
			Estagio estagio = estagioService.getEstagio(id);
		if(estagio == null) {
			throw new PocException(HttpStatus.NOT_FOUND, "Estágio não encontrado!");
		} else {
			estagio = estagioService.definirEstagioUfpr(estagio, estagioUfpr);
			EstagioDTO estagioDTO = estagioService.toEstagioDTO(estagio);
			return new ResponseEntity<>(estagioDTO, HttpStatus.OK);
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
