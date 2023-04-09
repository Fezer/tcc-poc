package br.ufpr.estagio.modulo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
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
     
    public List<TermoDeEstagio> listarTodos() {
        return repo.findAll();
    }
     
    public TermoDeEstagio novo(TermoDeEstagio termoDeEstagio) {
    	EnumStatusTermo statusTermo = EnumStatusTermo.EmPreenchimento;
    	EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
    	termoDeEstagio.setStatusTermo(statusTermo);
    	termoDeEstagio.setEtapaFluxo(etapaFluxo);
        return repo.save(termoDeEstagio);
    }
    
    public TermoDeEstagio buscarPorId(long id) {
        return repo.findById(id).get();
    }
     
    public TermoDeEstagio salvar(TermoDeEstagio termoDeEstagio) {
        return repo.save(termoDeEstagio);
    }
     
    public TermoDeEstagio atualizar(TermoDeEstagio termoDeEstagio) {
    	return repo.save(termoDeEstagio);
    }
     
    public void deletar(long id) {
        repo.deleteById(id);
    }
}
