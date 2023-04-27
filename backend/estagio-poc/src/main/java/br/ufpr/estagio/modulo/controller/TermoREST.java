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
import br.ufpr.estagio.modulo.dto.PlanoDeAtividadesDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.AgenteIntegradorService;
import br.ufpr.estagio.modulo.service.ApoliceService;
import br.ufpr.estagio.modulo.service.ContratanteService;
import br.ufpr.estagio.modulo.service.OrientadorService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/termo")
public class TermoREST {
	
	@Autowired
    private TermoDeEstagioService termoDeEstagioService;
	@Autowired
    private OrientadorService orientadorService;
	@Autowired
    private AgenteIntegradorService agenteIntegradorService;
	@Autowired
    private ApoliceService apoliceService;
	@Autowired
    private ContratanteService contratanteService;

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
			TermoDeEstagio termoAtualizado = termoDeEstagioService.atualizarDados(termofind, termo);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{id}/planoAtividades")
	public ResponseEntity<TermoDeEstagioDTO> atualizarPlanoAtividades(@PathVariable Long id, @RequestBody PlanoDeAtividadesDTO planoAtividades){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(id));
		if(termofind.isEmpty()) {
			throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
		} else {
			TermoDeEstagio termoAtualizado = termoDeEstagioService.atualizarPlanoAtividades(termofind, planoAtividades);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{termoId}/associarOrientador/{orientadorId}")
	public ResponseEntity<TermoDeEstagioDTO> associarOrientador(@PathVariable Long termoId, @PathVariable Long orientadorId){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(termoId));
			if(termofind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
			}
			Optional<Orientador> orientadorFind = Optional.ofNullable(orientadorService.buscarOrientadorPorId(orientadorId));
			if(orientadorFind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Orientador não encontrado!");			
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService.associarOrientadorAoTermo(termofind.get(), orientadorFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{termoId}/associarAgenteIntegrador/{agenteId}")
	public ResponseEntity<TermoDeEstagioDTO> associarAgenteIntegrador(@PathVariable Long termoId, @PathVariable Long agenteId){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(termoId));
			if(termofind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
			}
			Optional<AgenteIntegrador> agenteFind = agenteIntegradorService.buscarPorId(agenteId);
			if(agenteFind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Agente integrador não encontrado!");			
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService.associarAgenteIntegradorAoTermo(termofind.get(), agenteFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	/*@PutMapping("/{termoId}/associarSupervisor/{supervisorId}")
	public ResponseEntity<TermoDeEstagioDTO> associarSupervisor(@PathVariable Long termoId, @PathVariable Long supervisorId){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(termoId));
			if(termofind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
			}
			Optional<Supervisor> supervisorFind = supervisorService.buscarSupervisorPorId(supervisorId);
			if(supervisorFind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Supervisor não encontrado!");			
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService.associarSupervisorAoTermo(termofind.get(), supervisorFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}*/
	
	@PutMapping("/{termoId}/associarApolice/{apoliceId}")
	public ResponseEntity<TermoDeEstagioDTO> associarApolice(@PathVariable Long termoId, @PathVariable Long apoliceId){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(termoId));
			if(termofind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
			}
			Optional<Apolice> apoliceFind = apoliceService.buscarPorId(apoliceId);
			if(apoliceFind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Apolice não encontrada!");			
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService.associarApoliceAoTermo(termofind.get(), apoliceFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{termoId}/associarContratante/{contratanteId}")
	public ResponseEntity<TermoDeEstagioDTO> associarContratante(@PathVariable Long termoId, @PathVariable Long contratanteId){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(termoId));
			if(termofind.isEmpty()) {
				throw new NotFoundException("Termo não encontrado!");
			}
			Optional<Contratante> contratanteFind = contratanteService.buscarPorId(contratanteId);
			if(contratanteFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");			
			} else {
				TermoDeEstagio termoAtualizado = termoDeEstagioService.associarContratanteAoTermo(termofind.get(), contratanteFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termoAtualizado, TermoDeEstagioDTO.class));
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
	public ResponseEntity<TermoDeEstagioDTO> aprovarCoafe(@PathVariable Long id){
		try {
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(id));
			if(termofind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
			} else {
				TermoDeEstagio termo = new TermoDeEstagio();
				termo = termofind.get();
				termo.setStatusTermo(EnumStatusTermo.Aprovado);
//				termo.setStatusEstagio(EnumStatusEstagio.Aprovado);
//				termo.setParecerCOAFE("Aprovado");
				termoDeEstagioService.salvar(mapper.map(termo, TermoDeEstagio.class));
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
	
	@PutMapping("/reprovar/coafe/{id}")
	public ResponseEntity<TermoDeEstagioDTO> reprovarCoafe(@PathVariable Long id, @RequestBody JustificativaDTO requestBody){
		try {
			String justificativa = requestBody.getJustificativa();
			Optional<TermoDeEstagio> termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(id));
			if(termofind.isEmpty()) {
				throw new PocException(HttpStatus.NOT_FOUND, "Termo não encontrado!");
			} else {
				if (justificativa.isBlank() || justificativa.isEmpty()){
					throw new PocException(HttpStatus.BAD_REQUEST, "O motivo do indeferimento deve ser informado!");
				} else {
					TermoDeEstagio termo = new TermoDeEstagio();
					termo = termofind.get();
					termo.setStatusTermo(EnumStatusTermo.Reprovado);
//					termo.setStatusEstagio(EnumStatusEstagio.Reprovado);
//					termo.setParecerCOAFE("Reprovado");
					termo.setMotivoIndeferimento(justificativa);
					termoDeEstagioService.salvar(mapper.map(termo, TermoDeEstagio.class));
					termofind = Optional.ofNullable(termoDeEstagioService.buscarPorId(id));
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(termofind, TermoDeEstagioDTO.class));
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
