package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
 
@Service
@Transactional
public class EstagioService {
	
	private static final String selectEstagiosPorIdOrientador = "SELECT e FROM Estagio e INNER JOIN e.orientador o "
    		+ "WHERE o.id = :idOrientador";
	private static final String selectPorIdOrientadorFiltroStatusEstagio = "SELECT e FROM Estagio e INNER JOIN e.orientador o "
    		+ "WHERE o.id = :idOrientador "
    		+ "and e.statusEstagio = :statusEstagio"; 

	@Autowired
	private EstagioRepository estagioRepo;
	
    @PersistenceContext
    private EntityManager em;
	     
    public List<Estagio> listarTodosEstagios() {
        return estagioRepo.findAll();
    }
     
    public Estagio novoEstagio(Estagio estagio) {
    	EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmPreenchimento;
    	estagio.setStatusEstagio(statusEstagio);
        return estagioRepo.save(estagio);
    }
    
    public Optional<Estagio> buscarEstagioPorId(long id) {
        return estagioRepo.findById(id);
    }
     
    public Estagio salvarEstagio(Estagio estagio) {
        return estagioRepo.save(estagio);
    }
     
    public Estagio atualizarEstagio(Estagio estagio) {
    	return estagioRepo.save(estagio);
    }
     
    public void deletarEstagio(long id) {
    	estagioRepo.deleteById(id);
    }

	public EstagioDTO toEstagioDTO(Estagio estagio) {
		EstagioDTO estagioDTO = new EstagioDTO();
		estagioDTO.setId(estagio.getId());
		estagioDTO.setTipoEstagio(estagio.getTipoEstagio());
		estagioDTO.setStatusEstagio(estagio.getStatusEstagio());
		estagioDTO.setEstagioUfpr(estagio.isEstagioUfpr());
		estagioDTO.setEstagioSeed(estagio.isEstagioSeed());
		estagioDTO.setAluno(estagio.getAluno());
		estagioDTO.setContratante(estagio.getContratante());
		estagioDTO.setSeguradora(estagio.getSeguradora());
		estagioDTO.setApolice(estagio.getApolice());
		estagioDTO.setAgenteIntegrador(estagio.getAgenteIntegrador());
		estagioDTO.setOrientador(estagio.getOrientador());
		estagioDTO.setPlanoDeAtividades(estagio.getPlanoDeAtividades());
		estagioDTO.setDataInicio(estagio.getDataInicio());
		estagioDTO.setDataTermino(estagio.getDataTermino());
		estagioDTO.setJornadaDiaria(estagio.getJornadaDiaria());
		estagioDTO.setJornadaSemanal(estagio.getJornadaSemanal());
		estagioDTO.setValorBolsa(estagio.getValorBolsa());
		estagioDTO.setValorTransporte(estagio.getValorTransporte());
		estagioDTO.setTermoDeCompromisso(estagio.getTermoDeCompromisso() == null ? 0 : estagio.getTermoDeCompromisso().getId());
		List<Long> termoAditivo = new ArrayList<Long>();
		if(estagio.getTermoAdivito() != null) {
			for (TermoDeEstagio t : estagio.getTermoAdivito()) {
				termoAditivo.add(t.getId());
			}
		} else {
			termoAditivo.add((long) 0);
		}
		estagioDTO.setTermoAdivito(termoAditivo);
		estagioDTO.setTermoDeRescisao(estagio.getTermoDeRescisao() == null ? 0 : estagio.getTermoDeRescisao().getId());
		List<Long> relatorioDeEstagio = new ArrayList<Long>();
		if(estagio.getRelatorioDeEstagio() != null) {
			for (RelatorioDeEstagio r : estagio.getRelatorioDeEstagio()) {
				relatorioDeEstagio.add(r.getId());
			}
		} else {
			relatorioDeEstagio.add((long) 0);
		}
		estagioDTO.setRelatorioDeEstagio(relatorioDeEstagio);
		estagioDTO.setFichaDeAvaliacao(estagio.getFichaDeAvaliacao() == null ? 0 : estagio.getFichaDeAvaliacao().getId());
		estagioDTO.setCertificadoDeEstagio(estagio.getCertificadoDeEstagio() == null ? 0 : estagio.getCertificadoDeEstagio().getId());
		estagioDTO.setDataCriacao(estagio.getDataCriacao());
		return estagioDTO;
	}

	public Estagio definirTipoEstagio(Estagio estagio, EnumTipoEstagio tipoEstagio) {
		estagio.setTipoEstagio(tipoEstagio);
		return estagioRepo.save(estagio);		
	}

	public Estagio definirEstagioUfpr(Estagio estagio, Boolean estagioUfpr) {
		estagio.setEstagioUfpr(estagioUfpr);
		return estagioRepo.save(estagio);
	}
	
	public Estagio definirEstagioSeed(Estagio estagio, Boolean estagioSeed) {
		estagio.setEstagioSeed(estagioSeed);
		return estagioRepo.save(estagio);
	}

	public List<Estagio> buscarEstagioEmPreenchimentoPorAluno(Aluno aluno) {
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmPreenchimento;
		List<Estagio> estagio = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);
		return estagio;
	}

	//Neste caso, estágio em progresso é um estágio já aprovado, mas ainda não iniciado ou um estágio já aprovado e já iniciado ou seja, um estágio em andamento.
	public List<Estagio> buscarEstagioEmProgressoPorAluno(Aluno aluno) {
		//Primeiro busca por estágio aprovado.
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.Aprovado;
		List<Estagio> estagioAprovado = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);
		
		//Depois busca por estágio iniciado.
		statusEstagio = EnumStatusEstagio.Iniciado;
		List<Estagio> estagioIniciado = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);
		
		//Por fim, concatena as duas listas em uma única lista.
		List<Estagio> estagio = new ArrayList<>();
		estagio.addAll(estagioAprovado);
		estagio.addAll(estagioIniciado);
		return estagio;
	}

	public List<Estagio> buscarEstagioEmAprovacaoPorAluno(Aluno aluno) {
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmAprovacao;
		List<Estagio> estagio = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);
		return estagio;
	}

	public List<Estagio> buscarEstagioPorStatusEstagio(Aluno aluno, String statusEstagioString) {
		EnumStatusEstagio statusEstagio;
		statusEstagioString = statusEstagioString.toUpperCase();
		switch(statusEstagioString) {
			case "EMPREENCHIMENTO":
				statusEstagio = EnumStatusEstagio.EmPreenchimento;
				break;
			case "EMAPROVACAO":
				statusEstagio = EnumStatusEstagio.EmAprovacao;
				break;
			case "APROVADO":
				statusEstagio = EnumStatusEstagio.Aprovado;
				break;
			case "INICIADO":
				statusEstagio = EnumStatusEstagio.Iniciado;
				break;
			case "CONCLUIDO":
				statusEstagio = EnumStatusEstagio.Concluido;
				break;
			case "CANCELADO":
				statusEstagio = EnumStatusEstagio.Cancelado;
				break;
			case "RESCINDIDO":
				statusEstagio = EnumStatusEstagio.Rescindido;
				break;
			case "REPROVADO":
				statusEstagio = EnumStatusEstagio.Reprovado;
				break;
			default:
				return null;
		}
		List<Estagio> estagio = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);
		return estagio;
	}

	public List<Estagio> listarTodosEstagiosPendenteAprovacaoCoe() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Estagio> listarEstagiosPorIdOrientador(long idOrientador) {
				        
        TypedQuery<Estagio> query = em.createQuery(selectEstagiosPorIdOrientador, Estagio.class);
        
        query.setParameter("idOrientador", idOrientador);
        
        return query.getResultList();
	}

	public List<Estagio> listarEstagiosPendenteAprovacaoPorIdOrientador(long idOrientador) {
		
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmAprovacao;
        
        TypedQuery<Estagio> query = em.createQuery(selectPorIdOrientadorFiltroStatusEstagio, Estagio.class);
        
        query.setParameter("idOrientador", idOrientador);
        query.setParameter("statusEstagio", statusEstagio);
        
        return query.getResultList();
	}

	public List<Estagio> listarEstagiosIndeferidosPorIdOrientador(long idOrientador) {
		
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.Reprovado;
        
        TypedQuery<Estagio> query = em.createQuery(selectPorIdOrientadorFiltroStatusEstagio, Estagio.class);
        
        query.setParameter("idOrientador", idOrientador);
        query.setParameter("statusEstagio", statusEstagio);
        
        return query.getResultList();
	}

}
