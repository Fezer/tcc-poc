package br.ufpr.estagio.modulo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.TermoDeEstagioRepository;
 
@Service
@Transactional
public class TermoDeEstagioService {
	
	@Autowired
	private TermoDeEstagioRepository repo;
	
    public TermoDeEstagioService(TermoDeEstagioRepository repo) {
        this.repo = repo;
    }
     
    public List<TermoDeEstagio> listAll() {
        return repo.findAll();
    }
     
    public TermoDeEstagio novo(TermoDeEstagio termoDeEstagio) {
        return repo.save(termoDeEstagio);
    }
    
    public TermoDeEstagio get(long id) {
        return repo.findById(id).get();
    }
     
    public TermoDeEstagio save(TermoDeEstagio termoDeEstagio) {
        return repo.save(termoDeEstagio);
    }
     
    public TermoDeEstagio update(TermoDeEstagio termoDeEstagio) {
    	return repo.save(termoDeEstagio);
    }
     
    public void delete(long id) {
        repo.deleteById(id);
    }
}