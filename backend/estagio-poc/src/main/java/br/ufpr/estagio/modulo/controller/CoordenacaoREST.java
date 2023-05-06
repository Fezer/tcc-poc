package br.ufpr.estagio.modulo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.DescricaoAjustesDTO;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/coordenacao")
public class CoordenacaoREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;
		
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/termo/pendenteAprovacaoCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosPendenteAprovacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosPendenteAprovacaoCoordenacao();
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
	
	@GetMapping("/termo/pendenteAprovacaoCoordenacaoFiltro")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosPendenteAprovacaoPorTipoEstagio(@RequestParam String tipoEstagio){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosPendenteAprovacaoCoordenacaoPorTipoEstagio(tipoEstagio);
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
	
	@GetMapping("/termo/pendenteCienciaCoordenacao")
	public ResponseEntity<List<TermoDeEstagioDTO>> listarTermosIndeferidosPendentesCienciaCoordenacao(){
		try {
			List<TermoDeEstagio> listaTermos = termoDeEstagioService.listarTermosIndeferidosPendentesCienciaCoordenacao();
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
			termo = termoDeEstagioService.indeferirTermoDeCompromissoCoordenacao(termo, justificativa);
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
			termo = termoDeEstagioService.solicitarAjutesTermoDeCompromissoCoordenacao(termo, descricaoAjustes);
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
	
	@PutMapping("/termo/{idTermo}/cienciaFormacaoSupervisor")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaFormacaoSupervisor(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.darCienciaFormacaoSupervisorCoordenacao(termo);
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
	
	@PutMapping("/termo/{idTermo}/cienciaPlanoAtividades")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaPlanoAtividades(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.darCienciaPlanoAtividadesCoordenacao(termo);
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
	
	@PutMapping("/termo/{idTermo}/cienciaIra")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaIra(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.darCienciaIraCoordenacao(termo);
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
	
	@PutMapping("/termo/{idTermo}/cienciaIndeferimento")
	public ResponseEntity<TermoDeEstagioDTO> darCienciaTermoIndeferido(@PathVariable Long idTermo){
		try {
			Optional<TermoDeEstagio> termoOptional = Optional.ofNullable(termoDeEstagioService.buscarPorId(idTermo));
		if(termoOptional.isEmpty()) {
			throw new NotFoundException("Termo não encontrado!");
		} else {
			TermoDeEstagio termo = termoOptional.get();
			termo = termoDeEstagioService.darCienciaIndeferimentoCoordenacao(termo);
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
			termo = termoDeEstagioService.aprovarTermoDeCompromissoCoordenacao(termo);
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

}