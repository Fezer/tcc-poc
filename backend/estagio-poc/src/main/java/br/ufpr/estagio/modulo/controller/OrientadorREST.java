package br.ufpr.estagio.modulo.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.dto.RelatorioDeEstagioDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.OrientadorService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/orientador")
public class OrientadorREST {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EstagioService estagioService;
	
	@Autowired
	private OrientadorService orientadorService;
	
	@Autowired
	private RelatorioDeEstagioService relatorioDeEstagioService;
		
	@GetMapping("/{idOrientador}/estagio")
	public ResponseEntity<List<EstagioDTO>> listarEstagiosDeOrientandos(@PathVariable Long idOrientador){
		try {
			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idOrientador);
			if(orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<Estagio> listaEstagios = estagioService.listarEstagiosPorIdOrientador(orientador.getId());
				List<EstagioDTO> listaDTO = new ArrayList<EstagioDTO>();
				for(Estagio l : listaEstagios) {
					listaDTO.add(estagioService.toEstagioDTO(l));
				}
				return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{idOrientador}/estagio/pendenteAprovacao")
	public ResponseEntity<List<EstagioDTO>> listarEstagiosDeOrientandosPendenteAprovacao(@PathVariable Long idOrientador){
		try {
			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idOrientador);
			if(orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<Estagio> listaEstagios = estagioService.listarEstagiosPendenteAprovacaoPorIdOrientador(orientador.getId());
				List<EstagioDTO> listaDTO = new ArrayList<EstagioDTO>();
				for(Estagio l : listaEstagios) {
					listaDTO.add(estagioService.toEstagioDTO(l));
				}
				return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{idOrientador}/estagio/indeferido")
	public ResponseEntity<List<EstagioDTO>> listarEstagiosDeOrientandosIndeferido(@PathVariable Long idOrientador){
		try {
			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idOrientador);
			if(orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<Estagio> listaEstagios = estagioService.listarEstagiosIndeferidosPorIdOrientador(orientador.getId());
				List<EstagioDTO> listaDTO = new ArrayList<EstagioDTO>();
				for(Estagio l : listaEstagios) {
					listaDTO.add(estagioService.toEstagioDTO(l));
				}
				return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{idOrientador}/relatorioDeEstagio/")
	public ResponseEntity<List<RelatorioDeEstagioDTO>> listarRelatoriosDeEstagioDeOrientandos(@PathVariable Long idOrientador){
		try {
			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idOrientador);
			if(orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<RelatorioDeEstagio> listaRelatorios = relatorioDeEstagioService.listarRelatoriosPorIdOrientador(orientador.getId());
			    List<RelatorioDeEstagioDTO> listaRelatoriosDTO = listaRelatorios.stream()
			            .map(ap -> mapper.map(ap, RelatorioDeEstagioDTO.class))
			            .collect(Collectors.toList());
			    return ResponseEntity.ok().body(listaRelatoriosDTO);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/{idOrientador}/relatorioDeEstagio/pendenteCiencia")
	public ResponseEntity<List<RelatorioDeEstagioDTO>> listarRelatoriosDeEstagioDeOrientandosPendenteCiencia(@PathVariable Long idOrientador){
		try {
			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idOrientador);
			if(orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			} else {
				Orientador orientador = orientadorFind.get();
				List<RelatorioDeEstagio> listaRelatorios = relatorioDeEstagioService.listarRelatoriosPendenteCienciaPorIdOrientador(orientador.getId());
			    List<RelatorioDeEstagioDTO> listaRelatoriosDTO = listaRelatorios.stream()
			            .map(ap -> mapper.map(ap, RelatorioDeEstagioDTO.class))
			            .collect(Collectors.toList());
			    return ResponseEntity.ok().body(listaRelatoriosDTO);
			}
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@PutMapping("/{idOrientador}/relatorioDeEstagio/{idRelatorio}/darCiencia")
	public ResponseEntity<RelatorioDeEstagioDTO> darCienciaRelatorioDeEstagio(@PathVariable Long idOrientador, @PathVariable Long idRelatorio){
		try {
			Optional<Orientador> orientadorFind = orientadorService.buscarOrientadorPorId(idOrientador);
			if(orientadorFind.isEmpty()) {
				throw new NotFoundException("Orientador não encontrado!");
			}
			Optional<RelatorioDeEstagio> relatorioFind = relatorioDeEstagioService.buscarRelatorioPorId(idRelatorio);
			if(relatorioFind.isEmpty()) {
				throw new NotFoundException("Relatório não encontrado!");
			} else {
				RelatorioDeEstagio relatorio = relatorioFind.get();
				relatorio = relatorioDeEstagioService.darCienciaRelatorioDeEstagioOrientador(relatorio);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(relatorio, RelatorioDeEstagioDTO.class));
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