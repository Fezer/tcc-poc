package br.ufpr.estagio.modulo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.repository.CursoSigaRepository;
 
@Service
@Transactional
public class CursoSigaService {

	@Autowired
	private CursoSigaRepository cursoSigaRepo;
	
    public CursoSigaService(CursoSigaRepository cursoSigaRepo) {
        this.cursoSigaRepo = cursoSigaRepo;
    }
     
    public List<CursoSiga> listarTodosCursoSigas() {
        return cursoSigaRepo.findAll();
    }
     
    public CursoSiga novoCursoSiga(CursoSiga cursoSiga) {
        return cursoSigaRepo.save(cursoSiga);
    }
    
    public CursoSiga buscarCursoSigaPorId(long id) {
        return cursoSigaRepo.findById(id).get();
    }
     
    public CursoSiga salvarCursoSiga(CursoSiga cursoSiga) {
        return cursoSigaRepo.save(cursoSiga);
    }
     
    public CursoSiga atualizarCursoSiga(CursoSiga cursoSiga) {
    	return cursoSigaRepo.save(cursoSiga);
    }
     
    public void deletarCursoSiga(long id) {
    	cursoSigaRepo.deleteById(id);
    }

}
