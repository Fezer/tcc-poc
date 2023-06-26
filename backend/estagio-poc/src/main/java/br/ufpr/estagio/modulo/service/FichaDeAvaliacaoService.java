package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.FichaDeAvaliacaoDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumParecerAprovadores;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.FichaDeAvaliacao;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.FichaDeAvaliacaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
 
@Service
@Transactional
public class FichaDeAvaliacaoService {
	
    @PersistenceContext
    private EntityManager em;
		
	@Autowired
	private FichaDeAvaliacaoRepository fichaRepo;
	
	@Autowired
	private EstagioRepository estagioRepo;
     
    public List<FichaDeAvaliacao> listarTodosFichasDeAvaliacao() {
        return fichaRepo.findAll();
    }
     
    public FichaDeAvaliacao novoFichaDeAvaliacao(FichaDeAvaliacao relatorioDeEstagio) {
        return fichaRepo.save(relatorioDeEstagio);
    }
    
    public Optional<FichaDeAvaliacao> buscarFichaDeAvaliacaoPorId(long id) {
        return fichaRepo.findById(id);
    }
     
    public FichaDeAvaliacao salvarFichaDeAvaliacao(FichaDeAvaliacao fichaDeAvaliacao) {
        return fichaRepo.save(fichaDeAvaliacao);
    }
     
    public FichaDeAvaliacao atualizarFichaAvaliacao(FichaDeAvaliacao fichaDeAvaliacao, FichaDeAvaliacaoDTO fichaDeAvaliacaoDTO) {
    	
    	fichaDeAvaliacao.setTotalHorasEstagioEfetivamenteRealizadas(fichaDeAvaliacaoDTO.getTotalHorasEstagioEfetivamenteRealizadas());
    	
    	fichaDeAvaliacao.setAcompanhamentoCoordenador(fichaDeAvaliacaoDTO.getAcompanhamentoCoordenador() == null ? 
    			fichaDeAvaliacao.getAcompanhamentoCoordenador() : fichaDeAvaliacaoDTO.getAcompanhamentoCoordenador());
    	
    	fichaDeAvaliacao.setAcompanhamentoCoordenadorComentario(fichaDeAvaliacaoDTO.getAcompanhamentoCoordenadorComentario() == null ? 
    			fichaDeAvaliacao.getAcompanhamentoCoordenadorComentario() : fichaDeAvaliacaoDTO.getAcompanhamentoCoordenadorComentario());
    	
    	fichaDeAvaliacao.setAcompanhamentoOrientador(fichaDeAvaliacaoDTO.getAcompanhamentoOrientador() == null ? 
    			fichaDeAvaliacao.getAcompanhamentoOrientador() : fichaDeAvaliacaoDTO.getAcompanhamentoOrientador());
    	
    	fichaDeAvaliacao.setAcompanhamentoOrientadorComentario(fichaDeAvaliacaoDTO.getAcompanhamentoOrientadorComentario() == null ? 
    			fichaDeAvaliacao.getAcompanhamentoOrientadorComentario() : fichaDeAvaliacaoDTO.getAcompanhamentoOrientadorComentario());
    	
    	fichaDeAvaliacao.setAtividadesForamRealizadas(fichaDeAvaliacaoDTO.isAtividadesForamRealizadas());
    	
    	fichaDeAvaliacao.setAtividadesRealizadasConsideracoes(fichaDeAvaliacaoDTO.getAtividadesRealizadasConsideracoes() == null ? 
    			fichaDeAvaliacao.getAtividadesRealizadasConsideracoes() : fichaDeAvaliacaoDTO.getAtividadesRealizadasConsideracoes());
    	
    	fichaDeAvaliacao.setAvalConduta(fichaDeAvaliacaoDTO.getAvalConduta() == null ? 
    			fichaDeAvaliacao.getAvalConduta() : fichaDeAvaliacaoDTO.getAvalConduta());
    	
    	fichaDeAvaliacao.setAvalCriatividade(fichaDeAvaliacaoDTO.getAvalCriatividade() == null ? 
    			fichaDeAvaliacao.getAvalCriatividade() : fichaDeAvaliacaoDTO.getAvalCriatividade());
    	
    	fichaDeAvaliacao.setAvalDominioTecnico(fichaDeAvaliacaoDTO.getAvalDominioTecnico() == null ? 
    			fichaDeAvaliacao.getAvalDominioTecnico() : fichaDeAvaliacaoDTO.getAvalDominioTecnico());
    	
    	fichaDeAvaliacao.setAvalEfetivacao(fichaDeAvaliacaoDTO.getAvalEfetivacao() == null ? 
    			fichaDeAvaliacao.getAvalEfetivacao() : fichaDeAvaliacaoDTO.getAvalEfetivacao());
    	
    	fichaDeAvaliacao.setAvalHabilidades(fichaDeAvaliacaoDTO.getAvalHabilidades() == null ? 
    			fichaDeAvaliacao.getAvalHabilidades() : fichaDeAvaliacaoDTO.getAvalHabilidades());
    	
    	fichaDeAvaliacao.setAvalPontualidade(fichaDeAvaliacaoDTO.getAvalPontualidade() == null ? 
    			fichaDeAvaliacao.getAvalPontualidade() : fichaDeAvaliacaoDTO.getAvalPontualidade());
    	
    	fichaDeAvaliacao.setAvalProtagonismo(fichaDeAvaliacaoDTO.getAvalProtagonismo() == null ? 
    			fichaDeAvaliacao.getAvalProtagonismo() : fichaDeAvaliacaoDTO.getAvalProtagonismo());
    	
    	fichaDeAvaliacao.setAvalResponsabilidade(fichaDeAvaliacaoDTO.getAvalResponsabilidade() == null ? 
    			fichaDeAvaliacao.getAvalResponsabilidade() : fichaDeAvaliacaoDTO.getAvalResponsabilidade());
    	
    	fichaDeAvaliacao.setContribuicaoEstagio(fichaDeAvaliacaoDTO.getContribuicaoEstagio() == null ? 
    			fichaDeAvaliacao.getContribuicaoEstagio() : fichaDeAvaliacaoDTO.getContribuicaoEstagio());
    	
    	return fichaRepo.save(fichaDeAvaliacao);
    }
     
    public void deletarFicha(long id) {
        fichaRepo.deleteById(id);
    }

	public FichaDeAvaliacao criarFichaDeAvaliacao(Estagio estagio) {
		
		FichaDeAvaliacao fichaDeAvaliacao = new FichaDeAvaliacao();
		fichaDeAvaliacao.setEstagio(estagio);
		
		estagio.setFichaDeAvaliacao(fichaDeAvaliacao);
		
		estagioRepo.save(estagio);
		fichaRepo.save(fichaDeAvaliacao);
		
		return fichaDeAvaliacao;
	}

	public void deletarFichaDeAvaliacao(FichaDeAvaliacao fichaDeAvaliacao) {
		
		Estagio estagio = fichaDeAvaliacao.getEstagio();
		estagio.setFichaDeAvaliacao(null);
		
		fichaDeAvaliacao.setEstagio(null);
		
		estagioRepo.save(estagio);
		fichaRepo.delete(fichaDeAvaliacao);
		
		return;
	}
	
	public FichaDeAvaliacao uploadFichaDeAvaliacao(FichaDeAvaliacao fichaDeAvaliacao, String nomeArquivo) {
		fichaDeAvaliacao.setUpload(true);
		
		List<String> listaAux = new ArrayList<>();
		listaAux.add(nomeArquivo);

		fichaDeAvaliacao.setArquivos(listaAux);
		
		return fichaRepo.save(fichaDeAvaliacao);
	}

}
