package br.ufpr.estagio.modulo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.TermoDeEstagioRepository;
 
@Service
@Transactional
public class EstagioService {
	
	@Autowired
	private EstagioRepository estagioRepo;
	
	@Autowired
	private TermoDeEstagioRepository termoDeEstagioRepo;
	
    public EstagioService(EstagioRepository estagioRepo) {
        this.estagioRepo = estagioRepo;
    }
     
    public List<Estagio> listAllEstagios() {
        return estagioRepo.findAll();
    }
     
    public Estagio novoEstagio(Estagio estagio) {
        return estagioRepo.save(estagio);
    }
    
    public Estagio getEstagio(long id) {
        return estagioRepo.findById(id).get();
    }
     
    public Estagio saveEstagio(Estagio estagio) {
        return estagioRepo.save(estagio);
    }
     
    public Estagio updateEstagio(Estagio estagio) {
    	return estagioRepo.save(estagio);
    }
     
    public void deleteEstagio(long id) {
    	estagioRepo.deleteById(id);
    }
}
