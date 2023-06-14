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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.RelatorioDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoRelatorio;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/relatorioDeEstagio")
public class RelatorioDeEstagioREST {

	@Autowired
    private RelatorioDeEstagioService relatorioDeEstagioService;
        
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/")
	public ResponseEntity<RelatorioDeEstagioDTO> criarRelatorioDeEstagio() {
	    
    	RelatorioDeEstagio relatorioDeEstagio = new RelatorioDeEstagio();
    	relatorioDeEstagio = relatorioDeEstagioService.novoRelatorio(relatorioDeEstagio);
    	
	    RelatorioDeEstagioDTO relatorioDeEstagioDTO = mapper.map(relatorioDeEstagio, RelatorioDeEstagioDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(relatorioDeEstagioDTO);
	}
        
    @GetMapping("/")
	public ResponseEntity<Object> listarRelatoriosDeEstagio() {
    	try {
		    List<RelatorioDeEstagio> listaRelatorios = relatorioDeEstagioService.listarTodosRelatorios();
		    List<RelatorioDeEstagioDTO> listaRelatoriosDTO = listaRelatorios.stream()
		            .map(ap -> mapper.map(ap, RelatorioDeEstagioDTO.class))
		            .collect(Collectors.toList());
		    return ResponseEntity.ok().body(listaRelatoriosDTO);
    	} catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
    
    @GetMapping("/{idRelatorio}")
	public ResponseEntity<Object> buscarRelatorioDeEstagioPorId(@PathVariable String idRelatorio) {
    	try {
    		long idRelatorioLong = Long.parseLong(idRelatorio);
	    	
		    if (idRelatorioLong < 1) {
		   		throw new InvalidFieldException("Id do relatório de estágio inválido!");
		   	}
		    
		    Optional<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorioLong);
		    
		    if (relatorioDeEstagio.isEmpty()) {
				throw new NotFoundException("Relatório de estágio não encontrado!");
			}
		    
		    RelatorioDeEstagioDTO relatorioDeEstagioDTO = mapper.map(relatorioDeEstagio.get(), RelatorioDeEstagioDTO.class);
		    return ResponseEntity.status(HttpStatus.OK).body(relatorioDeEstagioDTO);
    	} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do relatório de estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

    @PutMapping("/{idRelatorio}")
    public ResponseEntity<Object> atualizarRelatorioDeEstagioAvaliacao(@PathVariable String idRelatorio, @RequestBody RelatorioDeEstagioDTO relatorioDeEstagioDTO){
    	try {
	    	long idRelatorioLong = Long.parseLong(idRelatorio);
		    	
			if (idRelatorioLong < 1) {
				throw new InvalidFieldException("Id do relatório de estágio inválido!");
			}
			    
			Optional<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorioLong);
		    	    
			if (relatorioDeEstagio.isEmpty()) {
				throw new NotFoundException("Relatório de estágio não encontrado!");
			}
			    
			if (relatorioDeEstagioDTO.getAvalAtividades() == null) {
			    throw new InvalidFieldException("Campo 'avaliacaoAtividades' não informado!");
			}

			if (relatorioDeEstagioDTO.getAvalFormacaoProfissional() == null) {
			    throw new InvalidFieldException("Campo 'avaliacaoFormacaoProfissional' não informado!");
			}
			    
		    if (relatorioDeEstagioDTO.getAvalRelacoesInterpessoais() == null) {
		        throw new InvalidFieldException("Campo 'avaliacaoRelacoesInterpessoais' não informado!");
		    }
			    
		    if (relatorioDeEstagioDTO.getAvalDesenvolvimentoAtividades() == null) {
		        throw new InvalidFieldException("Campo 'avaliacaoDesenvolvimentoAtividades' não informado!");
		    }
		    
		    if (relatorioDeEstagioDTO.getAvalContribuicaoEstagio() == null) {
		        throw new InvalidFieldException("Campo 'avaliacaoContribuicaoEstagio' não informado!");
		    }
		    
		    if (relatorioDeEstagioDTO.getAvalEfetivacao() == null) {
		        throw new InvalidFieldException("Campo 'avaliacaoEfetivação' não informado!");
		    }
		    
		    if (relatorioDeEstagioDTO.getConsideracoes() == null || relatorioDeEstagioDTO.getConsideracoes().isEmpty()) {
		        throw new InvalidFieldException("Campo 'considerações' não informado!");
		    }
		        
		    RelatorioDeEstagio relatorioDeEstagioAtualizado = relatorioDeEstagioService.atualizarRelatorioAvaliacao(relatorioDeEstagio.get(), relatorioDeEstagioDTO);
		    relatorioDeEstagioDTO = mapper.map(relatorioDeEstagioAtualizado, RelatorioDeEstagioDTO.class);
	        return ResponseEntity.ok().body(relatorioDeEstagioDTO);
    	} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do relatório de estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
    
    @PutMapping("/{idRelatorio}/definirTipo")
    public ResponseEntity<Object> definirTipoDeRelatorioDeEstagio(@PathVariable String idRelatorio, @RequestParam String tipoRelatorio){
    	try {
	    	long idRelatorioLong = Long.parseLong(idRelatorio);
		    	
			if (idRelatorioLong < 1) {
				throw new InvalidFieldException("Id do relatório de estágio inválido!");
			}
			    
			Optional<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorioLong);
	    	    
		    if (relatorioDeEstagio.isEmpty()) {
				throw new NotFoundException("Relatório de estágio não encontrado!");
			}
		    EnumTipoRelatorio enumTipoRelatorio = EnumTipoRelatorio.valueOf(tipoRelatorio);
		    
		    RelatorioDeEstagio relatorioDeEstagioAtualizado = relatorioDeEstagioService.definirTipoDeRelatorioDeEstagio(relatorioDeEstagio.get(), enumTipoRelatorio);
		    RelatorioDeEstagioDTO relatorioDeEstagioDTO = mapper.map(relatorioDeEstagioAtualizado, RelatorioDeEstagioDTO.class);
	        return ResponseEntity.ok().body(relatorioDeEstagioDTO);
    	} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do relatório de estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (IllegalArgumentException ex) {
			ErrorResponse response = new ErrorResponse("Valor para definir tipo do relatório de estágio inválido.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }

    @DeleteMapping("/{idRelatorio}")
    public ResponseEntity<?> excluirRelatorioDeEstagio(@PathVariable String idRelatorio){
    	try {
	    	long idRelatorioLong = Long.parseLong(idRelatorio);
		    	
			if (idRelatorioLong < 1) {
				throw new InvalidFieldException("Id do relatório de estágio inválido!");
			}
			    
			Optional<RelatorioDeEstagio> relatorioDeEstagio = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorioLong);
	    
		    if (relatorioDeEstagio.isEmpty()) {
				throw new NotFoundException("Relatório de estágio não encontrado!");
			}
		    
		    relatorioDeEstagioService.deletarRelatorioDoEstagio(relatorioDeEstagio.get());
		    
		    return ResponseEntity.noContent().build();
		} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do relatório de estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (RuntimeException ex) {
			ex.printStackTrace();
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Desculpe, mas um erro inesperado ocorreu e não possível processar sua requisição.");
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
}
