package br.ufpr.estagio.modulo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.CertificadoDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.DescricaoAjustesDTO;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.CertificadoDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/coe")
public class CoeREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;
	
	@Autowired
	private CertificadoDeEstagioService certificadoDeEstagioService;
		
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/termo/pendenteAprovacaoCoe")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosPendenteAprovacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosPendenteAprovacaoCoe();
			if(listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/termo/indeferido")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosIndeferidos(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosIndeferidos();
			if(listaTermos == null || listaTermos.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaTermos.stream().map(e -> mapper.map(e, TermoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/termo/{idTermo}/indeferir")
	public ResponseEntity<TermoDeEstagioDTO> indeferirTermoDeCompromisso(@PathVariable Long idTermo, @RequestBody JustificativaDTO justificativa){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.indeferirTermoDeCompromissoCoe(termo, justificativa);
			TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
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
	
	@PutMapping("/termo/{idTermo}/solicitarAjustes")
	public ResponseEntity<TermoDeEstagioDTO> solicitarAjutesTermoDeCompromisso(@PathVariable Long idTermo, @RequestBody DescricaoAjustesDTO descricaoAjustes){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.solicitarAjutesTermoDeCompromissoCoe(termo, descricaoAjustes);
			TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
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
		
	@PutMapping("/termo/{idTermo}/aprovar")
	public ResponseEntity<TermoDeEstagioDTO> aprovarTermoDeCompromisso(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.aprovarTermoDeCompromissoCoe(termo);
			TermoDeEstagioDTO termoDTO = mapper.map(termo, TermoDeEstagioDTO.class);
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
	
	@GetMapping("/certificado/pendenteAprovacaoCoe")
	public ResponseEntity<List<CertificadoDeEstagioDTO>> listarCertificadosPendenteAprovacao(){
		try {
			List<CertificadoDeEstagio> listaCertificados = certificadoDeEstagioService.listarCertificadosPendentesAprovacaoCoe();
			if(listaCertificados == null || listaCertificados.isEmpty()) {
				return null;
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaCertificados.stream().map(e -> mapper.map(e, CertificadoDeEstagioDTO.class)).collect(Collectors.toList()));
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/certificado/{idCertificado}/aprovar")
	public ResponseEntity<CertificadoDeEstagioDTO> aprovarCertificadoDeEstagio(@PathVariable Long idCertificado){
		try {
			Optional<CertificadoDeEstagio> certificadoOptional = certificadoDeEstagioService.buscarCertificadoDeEstagioPorId(idCertificado);
		if(certificadoOptional.isEmpty()) {
			throw new NotFoundException("Certificado de estágio não encontrado!");
		} else {
			CertificadoDeEstagio certificado = certificadoOptional.get();
			certificado = certificadoDeEstagioService.aprovarCertificadoDeEstagioCoe(certificado);
			CertificadoDeEstagioDTO certificadoDTO = mapper.map(certificado, CertificadoDeEstagioDTO.class);
			return new ResponseEntity<>(certificadoDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/certificado/{idCertificado}/reprovar")
	public ResponseEntity<CertificadoDeEstagioDTO> reprovarCertificadoDeEstagio(@PathVariable Long idCertificado, @RequestBody JustificativaDTO justificativa){
		try {
			Optional<CertificadoDeEstagio> certificadoOptional = certificadoDeEstagioService.buscarCertificadoDeEstagioPorId(idCertificado);
		if(certificadoOptional.isEmpty()) {
			throw new NotFoundException("Certificado de estágio não encontrado!");
		} else {
			CertificadoDeEstagio certificado = certificadoOptional.get();
			certificado = certificadoDeEstagioService.reprovarCertificadoDeEstagioCoe(certificado, justificativa);
			CertificadoDeEstagioDTO certificadoDTO = mapper.map(certificado, CertificadoDeEstagioDTO.class);
			return new ResponseEntity<>(certificadoDTO, HttpStatus.OK);
		}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	/*@GetMapping("/{grrAlunoURL}/visualizar-termo")
	public ResponseEntity<byte[]> visualizarTermoPdf(@PathVariable String grrAlunoURL) throws IOException {
		
		// TO-DO: Jogar dentro de um try-catch
		
		if (grrAlunoURL.isBlank() || grrAlunoURL.isEmpty()) {
			throw new BadRequestException("GRR do aluno não informado!");
		} else {
			Aluno aluno = alunoService.buscarAlunoPorGrr(grrAlunoURL);
			if (aluno == null) {
				throw new NotFoundException("Aluno não encontrado!");
			} else {
				byte[] pdf = geradorService.gerarPdf(aluno);
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDisposition(ContentDisposition.builder("inline").filename("arquivo.pdf").build());
		
				return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
			}
		}

	}*/

}