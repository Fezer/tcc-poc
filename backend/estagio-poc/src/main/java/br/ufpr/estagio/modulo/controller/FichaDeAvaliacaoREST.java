package br.ufpr.estagio.modulo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.dto.FichaDeAvaliacaoDTO;
import br.ufpr.estagio.modulo.enums.EnumAvaliacaoAcomp;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.service.FichaDeAvaliacaoService;

@CrossOrigin
@RestController
@RequestMapping("/fichaDeAvaliacao")
public class FichaDeAvaliacaoREST {

	@Autowired
    private FichaDeAvaliacaoService fichaDeAvaliacaoService;
        
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/")
	public ResponseEntity<Object> criarFichaDeAvaliacao() {
	    try {
	    	FichaDeAvaliacao fichaDeAvaliacao = new FichaDeAvaliacao();
	    	fichaDeAvaliacao = fichaDeAvaliacaoService.novoFichaDeAvaliacao(fichaDeAvaliacao);
	    	
		    FichaDeAvaliacaoDTO fichaDeAvaliacaoDTO = mapper.map(fichaDeAvaliacao, FichaDeAvaliacaoDTO.class);
		    return ResponseEntity.status(HttpStatus.OK).body(fichaDeAvaliacaoDTO);
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
        
    @GetMapping("/")
	public ResponseEntity<Object> listarFichasDeAvaliacao() {
    	try {
		    List<FichaDeAvaliacao> listaFichasDeAvaliacao = fichaDeAvaliacaoService.listarTodosFichasDeAvaliacao();
		    List<FichaDeAvaliacaoDTO> listaFichasDeAvaliacaoDTO = listaFichasDeAvaliacao.stream()
		            .map(ap -> mapper.map(ap, FichaDeAvaliacaoDTO.class))
		            .collect(Collectors.toList());
		    return ResponseEntity.ok().body(listaFichasDeAvaliacaoDTO);
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
    
    @GetMapping("/{idFichaDeAvaliacao}")
	public ResponseEntity<Object> buscarFichaDeAvaliacaoPorId(@PathVariable String idFichaDeAvaliacao) {
    	try {
    		long idLongFichaDeAvaliacao = Long.parseLong(idFichaDeAvaliacao);
	    	
		    if (idLongFichaDeAvaliacao < 1) {
		   		throw new InvalidFieldException("Id da ficha de avaliação inválido!");
		   	}
		    
    		Optional<FichaDeAvaliacao> fichaDeAvaliacao = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idLongFichaDeAvaliacao);
		    
		    if (fichaDeAvaliacao.isEmpty()) {
				throw new NotFoundException("Ficha de avaliação não encontrada!");
			}
		    
		    FichaDeAvaliacaoDTO fichaDeAvaliacaoDTO = mapper.map(fichaDeAvaliacao.get(), FichaDeAvaliacaoDTO.class);
		    return ResponseEntity.status(HttpStatus.OK).body(fichaDeAvaliacaoDTO);
    	} catch (NotFoundException ex) {
    		ex.printStackTrace();
    		ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id da ficha de avaliação deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	    	ex.printStackTrace();
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

    @PutMapping("/{idFichaDeAvaliacao}")
    public ResponseEntity<Object> atualizarFichaDeAvaliacao(@PathVariable String idFichaDeAvaliacao, @RequestBody FichaDeAvaliacaoDTO fichaDeAvaliacaoDTO){
    	try {
    		long idLongFichaDeAvaliacao = Long.parseLong(idFichaDeAvaliacao);
	    	
		    if (idLongFichaDeAvaliacao < 1) {
		   		throw new InvalidFieldException("Id da ficha de avaliação inválido!");
		   	}
		    
    		Optional<FichaDeAvaliacao> fichaDeAvaliacao = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idLongFichaDeAvaliacao);
		    
		    if (fichaDeAvaliacao.isEmpty()) {
				throw new NotFoundException("Ficha de avaliação não encontrada!");
			}
		    
		    if (fichaDeAvaliacaoDTO.getTotalHorasEstagioEfetivamenteRealizadas() < 1) {
		        throw new InvalidFieldException("Total de horas inválido!");
		    }

		    if (fichaDeAvaliacaoDTO.getAtividadesRealizadasConsideracoes() == null || fichaDeAvaliacaoDTO.getAtividadesRealizadasConsideracoes().isEmpty()) {
		        throw new InvalidFieldException("Campo 'atividadesRealizadasConsideracoes' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAcompanhamentoOrientador() == null) {
		        throw new InvalidFieldException("Campo 'acompanhamentoOrientador' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAcompanhamentoOrientadorComentario() == null || fichaDeAvaliacaoDTO.getAcompanhamentoOrientadorComentario().isEmpty()) {
		        throw new InvalidFieldException("Campo 'acompanhamentoOrientadorComentario' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAcompanhamentoCoordenador() == null) {
		        throw new InvalidFieldException("Campo 'acompanhamentoCoordenador' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAcompanhamentoCoordenadorComentario() == null || fichaDeAvaliacaoDTO.getAcompanhamentoCoordenadorComentario().isEmpty()) {
		        throw new InvalidFieldException("Campo 'acompanhamentoCoordenadorComentario' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getContribuicaoEstagio() == null || fichaDeAvaliacaoDTO.getContribuicaoEstagio().isEmpty()) {
		        throw new InvalidFieldException("Campo 'contribuicaoEstagio' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAvalPontualidade() == null) {
		        throw new InvalidFieldException("Campo 'avalPontualidade' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAvalCriatividade() == null) {
		        throw new InvalidFieldException("Campo 'avalCriatividade' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAvalProtagonismo() == null) {
		        throw new InvalidFieldException("Campo 'avalProtagonismo' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAvalResponsabilidade() == null) {
		        throw new InvalidFieldException("Campo 'avalResponsabilidade' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAvalConduta() == null) {
		        throw new InvalidFieldException("Campo 'avalConduta' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAvalDominioTecnico() == null) {
		        throw new InvalidFieldException("Campo 'avalDominioTecnico' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAvalHabilidades() == null) {
		        throw new InvalidFieldException("Campo 'avalHabilidades' não informado!");
		    }

		    if (fichaDeAvaliacaoDTO.getAvalEfetivacao() == null) {
		        throw new InvalidFieldException("Campo 'avalEfetivacao' não informado!");
		    }

		    FichaDeAvaliacao fichaDeAvaliacaoAtualizada = fichaDeAvaliacaoService.atualizarFichaAvaliacao(fichaDeAvaliacao.get(), fichaDeAvaliacaoDTO);
		    fichaDeAvaliacaoDTO = mapper.map(fichaDeAvaliacaoAtualizada, FichaDeAvaliacaoDTO.class);
	        return ResponseEntity.ok().body(fichaDeAvaliacaoDTO);
    	} catch (NotFoundException ex) {
    		ex.printStackTrace();
    		ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id da ficha de avaliação deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (HttpMessageNotReadableException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Valor de avaliação inválido.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (IllegalArgumentException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Valor de avaliação inválido.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (InvalidFieldException ex) {
			ex.printStackTrace();
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

    @DeleteMapping("/{idFichaDeAvaliacao}")
    public ResponseEntity<?> excluirFichaDeAvaliacao(@PathVariable String idFichaDeAvaliacao){
    	try {
    		long idLongFichaDeAvaliacao = Long.parseLong(idFichaDeAvaliacao);
	    	
		    if (idLongFichaDeAvaliacao < 1) {
		   		throw new InvalidFieldException("Id da ficha de avaliação inválido!");
		   	}
		    
    		Optional<FichaDeAvaliacao> fichaDeAvaliacao = fichaDeAvaliacaoService.buscarFichaDeAvaliacaoPorId(idLongFichaDeAvaliacao);
		    
		    if (fichaDeAvaliacao.isEmpty()) {
				throw new NotFoundException("Ficha de avaliação não encontrada!");
			}
		    
		    fichaDeAvaliacaoService.deletarFichaDeAvaliacao(fichaDeAvaliacao.get());
		    
		    return ResponseEntity.noContent().build();
    	} catch (NotFoundException ex) {
    		ex.printStackTrace();
    		ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	    	ex.printStackTrace();
	    	ErrorResponse response = new ErrorResponse("Id da ficha de avaliação deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	    	ex.printStackTrace();
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
