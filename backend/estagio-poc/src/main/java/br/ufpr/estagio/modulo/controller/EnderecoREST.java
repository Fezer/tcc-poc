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

import br.ufpr.estagio.modulo.dto.EnderecoDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.service.EnderecoService;

@CrossOrigin
@RestController
@RequestMapping("/endereco")
public class EnderecoREST {
	
	@Autowired
    private EnderecoService enderecoService;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/")
	public ResponseEntity<Object> inserir(@RequestBody EnderecoDTO enderecoDTO){
		try {
		
			if (enderecoDTO.getRua() == null || enderecoDTO.getRua().isEmpty())
	    		throw new InvalidFieldException("Rua inválida.");
	    	
	    	if (enderecoDTO.getNumero() < 1)
	    		throw new InvalidFieldException("Número inválido.");
	    	
	    	//if (enderecoDTO.getComplemento() == null || enderecoDTO.getComplemento().isEmpty())
	    		//throw new InvalidFieldException("Complemento inválido.");
	    	
	    	if (enderecoDTO.getCidade() == null || enderecoDTO.getCidade().isEmpty())
	    		throw new InvalidFieldException("Cidade inválida.");
	    	
	    	if (enderecoDTO.getUf() == null || enderecoDTO.getUf().isEmpty())
	    		throw new InvalidFieldException("Estado inválido.");
	    	
	    	if (enderecoDTO.getCep() == null || enderecoDTO.getCep().isEmpty())
	    		throw new InvalidFieldException("CEP inválido.");
			
	    	// TO-DO: Validação de pessoa -> enderecoDTO.getPessoa()getCadaAtributo()...
	    	
		Endereco newEndereco = enderecoService.criarEndereco(mapper.map(enderecoDTO, Endereco.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(newEndereco, EnderecoDTO.class));
		} catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<EnderecoDTO>> listarTodos(){

		try {
			List<Endereco> enderecos = enderecoService.listarEnderecos();
			/*if(lista.isEmpty()) {
				throw new NotFoundException("Nenhum endereço encontrado!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(lista.stream().map(e -> mapper.map(e, EnderecoDTO.class)).collect(Collectors.toList()));
			}*/
			List<EnderecoDTO> enderecosDTO = enderecos.stream()
    	            .map(ap -> mapper.map(ap, EnderecoDTO.class))
    	            .collect(Collectors.toList());
    	    return ResponseEntity.ok().body(enderecosDTO);
		}catch(Exception e) {
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> listarEndereco(@PathVariable String id){
		try {
			long idLong = Long.parseLong(id);
	    	
		    if (idLong < 1) {
		   		throw new InvalidFieldException("Id do endereço inválido!");
		   	}
		    
			Optional<Endereco> enderecoFind = enderecoService.buscarEndercoPorId(idLong);
			
			if(enderecoFind.isEmpty()) {
				throw new NotFoundException("Endereço não encontrado!");
			} else {
				Endereco endereco = enderecoFind.get();
				EnderecoDTO enderecoDTO = mapper.map(endereco, EnderecoDTO.class);
				return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do endereço deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody EnderecoDTO endereco){
		try {
			long idLong = Long.parseLong(id);
	    	
		    if (idLong < 1) {
		   		throw new InvalidFieldException("Id do endereço inválido!");
		   	}
		    
			Optional<Endereco> enderecoFind = enderecoService.buscarEndercoPorId(idLong);
			
			if(enderecoFind.isEmpty()) {
				throw new NotFoundException("Endereço não encontrado!");
			} else {
				if (endereco.getRua() == null || endereco.getRua().isEmpty())
		    		throw new InvalidFieldException("Rua inválida.");
	    	
		    	if (endereco.getNumero() < 1)
		    		throw new InvalidFieldException("Número inválido.");
		    	
		    	//if (enderecoDTO.getComplemento() == null || enderecoDTO.getComplemento().isEmpty())
		    		//throw new InvalidFieldException("Complemento inválido.");
		    	
		    	if (endereco.getCidade() == null || endereco.getCidade().isEmpty())
		    		throw new InvalidFieldException("Cidade inválida.");
		    	
		    	if (endereco.getUf() == null || endereco.getUf().isEmpty())
		    		throw new InvalidFieldException("Estado inválido.");
		    	
		    	if (endereco.getCep() == null || endereco.getCep().isEmpty())
		    		throw new InvalidFieldException("CEP inválido.");
		    	
				Endereco enderecoAtualizado = enderecoService.atualizarEndereco(enderecoFind.get(), endereco);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(enderecoAtualizado, EnderecoDTO.class));
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do endereço deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id){
		try {
			long idLong = Long.parseLong(id);
	    	
		    if (idLong < 1) {
		   		throw new InvalidFieldException("Id do endereço inválido!");
		   	}
		    
		    Optional<Endereco> enderecoFind = enderecoService.buscarEndercoPorId(idLong);
		    
			if(enderecoFind.isEmpty()) {
				throw new NotFoundException("Endereço não encontrado!");
			} else {
				enderecoService.excluirEndereco(enderecoFind.get());
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do endereço deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
}
