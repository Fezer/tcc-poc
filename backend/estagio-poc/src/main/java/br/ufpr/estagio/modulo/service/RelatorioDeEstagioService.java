package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.RelatorioDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumTipoRelatorio;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.RelatorioDeEstagioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
 
@Service
@Transactional
public class RelatorioDeEstagioService {
	
    @PersistenceContext
    private EntityManager em;
		
	@Autowired
	private RelatorioDeEstagioRepository relatorioRepo;
	
	@Autowired
	private EstagioRepository estagioRepo;
	
	private static final String selectPorIdOrientadorFiltroPendenteCiencia = "SELECT r FROM RelatorioDeEstagio r "
			+ "INNER JOIN r.estagio e "
			+ "INNER JOIN e.orientador o "
    		+ "WHERE o.id = :idOrientador "
    		+ "AND r.etapaFluxo = :etapaFluxo "
    		+ "AND r.cienciaOrientador = :cienciaOrientador";
	
	private static final String selectPorIdOrientador = "SELECT r FROM RelatorioDeEstagio r "
			+ "INNER JOIN r.estagio e "
			+ "INNER JOIN e.orientador o "
    		+ "WHERE o.id = :idOrientador";
	
	private static final String selectPorIdAluno = "SELECT r FROM RelatorioDeEstagio r "
			+ "INNER JOIN r.estagio e "
			+ "INNER JOIN e.aluno a "
    		+ "WHERE a.id = :idAluno";
	
	private static final String selectPorIdAlunoStatusCienciaOrientador = "SELECT r FROM RelatorioDeEstagio r "
			+ "INNER JOIN r.estagio e "
			+ "INNER JOIN e.aluno a "
    		+ "WHERE a.id = :idAluno "
    		+ "AND r.cienciaOrientador = :cienciaOrientador";
     
    public List<RelatorioDeEstagio> listarTodosRelatorios() {
        return relatorioRepo.findAll();
    }
     
    public RelatorioDeEstagio novoRelatorio(RelatorioDeEstagio relatorioDeEstagio) {
        return relatorioRepo.save(relatorioDeEstagio);
    }
    
    public Optional<RelatorioDeEstagio> buscarRelatorioPorId(long id) {
        return relatorioRepo.findById(id);
    }
     
    public RelatorioDeEstagio salvarRelatorio(RelatorioDeEstagio relatorioDeEstagio) {
        return relatorioRepo.save(relatorioDeEstagio);
    }
     
    public RelatorioDeEstagio atualizarRelatorioAvaliacao(RelatorioDeEstagio relatorioDeEstagio, RelatorioDeEstagioDTO relatorioDeEstagioDTO) {
    	
    	relatorioDeEstagio.setAvalAtividades(relatorioDeEstagioDTO.getAvalAtividades() == null ? 
    			relatorioDeEstagio.getAvalAtividades() : relatorioDeEstagioDTO.getAvalAtividades());
    	relatorioDeEstagio.setAvalContribuicaoEstagio(relatorioDeEstagioDTO.getAvalContribuicaoEstagio() == null ? 
    			relatorioDeEstagio.getAvalContribuicaoEstagio() : relatorioDeEstagioDTO.getAvalContribuicaoEstagio());
    	relatorioDeEstagio.setAvalDesenvolvimentoAtividades(relatorioDeEstagioDTO.getAvalDesenvolvimentoAtividades() == null ? 
    			relatorioDeEstagio.getAvalDesenvolvimentoAtividades() : relatorioDeEstagioDTO.getAvalDesenvolvimentoAtividades());
    	relatorioDeEstagio.setAvalEfetivacao(relatorioDeEstagioDTO.getAvalEfetivacao() == null ? 
    			relatorioDeEstagio.getAvalEfetivacao() : relatorioDeEstagioDTO.getAvalEfetivacao());
    	relatorioDeEstagio.setAvalFormacaoProfissional(relatorioDeEstagioDTO.getAvalFormacaoProfissional() == null ? 
    			relatorioDeEstagio.getAvalFormacaoProfissional() : relatorioDeEstagioDTO.getAvalFormacaoProfissional());
    	relatorioDeEstagio.setAvalRelacoesInterpessoais(relatorioDeEstagioDTO.getAvalRelacoesInterpessoais() == null ? 
    			relatorioDeEstagio.getAvalRelacoesInterpessoais() : relatorioDeEstagioDTO.getAvalRelacoesInterpessoais());
    	relatorioDeEstagio.setConsideracoes(relatorioDeEstagioDTO.getConsideracoes() == null ? 
    			relatorioDeEstagio.getConsideracoes() : relatorioDeEstagioDTO.getConsideracoes());
    	
    	return relatorioRepo.save(relatorioDeEstagio);
    }
     
    public void deletarRelatorio(long id) {
        relatorioRepo.deleteById(id);
    }

	public RelatorioDeEstagio criarRelatorioDeEstagio(Estagio estagio) {
		RelatorioDeEstagio relatorioEstagio = new RelatorioDeEstagio();
		relatorioEstagio.setEstagio(estagio);
		relatorioEstagio.setCienciaOrientador(false);
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
		relatorioEstagio.setEtapaFluxo(etapaFluxo);
		
		List<RelatorioDeEstagio> listaRelatorios = estagio.getRelatorioDeEstagio();
		
		if (listaRelatorios == null) {
			listaRelatorios = new ArrayList<RelatorioDeEstagio>();
		}
		
		listaRelatorios.add(relatorioEstagio);
		estagio.setRelatorioDeEstagio(listaRelatorios);
		
		estagioRepo.save(estagio);
		relatorioRepo.save(relatorioEstagio);
		
		return relatorioEstagio;
	}

	public void deletarRelatorioDoEstagio(RelatorioDeEstagio relatorioDeEstagio) {
		Estagio estagio = relatorioDeEstagio.getEstagio();

		List<RelatorioDeEstagio> listaRelatorios = estagio.getRelatorioDeEstagio();
		if(listaRelatorios != null) {
			listaRelatorios.remove(relatorioDeEstagio);
			estagio.setRelatorioDeEstagio(listaRelatorios);
		}
		
		relatorioDeEstagio.setEstagio(null);
		estagioRepo.save(estagio);
		relatorioRepo.delete(relatorioDeEstagio);
	}

	public RelatorioDeEstagio definirTipoDeRelatorioDeEstagio(RelatorioDeEstagio relatorioDeEstagio,
			EnumTipoRelatorio tipoRelatorio) {
		relatorioDeEstagio.setTipoRelatorio(tipoRelatorio);
		return relatorioRepo.save(relatorioDeEstagio);
	}

	public RelatorioDeEstagio solicitarCienciaRelatorioDeEstagioAluno(RelatorioDeEstagio relatorioEstagio) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Orientador;
		relatorioEstagio.setEtapaFluxo(etapaFluxo);
		return relatorioRepo.save(relatorioEstagio);
	}

	public List<RelatorioDeEstagio> listarRelatoriosPendenteCienciaPorIdOrientador(long idOrientador) {
		
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Orientador;
		boolean cienciaOrientador = false;
        
        TypedQuery<RelatorioDeEstagio> query = em.createQuery(selectPorIdOrientadorFiltroPendenteCiencia, RelatorioDeEstagio.class);
        
        query.setParameter("idOrientador", idOrientador);
        query.setParameter("etapaFluxo", etapaFluxo);
        query.setParameter("cienciaOrientador", cienciaOrientador);
        
        return query.getResultList();
	}

	public List<RelatorioDeEstagio> listarRelatoriosPorIdOrientador(long idOrientador) {
        
        TypedQuery<RelatorioDeEstagio> query = em.createQuery(selectPorIdOrientador, RelatorioDeEstagio.class);
        
        query.setParameter("idOrientador", idOrientador);
        
        return query.getResultList();
	}

	public RelatorioDeEstagio darCienciaRelatorioDeEstagioOrientador(RelatorioDeEstagio relatorioEstagio) {
		
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
		
		relatorioEstagio.setCienciaOrientador(true);
		relatorioEstagio.setEtapaFluxo(etapaFluxo);
		
		return relatorioRepo.save(relatorioEstagio); 
	}

	public List<RelatorioDeEstagio> listarRelatorioDeEstagioPorAluno(Aluno aluno) {
		
        TypedQuery<RelatorioDeEstagio> query = em.createQuery(selectPorIdAluno, RelatorioDeEstagio.class);
        
        query.setParameter("idAluno", aluno.getId());
        
        return query.getResultList();
	}

	public List<RelatorioDeEstagio> listarRelatoriosDeEstagioPorAlunoPendenteCiencia(Aluno aluno) {
				
        TypedQuery<RelatorioDeEstagio> query = em.createQuery(selectPorIdAlunoStatusCienciaOrientador, RelatorioDeEstagio.class);
        
        query.setParameter("idAluno", aluno.getId());
        query.setParameter("cienciaOrientador", false);
        
        return query.getResultList();
	}
	
	public RelatorioDeEstagio uploadRelatorioDeEstagio(RelatorioDeEstagio relatorio, String nomeArquivo) {
		
		if (relatorio.getTipoRelatorio().equals(EnumTipoRelatorio.RelatorioParcial))
			relatorio.setUploadParcial(true);
		
		else if (relatorio.getTipoRelatorio().equals(EnumTipoRelatorio.RelatorioFinal))
			relatorio.setUploadFinal(true);
		
		List<String> listaAux = new ArrayList<>();
		listaAux.add(nomeArquivo);

		relatorio.setArquivos(listaAux);
		
		return relatorioRepo.save(relatorio);
	}
}
