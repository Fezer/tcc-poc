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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.DescricaoAjustesDTO;
import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.OrientadorService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;

@CrossOrigin
@RestController
@RequestMapping("/orientador")
public class OrientadorREST {

	@Autowired
	private TermoDeEstagioService termoDeEstagioService;
	
	@Autowired
	private EstagioService estagioService;
	
	@Autowired
	private OrientadorService orientadorService;
		
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/{idOrientador}/estagio")
	public ResponseEntity<List<EstagioDTO>> listarEstagiosDeOrientandos(@PathVariable Long idOrientador){
		try {
			Orientador orientador = orientadorService.buscarOrientadorPorId(idOrientador);
			if(orientador == null) {
				return null;
			} else {
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
			Orientador orientador = orientadorService.buscarOrientadorPorId(idOrientador);
			if(orientador == null) {
				return null;
			} else {
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

}