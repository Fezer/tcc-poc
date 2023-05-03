package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Curso;
import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.repository.CursoRepository;
import br.ufpr.estagio.modulo.repository.CursoSigaRepository;
import br.ufpr.estagio.modulo.repository.OrientadorRepository;
import br.ufpr.estagio.modulo.service.siga.SigaApiDiscentesService;
 
@Service
@Transactional
public class CursoService {
	
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CursoRepository cursoRepo;
	@Autowired
	private OrientadorRepository orientadorRepo;
	@Autowired
	private CursoSigaRepository cursoSigaRepo;
	
	private SigaApiDiscentesService sigaApiDiscentesService;
	
	private OrientadorService orientadorService;
	
    public CursoService(CursoRepository cursoRepo,
    		OrientadorRepository orientadorRepo,
    		SigaApiDiscentesService sigaApiDiscentesService,
    		OrientadorService orientadorService) {
        this.cursoRepo = cursoRepo;
        this.orientadorRepo = orientadorRepo;
        this.sigaApiDiscentesService = sigaApiDiscentesService;
        this.orientadorService = orientadorService;
    }
     
    public List<Curso> listarTodosCursos() {
        return cursoRepo.findAll();
    }
     
    public Curso novoCurso(Curso curso) {
        return cursoRepo.save(curso);
    }
    
    public Curso buscarCursoPorId(long id) {
        return cursoRepo.findById(id).get();
    }
     
    public Curso salvarCurso(Curso curso) {
        return cursoRepo.save(curso);
    }
     
    public Curso atualizarCurso(Curso curso) {
    	return cursoRepo.save(curso);
    }
     
    public void deletarCurso(long id) {
    	cursoRepo.deleteById(id);
    }
    
    public Curso buscarCursoPorIdPrograma(String idPrograma) {
    	return cursoRepo.findByIdPrograma(idPrograma);
    }

	public Curso mapearCursoDiscente(Discente discente) {
		/*Optional<Curso> cursoFind = cursoRepo.findByNome(cursoRepo.findById(discente.getCursoId()));
		Curso curso = new Curso();
		CursoSiga cursoSiga = discente.getCurso();
		if(cursoFind.isEmpty()) {
			curso = mapper.map(cursoSiga, Curso.class);
			curso.setIdPrograma(discente.getIdPrograma());
			curso = this.salvarCurso(curso);
		} else {
			curso = cursoFind.get();
		}
		return curso;*/
		return null;
	}

	public List<Orientador> buscarOrientadoresPorIdPrograma(String idPrograma) {
		Curso cursoFind = cursoRepo.findByIdPrograma(idPrograma);
		List<Orientador> orientadores = new ArrayList<>();
		if(cursoFind != null) {
			List<Orientador> listaOrientador = cursoFind.getOrientador();
			if(listaOrientador != null && listaOrientador.isEmpty()) {
				List<String> docentes = sigaApiDiscentesService.buscarDiscentesPorIdPrograma(idPrograma).getDocentes();
				orientadores = orientadorService.salvarListaDocentes(docentes, cursoFind);
				//this.salvarCurso(cursoFind);
			}
		}
		return orientadores;
	}

}
