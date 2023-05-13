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
import br.ufpr.estagio.modulo.exception.NotFoundException;
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
	    List<CertificadoDeEstagio> listaCertificadosDeEstagio = certificadoDeEstagioService.listarTodosCertificadosDeEstagio();
	    List<CertificadoDeEstagioDTO> listaCertificadosDeEstagioDTO = listaCertificadosDeEstagio.stream()
	            .map(ap -> mapper.map(ap, CertificadoDeEstagioDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok().body(listaCertificadosDeEstagioDTO);
	}
    
    @GetMapping("/{idCertificadoDeEstagio}")
	public ResponseEntity<CertificadoDeEstagioDTO> buscarCertificadoDeEstagioPorId(@PathVariable long idCertificadoDeEstagio) {
	    Optional<CertificadoDeEstagio> certificadoDeEstagio = certificadoDeEstagioService.buscarCertificadoDeEstagioPorId(idCertificadoDeEstagio);
	    
	    if (certificadoDeEstagio.isEmpty()) {
			throw new NotFoundException("Certificado de estágio não encontrado!");
		}
	    
	    CertificadoDeEstagioDTO certificadoDeEstagioDTO = mapper.map(certificadoDeEstagio.get(), CertificadoDeEstagioDTO.class);
	    return ResponseEntity.status(HttpStatus.OK).body(certificadoDeEstagioDTO);
	}

    @DeleteMapping("/{idCertificadoDeEstagio}")
    public ResponseEntity<Void> excluirCertificadoDeEstagio(@PathVariable long idCertificadoDeEstagio){
    	
	    Optional<CertificadoDeEstagio> certificadoDeEstagio = certificadoDeEstagioService.buscarCertificadoDeEstagioPorId(idCertificadoDeEstagio);
	    
	    if (certificadoDeEstagio.isEmpty()) {
	    	throw new NotFoundException("Certificado de estágio não encontrado!");
		}
	    
	    certificadoDeEstagioService.deletarCertificadoDeEstagio(certificadoDeEstagio.get());
	    
	    return ResponseEntity.noContent().build();
    }
}
