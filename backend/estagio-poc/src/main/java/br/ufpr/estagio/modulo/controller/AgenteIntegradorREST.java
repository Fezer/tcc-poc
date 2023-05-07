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

import br.ufpr.estagio.modulo.dto.AgenteIntegradorDTO;
import br.ufpr.estagio.modulo.dto.AgenteIntegradorDTOv2;
import br.ufpr.estagio.modulo.dto.ApoliceDTO;
import br.ufpr.estagio.modulo.dto.ConvenioDTO;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.service.AgenteIntegradorService;
import br.ufpr.estagio.modulo.service.ConvenioService;

@CrossOrigin
@RestController
@RequestMapping("/agente-integrador")
public class AgenteIntegradorREST {
	
	@Autowired
    private AgenteIntegradorService agenteIntegradorService;
    
    @Autowired
    private ConvenioService convenioService;
        
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/novo")
	public ResponseEntity<Object> novoAgenteIntegrador(@RequestBody AgenteIntegradorDTO agenteIntegradorDTO){
    	
    	if (agenteIntegradorDTO.getCnpj().isBlank() || agenteIntegradorDTO.getCnpj().isEmpty())
    		throw new InvalidFieldException("CNPJ inválido.");
    	
    	try {
			
			AgenteIntegrador agenteIntegrador = mapper.map(agenteIntegradorDTO, AgenteIntegrador.class);
			
			agenteIntegrador.setConvenio(agenteIntegradorDTO.getConvenio());
			agenteIntegrador.setTermoDeEstagio(agenteIntegradorDTO.getTermoDeEstagio());
			agenteIntegrador.setEstagio(agenteIntegradorDTO.getEstagio());
		    
			agenteIntegrador = agenteIntegradorService.criarAgenteIntegrador(agenteIntegrador);
			
			agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTO.class);
			
			return new ResponseEntity<>(agenteIntegradorDTO, HttpStatus.CREATED);	
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar cliente!");
		}
    	
	}
    
    @PostMapping("/")
	public ResponseEntity<AgenteIntegradorDTOv2> criarAgenteIntegrador(@RequestBody AgenteIntegradorDTOv2 agenteIntegradorDTO){
		
    	if (agenteIntegradorDTO.getNome().isBlank() || agenteIntegradorDTO.getNome().isEmpty())
    		throw new InvalidFieldException("Nome inválido.");
    	
    	if (agenteIntegradorDTO.getTelefone().isBlank() || agenteIntegradorDTO.getTelefone().isEmpty())
    		throw new InvalidFieldException("Telefone inválido.");
    	
    	if (agenteIntegradorDTO.getCnpj().isBlank() || agenteIntegradorDTO.getCnpj().isEmpty())
    		throw new InvalidFieldException("CNPJ inválido.");
    	
    	try {
			AgenteIntegrador agenteIntegrador = mapper.map(agenteIntegradorDTO, AgenteIntegrador.class);
			agenteIntegrador = agenteIntegradorService.criarAgenteIntegrador(agenteIntegrador);
			agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTOv2.class);
			return new ResponseEntity<>(agenteIntegradorDTO, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar agente integrador!");
		}
	}
    
    @PostMapping("/{idAgente}/convenio")
	public ResponseEntity<ConvenioDTO> criarConvenio(@PathVariable Integer idAgente, @RequestBody ConvenioDTO convenio){
		
    	/*if (convenio.getNumero() < 1)
    		throw new InvalidFieldException("Número inválido.");
    	
    	if (convenio.getDescricao().isBlank() || convenio.getDescricao().isEmpty())
    		throw new InvalidFieldException("Preencha a descrição.");
    	
    	if (convenio.getDataInicio() == null)
    		throw new InvalidFieldException("Insira uma data de início.");
    	
    	if (convenio.getDataFim() == null)
    		throw new InvalidFieldException("Insira uma data de fim.");
    	
    	if (convenio.getDataFim().before(convenio.getDataInicio()))
    		throw new InvalidFieldException("A data de fim deve ser posterior à data de início");*/
    	
    	try {
			Optional<AgenteIntegrador> agenteFind = agenteIntegradorService.buscarPorId(idAgente);
			if(agenteFind.isEmpty()) {
				throw new NotFoundException("Agente integrador não encontrado!");
			}
			AgenteIntegrador agente = agenteFind.get();
			Convenio convenioNovo = mapper.map(convenio, Convenio.class);
			convenioNovo = convenioService.criarConvenio(convenioNovo);
			convenioNovo = convenioService.associarAgenteAoConvenio(convenioNovo, agente);
			convenio = mapper.map(convenioNovo, ConvenioDTO.class);
			return new ResponseEntity<>(convenio, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar agente integrador!");
		}
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<AgenteIntegradorDTOv2> buscarAgenteIntegradorPorId(@PathVariable Integer id) {
    	try {
    		Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(id);
    		if(agenteIntegrador.isEmpty()) {
                throw new NotFoundException("Agente integrador não encontrado!");
            }
    		AgenteIntegradorDTOv2 agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTOv2.class);
    		return ResponseEntity.status(HttpStatus.OK).body(agenteIntegradorDTO);
    	} catch (Exception e) {
            e.printStackTrace();
            throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar agente integrador!");
        }
	}

	@GetMapping("/")
	public ResponseEntity<List<ApoliceDTO>> listarApolices() {
		try {
			List<AgenteIntegrador> agentesIntegradores = agenteIntegradorService.listarAgentesIntegradores();
			
			List<ApoliceDTO> agentesIntegradoresDTO = agentesIntegradores.stream()
					.map(ap -> mapper.map(ap, ApoliceDTO.class))
					.collect(Collectors.toList());
			return ResponseEntity.ok().body(agentesIntegradoresDTO);
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar apólices!");
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AgenteIntegradorDTO> atualizarAgenteIntegrador(@PathVariable Integer id, @RequestBody AgenteIntegradorDTO agenteIntegradorDTO){
	    try {
			Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(id);
		    
		    if(agenteIntegrador.isPresent()) {
		        AgenteIntegrador agenteIntegradorAtualizado = mapper.map(agenteIntegradorDTO, AgenteIntegrador.class);
		        agenteIntegradorAtualizado.setId(id);
		        agenteIntegradorAtualizado = agenteIntegradorService.atualizarAgenteIntegrador(agenteIntegradorAtualizado);
		        AgenteIntegradorDTO agenteIntegradorDTOAtualizado = mapper.map(agenteIntegradorAtualizado, AgenteIntegradorDTO.class);
		        return ResponseEntity.ok().body(agenteIntegradorDTOAtualizado);
		    } else {
		    	throw new NotFoundException("Agente integrador não encontrado!");
		    }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar agente integrador!");
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirAgenteIntegrador(@PathVariable Integer id){
		try {
		    Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(id);
		    if(agenteIntegrador.isPresent()) {
		        agenteIntegradorService.excluirAgenteIntegrador(agenteIntegrador.get());
		        return ResponseEntity.noContent().build();
		    } else {
		        //return ResponseEntity.notFound().build();
		    	throw new NotFoundException("Agente integrador não encontrado!");
		    }
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao excluir agente integrador!");
		}
	}

}
