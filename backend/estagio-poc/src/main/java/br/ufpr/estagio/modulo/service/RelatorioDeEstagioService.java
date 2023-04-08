package br.ufpr.estagio.modulo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.repository.RelatorioDeEstagioRepository;
 
@Service
@Transactional
public class RelatorioDeEstagioService {
	
	@Autowired
	private RelatorioDeEstagioRepository repo;
	
    public RelatorioDeEstagioService(RelatorioDeEstagioRepository repo) {
        this.repo = repo;
    }
     
    public List<RelatorioDeEstagio> listAll() {
        return repo.findAll();
    }
     
    public RelatorioDeEstagio novo(RelatorioDeEstagio relatorioDeEstagio) {
        return repo.save(relatorioDeEstagio);
    }
    
    public RelatorioDeEstagio get(long id) {
        return repo.findById(id).get();
    }
     
    public RelatorioDeEstagio save(RelatorioDeEstagio relatorioDeEstagio) {
        return repo.save(relatorioDeEstagio);
    }
     
    public RelatorioDeEstagio update(RelatorioDeEstagio relatorioDeEstagio) {
    	return repo.save(relatorioDeEstagio);
    }
     
    public void delete(long id) {
        repo.deleteById(id);
    }
}
