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
import br.ufpr.estagio.modulo.dto.ErrorResponse;
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
    
    /*@PostMapping("/novo")
	public ResponseEntity<Object> novoAgenteIntegrador(@RequestBody AgenteIntegradorDTO agenteIntegradorDTO){
    	
    	if (agenteIntegradorDTO.getCnpj() == null || agenteIntegradorDTO.getCnpj().isEmpty())
    		throw new InvalidFieldException("CNPJ inválido.");
    	
    	if (agenteIntegradorDTO.getNome() == null || agenteIntegradorDTO.getNome().isEmpty())
    		throw new InvalidFieldException("Nome inválido.");
    	
    	if (agenteIntegradorDTO.getTelefone() == null || agenteIntegradorDTO.getTelefone().isEmpty())
    		throw new InvalidFieldException("Telefone inválido.");
    	
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
    	
	}*/
    
    @PostMapping("/")
	public ResponseEntity<Object> criarAgenteIntegrador(@RequestBody AgenteIntegradorDTOv2 agenteIntegradorDTO){
		
    	if (agenteIntegradorDTO.getNome() == null || agenteIntegradorDTO.getNome().isEmpty())
    		throw new InvalidFieldException("Nome inválido.");
    	
    	if (agenteIntegradorDTO.getTelefone() == null || agenteIntegradorDTO.getTelefone().isEmpty())
    		throw new InvalidFieldException("Telefone inválido.");
    	
    	if (agenteIntegradorDTO.getCnpj() == null || agenteIntegradorDTO.getCnpj().isEmpty())
    		throw new InvalidFieldException("CNPJ inválido.");
    	
    	try {
			AgenteIntegrador agenteIntegrador = mapper.map(agenteIntegradorDTO, AgenteIntegrador.class);
			agenteIntegrador = agenteIntegradorService.criarAgenteIntegrador(agenteIntegrador);
			agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTOv2.class);
			return new ResponseEntity<>(agenteIntegradorDTO, HttpStatus.CREATED);
		} catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar agente integrador!");
		}
	}
    
    @PostMapping("/{idAgente}/convenio")
	public ResponseEntity<Object> criarConvenio(@PathVariable String idAgente, @RequestBody ConvenioDTO convenio){
    	
    	try {
    		int idInt = Integer.parseInt(idAgente);
    		
    		if (convenio.getNumero() < 1)
        		throw new InvalidFieldException("Número inválido.");
        	
        	if (convenio.getDescricao() == null || convenio.getDescricao().isEmpty())
        		throw new InvalidFieldException("Preencha a descrição.");
        	
        	if (convenio.getDataInicio() == null)
        		throw new InvalidFieldException("Insira uma data de início.");
        	
        	if (convenio.getDataFim() == null)
        		throw new InvalidFieldException("Insira uma data de fim.");
        	
        	if (convenio.getDataFim().before(convenio.getDataInicio()))
        		throw new InvalidFieldException("A data de fim deve ser posterior à data de início");
    		
			Optional<AgenteIntegrador> agenteFind = agenteIntegradorService.buscarPorId(idInt);
			
			if(agenteFind.isEmpty()) {
				throw new NotFoundException("Agente integrador não encontrado!");
			}
			
			AgenteIntegrador agente = agenteFind.get();
			Convenio convenioNovo = mapper.map(convenio, Convenio.class);
			convenioNovo = convenioService.criarConvenio(convenioNovo);
			convenioNovo = convenioService.associarAgenteAoConvenio(convenioNovo, agente);
			convenio = mapper.map(convenioNovo, ConvenioDTO.class);
			return new ResponseEntity<>(convenio, HttpStatus.CREATED);
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do agente integrador deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar agente integrador!");
		}
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<Object> buscarAgenteIntegradorPorId(@PathVariable String id) {
    	try {
    		int idInt = Integer.parseInt(id);
    		
    		if (idInt < 1) {
	    		throw new InvalidFieldException("Id do agente integrador inválido!");
	    	}
    		
    		Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(idInt);
    		
    		if(agenteIntegrador.isEmpty()) {
                throw new NotFoundException("Agente integrador não encontrado!");
            }
    		
    		AgenteIntegradorDTOv2 agenteIntegradorDTO = mapper.map(agenteIntegrador, AgenteIntegradorDTOv2.class);
    		return ResponseEntity.status(HttpStatus.OK).body(agenteIntegradorDTO);
    	} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do agente integrador deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (Exception e) {
            e.printStackTrace();
            throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar agente integrador!");
        }
	}

	@GetMapping("/")
	public ResponseEntity<List<AgenteIntegradorDTO>> listarAgentesIntegradores() {
		try {
			List<AgenteIntegrador> agentesIntegradores = agenteIntegradorService.listarAgentesIntegradores();
			
			List<AgenteIntegradorDTO> agentesIntegradoresDTO = agentesIntegradores.stream()
					.map(ap -> mapper.map(ap, AgenteIntegradorDTO.class))
					.collect(Collectors.toList());
			return ResponseEntity.ok().body(agentesIntegradoresDTO);
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar apólices!");
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarAgenteIntegrador(@PathVariable String id, @RequestBody AgenteIntegradorDTO agenteIntegradorDTO){
	    try {
	    	int idInt = Integer.parseInt(id);
	    	
	    	if (idInt < 1) {
	    		throw new InvalidFieldException("Id da seguradora inválido!");
	    	}
	    	
			Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(idInt);
		    
		    if(agenteIntegrador.isPresent()) {
		    	if (agenteIntegradorDTO.getNome().isEmpty())
		    		throw new InvalidFieldException("Nome inválido.");
		    	
		    	if (agenteIntegradorDTO.getTelefone().isEmpty())
		    		throw new InvalidFieldException("Telefone inválido.");
		    	
		    	if (agenteIntegradorDTO.getCnpj().isEmpty())
		    		throw new InvalidFieldException("CNPJ inválido.");
		    	
		        AgenteIntegrador agenteIntegradorAtualizado = mapper.map(agenteIntegradorDTO, AgenteIntegrador.class);
		        agenteIntegradorAtualizado.setId(idInt);
		        agenteIntegradorAtualizado = agenteIntegradorService.atualizarAgenteIntegrador(agenteIntegradorAtualizado);
		        AgenteIntegradorDTO agenteIntegradorDTOAtualizado = mapper.map(agenteIntegradorAtualizado, AgenteIntegradorDTO.class);
		        return ResponseEntity.ok().body(agenteIntegradorDTOAtualizado);
		    } else {
		    	throw new NotFoundException("Agente integrador não encontrado!");
		    }
	    } catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do agente integrador deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar agente integrador!");
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirAgenteIntegrador(@PathVariable String id){
		try {
			int idInt = Integer.parseInt(id);
	    	
	    	if (idInt < 1) {
	    		throw new InvalidFieldException("Id da seguradora inválido!");
	    	}
	    	
		    Optional<AgenteIntegrador> agenteIntegrador = agenteIntegradorService.buscarPorId(idInt);
		    if(agenteIntegrador.isPresent()) {
		        agenteIntegradorService.excluirAgenteIntegrador(agenteIntegrador.get());
		        return ResponseEntity.noContent().build();
		    } else {
		    	throw new NotFoundException("Agente integrador não encontrado!");
		    }
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um inteiro!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao excluir agente integrador!");
		}
	}

}
