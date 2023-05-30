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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.estagio.modulo.dto.CursoDTO;
import br.ufpr.estagio.modulo.dto.OrientadorDTO;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Curso;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.service.CursoService;


@CrossOrigin
@RestController
@RequestMapping("/curso")
public class CursoREST {
	
	@Autowired
    private CursoService cursoService;
        
    @Autowired
	private ModelMapper mapper;
    
    
    @GetMapping("/{idPrograma}")
	public ResponseEntity<CursoDTO> buscarCursoPorIdPrograma(@PathVariable String idPrograma) {
    	try {
			if (idPrograma.isBlank() || idPrograma.isEmpty()) {
				throw new BadRequestException("ID do programa do Curso não informado!");
			} else {
				Optional<Curso> curso = Optional.ofNullable(cursoService.buscarCursoPorIdPrograma(idPrograma));
				if (curso == null) {
					throw new NotFoundException("Curso não encontrado!");
				} else {
					CursoDTO cursoDTO = mapper.map(curso, CursoDTO.class);
					return ResponseEntity.status(HttpStatus.OK).body(cursoDTO);
				}
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
    
    @GetMapping("/{idPrograma}/orientadores")
	public ResponseEntity<List<OrientadorDTO>> buscarOrientadoresDoCurso(@PathVariable String idPrograma, @RequestHeader("Authorization") String accessToken) {
    	try {
			if (idPrograma.isBlank() || idPrograma.isEmpty()) {
				throw new BadRequestException("ID do programa do Curso não informado!");
			} else {
				Optional<Curso> curso = Optional.ofNullable(cursoService.buscarCursoPorIdPrograma(idPrograma));
				if (curso == null) {
					throw new NotFoundException("Curso não encontrado!");
				} else {
					List<Orientador> listaOrientadores = cursoService.buscarOrientadoresPorIdPrograma(idPrograma, accessToken);
					if (listaOrientadores == null) {
						throw new NotFoundException("Orientadores não encontrados!");
					} else {
						return ResponseEntity.status(HttpStatus.OK).body(listaOrientadores.stream().map(e -> mapper.map(e, OrientadorDTO.class)).collect(Collectors.toList()));
					}
				}
			}
		} catch (PocException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}

	@GetMapping("")
	public ResponseEntity<List<CursoDTO>> listarCursos() {
		try {
		    List<Curso> listaCursos = cursoService.listarTodosCursos();
			if(listaCursos.isEmpty()) {
				throw new NotFoundException("Nenhum curso encontrado!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(listaCursos.stream().map(e -> mapper.map(e, CursoDTO.class)).collect(Collectors.toList()));
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
