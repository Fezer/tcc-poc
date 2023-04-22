package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Curso;
import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.repository.CursoRepository;
import br.ufpr.estagio.modulo.repository.CursoSigaRepository;
 
@Service
@Transactional
public class CursoService {
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CursoRepository cursoRepo;
	
	@Autowired
	private CursoSigaRepository cursoSigaRepo;
	
    public CursoService(CursoRepository cursoRepo) {
        this.cursoRepo = cursoRepo;
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

	public Curso mapearCursoDiscente(Discente discente) {
		Optional<Curso> cursoFind = cursoRepo.findByNome(discente.getCurso().getNome());
		Curso curso = new Curso();
		CursoSiga cursoSiga = discente.getCurso();
		if(cursoFind.isEmpty()) {
			curso = mapper.map(cursoSiga, Curso.class);
			curso.setIdPrograma(discente.getIdPrograma());
			curso = this.salvarCurso(curso);
		} else {
			curso = cursoFind.get();
		}
		return curso;
	}

}
