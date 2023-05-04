package br.ufpr.estagio.modulo.controller;

import java.net.URI;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.exception.BadRequestException;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.exception.PocException;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.model.CursoSigaData;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.DocentesData;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoPoc;
import br.ufpr.estagio.modulo.repository.TermoPocRepository;
import br.ufpr.estagio.modulo.service.AlunoService;
import br.ufpr.estagio.modulo.service.EstagioService;
import br.ufpr.estagio.modulo.service.RelatorioDeEstagioService;
import br.ufpr.estagio.modulo.service.TermoDeEstagioService;
import br.ufpr.estagio.modulo.service.siga.SigaApiAlunoService;
import br.ufpr.estagio.modulo.service.siga.SigaApiCursoSigaService;
import br.ufpr.estagio.modulo.service.siga.SigaApiDiscentesService;
import br.ufpr.estagio.modulo.wrapper.DiscenteWrapper;

@CrossOrigin
@RestController
@RequestMapping("/siga")
public class SigaREST {
	
    private SigaApiAlunoService sigaApiAlunoService;
    private SigaApiDiscentesService sigaApiDiscentesService;
    private SigaApiCursoSigaService sigaApiCursoSigaService;
    
    public SigaREST(SigaApiAlunoService sigaApiAlunoService,
    		SigaApiDiscentesService sigaApiDiscentesService,
    		SigaApiCursoSigaService sigaApiCursoSigaService) {
        this.sigaApiAlunoService = sigaApiAlunoService;
        this.sigaApiDiscentesService = sigaApiDiscentesService;
        this.sigaApiCursoSigaService = sigaApiCursoSigaService;
    }
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/aluno")
	public ResponseEntity<Discente> listarAluno(@RequestParam String grr){
		try {
			if(grr.isBlank() || grr.isEmpty()) {
				throw new BadRequestException("GRR do aluno não informado!");
			} else {
				Discente discente = sigaApiAlunoService.buscarAlunoPorGrr(grr);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(discente, Discente.class));
			}
		}catch (NumberFormatException e) {
			throw new BadRequestException("O GRR informado para o aluno não é do tipo de dado esperado!");
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
		
	@GetMapping("/docentes")
	public ResponseEntity<DocentesData> listarDocentes(@RequestParam String idPrograma){
		try {
			if(idPrograma.isBlank() || idPrograma.isEmpty()) {
				throw new BadRequestException("ID do programa não informado!");
			} else {
				DocentesData docentesData = sigaApiDiscentesService.buscarDiscentesPorIdPrograma(idPrograma);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(docentesData, DocentesData.class));
			}
		}catch (NumberFormatException e) {
			throw new BadRequestException("O ID informado para o programa não é do tipo de dado esperado!");
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
	
	@GetMapping("/cursos")
	public ResponseEntity<CursoSiga> listarCursos(@RequestParam Long idCurso){
		try {
			if(idCurso < 1) {
				throw new BadRequestException("ID do curso não informado!");
			} else {
				CursoSiga cursoSiga = sigaApiCursoSigaService.buscarCursoSigaPorIdCurso(idCurso);
				if(cursoSiga == null) {
					throw new NotFoundException("Curso não encontrado!");
				}
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(cursoSiga, CursoSiga.class));
			}
		}catch (NumberFormatException e) {
			throw new BadRequestException("O ID informado para o curso não é do tipo de dado esperado!");
		}catch(PocException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PocException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro!");
		}
	}
}