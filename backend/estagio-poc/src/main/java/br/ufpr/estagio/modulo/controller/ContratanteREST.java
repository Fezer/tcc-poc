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

import br.ufpr.estagio.modulo.dto.ContratanteDTO;
import br.ufpr.estagio.modulo.dto.EnderecoDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.service.ContratanteService;


@CrossOrigin
@RestController
@RequestMapping("/contratante")
public class ContratanteREST {
	
	@Autowired
    private ContratanteService contratanteService;
        
    @Autowired
	private ModelMapper mapper;
        
    // TO-DO: Adicionar validação para garantir que contratante tem CPF **OU** CNPJ
    @PostMapping("/")
	public ResponseEntity<Object> criarContratante(@RequestBody ContratanteDTO contratanteDTO){
		try {
			Contratante contratante = mapper.map(contratanteDTO, Contratante.class);
		    
			if (contratante.getNome() == null || contratante.getNome().isEmpty())
	    		throw new InvalidFieldException("Preencha o nome.");
			
			if (contratante.getTelefone() == null || contratante.getTelefone().isEmpty())
	    		throw new InvalidFieldException("Preencha o telefone.");
			
			/*if ((contratante.getCnpj() == null || contratante.getCnpj().isEmpty()) &&
				    (contratante.getCpf() == null || contratante.getCpf().isEmpty())) {
				    throw new InvalidFieldException("Preencha o CNPJ ou o CPF.");
				}*/
	    	
			/*if (contratante.getCnpj() != null && contratante.getCpf() != null) {
				if (!contratante.getCnpj().isEmpty() && !contratante.getCpf().isEmpty())
					throw new InvalidFieldException("Contratante só pode ter CNPJ ou CPF.");
			}*/
			
			if (contratante.getTipo() == EnumTipoContratante.PessoaFisica && contratante.getCpf() == null)
				throw new InvalidFieldException("Contratante do tipo Pessoa Física deve possuir CPF.");
			
			if (contratante.getTipo() == EnumTipoContratante.PessoaFisica && contratante.getCpf().isEmpty())
				throw new InvalidFieldException("CPF inválido.");
				
			if (contratante.getTipo() == EnumTipoContratante.PessoaJuridica && contratante.getCnpj() == null)
				throw new InvalidFieldException("Contratante do tipo Pessoa Jurídica deve possuir CNPJ.");

			if (contratante.getTipo() == EnumTipoContratante.PessoaJuridica && contratante.getCnpj().isEmpty())
				throw new InvalidFieldException("CNPJ inválido.");
			
			if (contratante.getTipo() == EnumTipoContratante.PessoaFisica && (contratante.getCnpj() != null))
				throw new InvalidFieldException("Contratante do tipo Pessoa Física não deve possuir CNPJ.");
			
			if (contratante.getTipo() == EnumTipoContratante.PessoaJuridica && (contratante.getCpf() != null))
				throw new InvalidFieldException("Contratante do tipo Pessoa Jurídica não deve possuir CPF.");
	    	
			if (contratante.getRepresentanteEmpresa() == null || contratante.getRepresentanteEmpresa().isEmpty())
	    		throw new InvalidFieldException("Preencha o nome do representante da empresa.");
			
			contratante = contratanteService.criarContratante(contratante);
			
			contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
			
			return new ResponseEntity<>(contratanteDTO, HttpStatus.CREATED);	
			
		} catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<Object> buscarContratantePorId(@PathVariable String id) {
    	try {
    		long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1) {
	    		throw new InvalidFieldException("Id da seguradora inválido!");
	    	}
		    
	    	Optional<Contratante> contratante = contratanteService.buscarPorId(idLong);
	    	
		    if(contratante.isEmpty()) {
	            throw new NotFoundException("Contratante não encontrado!");
	        }
		    ContratanteDTO contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
		    return ResponseEntity.status(HttpStatus.OK).body(contratanteDTO);
	
    	} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
    }
    
    @GetMapping("/nome/{nomeContratante}")
	public ResponseEntity<Object> buscarContratanteNome(@PathVariable String nomeContratante) {
	    try {
	    	//Optional<Contratante> contratanteFind = contratanteService.buscarPorNome(nomeContratante);
	    	List<Contratante> contratantesFind = contratanteService.buscarPorNome(nomeContratante);
	    
			if(contratantesFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
			    //ContratanteDTO contratanteDTO = mapper.map(contratanteFind.get(), ContratanteDTO.class);
				List<ContratanteDTO> contratantesDTO = contratantesFind.stream()
			            .map(ap -> mapper.map(ap, ContratanteDTO.class))
			            .collect(Collectors.toList());
			    return ResponseEntity.status(HttpStatus.OK).body(contratantesDTO);
			}
	    } catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/nome/contendo/{nomeContratante}")
	public ResponseEntity<?> buscarContratantePorNomeSimilar(@PathVariable String nomeContratante) {
	    try {
	    	List<Contratante> contratantesFind = contratanteService.buscarPorNomeContendo(nomeContratante);
			if(contratantesFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
			    List<ContratanteDTO> contratantesDTO = contratantesFind.stream()
			            .map(ap -> mapper.map(ap, ContratanteDTO.class))
			            .collect(Collectors.toList());
			    return ResponseEntity.ok().body(contratantesDTO);
			}
	    } catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/nome/comecandoPor/{nomeContratante}")
	public ResponseEntity<?> buscarContratantePorNomeComecandoPor(@PathVariable String nomeContratante) {
	    try {
	    	List<Contratante> contratantesFind = contratanteService.buscarContratantePorNomeComecandoPor(nomeContratante);
			if(contratantesFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
			    List<ContratanteDTO> contratantesDTO = contratantesFind.stream()
			            .map(ap -> mapper.map(ap, ContratanteDTO.class))
			            .collect(Collectors.toList());
			    return ResponseEntity.ok().body(contratantesDTO);
			}
	    } catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<ContratanteDTO>> listarContratantes() {
		try {
		    List<Contratante> contratantes = contratanteService.listarContratantes();
		    List<ContratanteDTO> contratantesDTO = contratantes.stream()
		            .map(ap -> mapper.map(ap, ContratanteDTO.class))
		            .collect(Collectors.toList());
		    return ResponseEntity.ok().body(contratantesDTO);
		} catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarContratante(@PathVariable String id, @RequestBody ContratanteDTO contratanteDTO){
	    try {

	    	long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1) {
	    		throw new InvalidFieldException("Id da seguradora inválido!");
	    	}
	    	
			Optional<Contratante> contratante = contratanteService.buscarPorId(idLong);
			
		    if(contratante.isPresent()) {
		    	if (contratanteDTO.getNome() == null || contratanteDTO.getNome().isEmpty())
		    		throw new InvalidFieldException("Preencha o nome.");
				
				if (contratanteDTO.getTelefone() == null || contratanteDTO.getTelefone().isEmpty())
		    		throw new InvalidFieldException("Preencha o telefone.");
				
				/*if ((contratanteDTO.getCnpj() == null || contratanteDTO.getCnpj().isEmpty()) &&
					    (contratanteDTO.getCpf() == null || contratanteDTO.getCpf().isEmpty())) {
					    throw new InvalidFieldException("Preencha o CNPJ ou o CPF.");
					}*/
		    	
				/*if (contratanteDTO.getCnpj() != null && contratanteDTO.getCpf() != null) {
					if (!contratanteDTO.getCnpj().isEmpty() && !contratanteDTO.getCpf().isEmpty())
						throw new InvalidFieldException("Contratante só pode ter CNPJ ou CPF.");
				}*/
				
				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaFisica && contratanteDTO.getCpf() == null)
					throw new InvalidFieldException("Contratante do tipo Pessoa Física deve possuir CPF.");
				
				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaFisica && contratanteDTO.getCpf().isEmpty())
					throw new InvalidFieldException("CPF inválido.");
					
				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaJuridica && contratanteDTO.getCnpj() == null)
					throw new InvalidFieldException("Contratante do tipo Pessoa Jurídica deve possuir CNPJ.");

				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaJuridica && contratanteDTO.getCnpj().isEmpty())
					throw new InvalidFieldException("CNPJ inválido.");
				
				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaFisica && (contratanteDTO.getCnpj() != null))
					throw new InvalidFieldException("Contratante do tipo Pessoa Física não deve possuir CNPJ.");
				
				if (contratanteDTO.getTipo() == EnumTipoContratante.PessoaJuridica && (contratanteDTO.getCpf() != null))
					throw new InvalidFieldException("Contratante do tipo Pessoa Jurídica não deve possuir CPF.");
		    	
				if (contratanteDTO.getRepresentanteEmpresa() == null || contratanteDTO.getRepresentanteEmpresa().isEmpty())
		    		throw new InvalidFieldException("Preencha o nome do representante da empresa.");
				
		    	Contratante contratanteAtualizado = mapper.map(contratanteDTO, Contratante.class);
		    	contratanteAtualizado.setId(idLong);
		    	contratanteAtualizado = contratanteService.atualizarContratante(contratanteAtualizado);
		        ContratanteDTO contratanteDTOAtualizado = mapper.map(contratanteAtualizado, ContratanteDTO.class);
		        return ResponseEntity.ok().body(contratanteDTOAtualizado);
		    } else {
		    	throw new NotFoundException("Contratante não encontrado!");
		    }
	    } catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um número!");
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
	public ResponseEntity<?> excluirContratante(@PathVariable String id){
	    try {

	    	long idLong = Long.parseLong(id);
	    	
	    	if (idLong < 1) {
	    		throw new InvalidFieldException("Id do contratante inválido!");
	    	}
	    	
			Optional<Contratante> contratante = contratanteService.buscarPorId(idLong);
	    
		    if(contratante.isPresent()) {
		    	contratanteService.excluirContratante(contratante.get());
		        return ResponseEntity.noContent().build();
		    } else {
		    	throw new NotFoundException("Contratante não encontrado!");
		    }
	    } catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
    @PostMapping("/{idContratante}/endereco/")
	public ResponseEntity<Object> criarEnderecoContratante(@PathVariable String idContratante, @RequestBody EnderecoDTO enderecoDTO){
		try {
			long idLong = Long.parseLong(idContratante);
		    	
		    if (idLong < 1) {
		   		throw new InvalidFieldException("Id do contratante inválido!");
		   	}
		    	
			Optional<Contratante> contratanteFind = contratanteService.buscarPorId(idLong);
			
			if(contratanteFind.isEmpty()) {
				throw new NotFoundException("Contratante não encontrado!");
			} else {
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
		    	
				Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
				Contratante contratante = contratanteService.criarEnderecoContratante(contratanteFind.get(), endereco);
				ContratanteDTO contratanteDTO = mapper.map(contratante, ContratanteDTO.class);
				return ResponseEntity.ok().body(contratanteDTO);
			}
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id da seguradora deve ser um número!");
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
