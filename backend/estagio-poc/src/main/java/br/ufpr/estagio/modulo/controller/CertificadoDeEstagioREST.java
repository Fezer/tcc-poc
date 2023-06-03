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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.CertificadoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/certificadoDeEstagio")
public class CertificadoDeEstagioREST {

	@Autowired
    private CertificadoDeEstagioService certificadoDeEstagioService;
        
    @Autowired
	private ModelMapper mapper;
    
    @PostMapping("/")
	public ResponseEntity<CertificadoDeEstagioDTO> criarCertificadoDeEstagio() {
	    
    	CertificadoDeEstagio certificadoDeEstagio = new CertificadoDeEstagio();
    	certificadoDeEstagio = certificadoDeEstagioService.novoCertificadoDeEstagio(certificadoDeEstagio);
    	
	    CertificadoDeEstagioDTO certificadoDeEstagioDTO = mapper.map(certificadoDeEstagio, CertificadoDeEstagioDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(certificadoDeEstagioDTO);
	}
        
    @GetMapping("/")
	public ResponseEntity<List<CertificadoDeEstagioDTO>> listarCertificadosDeEstagio() {
	    try {
		    List<CertificadoDeEstagio> listaCertificadosDeEstagio = certificadoDeEstagioService.listarTodosCertificadosDeEstagio();
		    List<CertificadoDeEstagioDTO> listaCertificadosDeEstagioDTO = listaCertificadosDeEstagio.stream()
		            .map(ap -> mapper.map(ap, CertificadoDeEstagioDTO.class))
		            .collect(Collectors.toList());
		    return ResponseEntity.ok().body(listaCertificadosDeEstagioDTO);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/{idCertificadoDeEstagio}")
	public ResponseEntity<Object> buscarCertificadoDeEstagioPorId(@PathVariable String idCertificadoDeEstagio) {
	    try {
    		long idLongCertificadoDeEstagio = Long.parseLong(idCertificadoDeEstagio);
	    	
		    if (idLongCertificadoDeEstagio < 1) {
		   		throw new InvalidFieldException("Id do certificado de estágio inválido!");
		   	}
		    
    		Optional<CertificadoDeEstagio> certificadoDeEstagio = certificadoDeEstagioService.buscarCertificadoDeEstagioPorId(idLongCertificadoDeEstagio);
    	
	    
		    if (certificadoDeEstagio.isEmpty()) {
				throw new NotFoundException("Certificado de estágio não encontrado!");
			}
		    
		    CertificadoDeEstagioDTO certificadoDeEstagioDTO = mapper.map(certificadoDeEstagio.get(), CertificadoDeEstagioDTO.class);
		    return ResponseEntity.status(HttpStatus.OK).body(certificadoDeEstagioDTO);
    	} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do estágio deve ser um número!");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch (InvalidFieldException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

    @DeleteMapping("/{idCertificadoDeEstagio}")
    public ResponseEntity<?> excluirCertificadoDeEstagio(@PathVariable String idCertificadoDeEstagio){
    	try {
    		long idLongCertificadoDeEstagio = Long.parseLong(idCertificadoDeEstagio);
	    	
		    if (idLongCertificadoDeEstagio < 1) {
		   		throw new InvalidFieldException("Id do certificado de estágio inválido!");
		   	}
		    
    		Optional<CertificadoDeEstagio> certificadoDeEstagio = certificadoDeEstagioService.buscarCertificadoDeEstagioPorId(idLongCertificadoDeEstagio);
		    
		    if (certificadoDeEstagio.isEmpty()) {
		    	throw new NotFoundException("Certificado de estágio não encontrado!");
			}
		    
		    certificadoDeEstagioService.deletarCertificadoDeEstagio(certificadoDeEstagio.get());
		    
		    return ResponseEntity.noContent().build();
		    
    	} catch (NotFoundException ex) {
	        ErrorResponse response = new ErrorResponse(ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (NumberFormatException ex) {
	        ErrorResponse response = new ErrorResponse("Id do estágio deve ser um número!");
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
