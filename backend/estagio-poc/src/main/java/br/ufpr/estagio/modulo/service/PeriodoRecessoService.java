package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.PeriodoRecessoDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.model.PeriodoRecesso;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.repository.PeriodoRecessoRepository;
import br.ufpr.estagio.modulo.repository.TermoDeRescisaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
 
@Service
@Transactional
public class PeriodoRecessoService {
			
	
	@Autowired
	private PeriodoRecessoRepository periodoRecessoRepo;
	
	@Autowired
	private TermoDeRescisaoRepository termoDeRescisaoRepo;
	
    @PersistenceContext
    private EntityManager em;
	     
    public List<PeriodoRecesso> listarTodos() {
        return periodoRecessoRepo.findAll();
    }
     
    public PeriodoRecesso novo(PeriodoRecesso periodoRecesso) {
        return periodoRecessoRepo.save(periodoRecesso);
    }
    
    public Optional<PeriodoRecesso> buscarPorId(long id) {
        return periodoRecessoRepo.findById(id);
    }
     
    public PeriodoRecesso salvar(PeriodoRecesso periodoRecesso) {
        return periodoRecessoRepo.save(periodoRecesso);
    }
     
    public PeriodoRecesso atualizar(PeriodoRecesso periodoRecesso) {
    	return periodoRecessoRepo.save(periodoRecesso);
    }
     
    public void deletar(long id) {
    	periodoRecessoRepo.deleteById(id);
    }
    
    public PeriodoRecesso novoPeriodoRecesso(TermoDeRescisao termoDeRescisao) {
    	/**
    	 * Somente é possível adicionar um periodo de recesso caso o termo de rescisão
    	 * em questão esteja na etapa de preenchimento pelo aluno.
    	 */
	    if(termoDeRescisao.getEtapaFluxo() != EnumEtapaFluxo.Aluno) {
	    	return null;
	    }
	    if(termoDeRescisao.isCienciaCOAFE()) {
	    	return null;
	    }
    	PeriodoRecesso periodoRecesso = new PeriodoRecesso();
    	periodoRecesso.setTermoRescisao(termoDeRescisao);
    	List<PeriodoRecesso> listaPeriodoRecesso = termoDeRescisao.getPeriodoRecesso();
    	if(listaPeriodoRecesso == null) {
    		listaPeriodoRecesso = new ArrayList<PeriodoRecesso>();
    	}
    	listaPeriodoRecesso.add(periodoRecesso);
    	termoDeRescisao.setPeriodoRecesso(listaPeriodoRecesso);
    	termoDeRescisaoRepo.save(termoDeRescisao);    	
        return periodoRecessoRepo.save(periodoRecesso);
    }
    
    public void deletarPeriodoRecesso(PeriodoRecesso periodoRecesso) {
    	/**
    	 * Somente é possível deletar um periodo de recesso caso o termo de rescisão
    	 * associado ao periodo de recesso em questão esteja na etapa de preenchimento
    	 * pelo aluno.
    	 */
    	if (periodoRecesso.getTermoRescisao() != null) {
	    	TermoDeRescisao termoDeRescisao = periodoRecesso.getTermoRescisao();
	    	if(termoDeRescisao.getEtapaFluxo() != EnumEtapaFluxo.Aluno) {
	    		return;
	    	}
	    	if(termoDeRescisao.isCienciaCOAFE()) {
	    		return;
	    	}
	    	List<PeriodoRecesso> listaPeriodoRecesso = termoDeRescisao.getPeriodoRecesso();
	    	if(listaPeriodoRecesso != null) {
	    		listaPeriodoRecesso.remove(periodoRecesso);
	    	}
	    	termoDeRescisao.setPeriodoRecesso(listaPeriodoRecesso);
	    	termoDeRescisaoRepo.save(termoDeRescisao);
    	}	
	    periodoRecesso.setTermoRescisao(null);
	    periodoRecessoRepo.delete(periodoRecesso);
	    return;
    	
    }

	public PeriodoRecesso atualizarDados(PeriodoRecesso periodoFind, PeriodoRecessoDTO periodoDeRecesso) {
    	/**
    	 * Somente é possível atualizar um periodo de recesso caso o termo de rescisão
    	 * associado ao periodo de recesso em questão esteja na etapa de preenchimento
    	 * pelo aluno.
    	 */
    	if (periodoFind.getTermoRescisao() != null) {
	    	TermoDeRescisao termoDeRescisao = periodoFind.getTermoRescisao();
	    	if(termoDeRescisao.getEtapaFluxo() != EnumEtapaFluxo.Aluno) {
	    		return periodoFind;
	    	}
	    	if(termoDeRescisao.isCienciaCOAFE()) {
	    		return periodoFind;
	    	}
    	}
		PeriodoRecesso periodoAtualizado = periodoFind;		
		periodoAtualizado.setDataFim(periodoDeRecesso.getDataFim() == null ? periodoAtualizado.getDataFim() : periodoDeRecesso.getDataFim());
		periodoAtualizado.setDataInicio(periodoDeRecesso.getDataInicio() == null ? periodoAtualizado.getDataInicio() : periodoDeRecesso.getDataInicio());
		return periodoRecessoRepo.save(periodoAtualizado);
	}
	
}
