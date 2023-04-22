package br.ufpr.estagio.modulo.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.PlanoDeAtividadesDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.PlanoDeAtividadesRepository;
import br.ufpr.estagio.modulo.repository.TermoDeEstagioRepository;
 
@Service
@Transactional
public class TermoDeEstagioService {
	
	@Autowired
	private TermoDeEstagioRepository termoRepo;
	private PlanoDeAtividadesRepository planoRepo;
	
    public TermoDeEstagioService(TermoDeEstagioRepository termoRepo,
    		PlanoDeAtividadesRepository planoRepo) {
        this.termoRepo = termoRepo;
        this.planoRepo = planoRepo;
    }
     
    public List<TermoDeEstagio> listarTodos() {
        return termoRepo.findAll();
    }
     
    public TermoDeEstagio novo(TermoDeEstagio termoDeEstagio) {
    	EnumStatusTermo statusTermo = EnumStatusTermo.EmPreenchimento;
    	EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
    	termoDeEstagio.setStatusTermo(statusTermo);
    	termoDeEstagio.setEtapaFluxo(etapaFluxo);
        return termoRepo.save(termoDeEstagio);
    }
    
    public TermoDeEstagio buscarPorId(long id) {
        return termoRepo.findById(id).get();
    }
     
    public TermoDeEstagio salvar(TermoDeEstagio termoDeEstagio) {
        return termoRepo.save(termoDeEstagio);
    }
     
    public TermoDeEstagio atualizar(TermoDeEstagio termoDeEstagio) {
    	return termoRepo.save(termoDeEstagio);
    }
     
    public void deletar(long id) {
    	termoRepo.deleteById(id);
    }

	public TermoDeEstagio atualizarDados(Optional<TermoDeEstagio> termofind, TermoDeEstagioDTO termo) {
		TermoDeEstagio termoAtualizado = termofind.get();		
		termoAtualizado.setDataInicio(termo.getDataInicio() == null ? termoAtualizado.getDataInicio() : termo.getDataInicio());
		termoAtualizado.setDataTermino(termo.getDataTermino() == null ? termoAtualizado.getDataTermino() : termo.getDataTermino());
		termoAtualizado.setJornadaDiaria(termo.getJornadaDiaria() == 0 ? termoAtualizado.getJornadaDiaria() : termo.getJornadaDiaria());
		termoAtualizado.setJornadaSemanal(termo.getJornadaSemanal() == 0 ? termoAtualizado.getJornadaSemanal() : termo.getJornadaSemanal());
		termoAtualizado.setValorBolsa(termo.getValorBolsa() == 0 ? termoAtualizado.getValorBolsa() : termo.getValorBolsa());
		termoAtualizado.setValorTransporte(termo.getValorTransporte() == 0 ? termoAtualizado.getValorTransporte() : termo.getValorTransporte());
		termoAtualizado.setDataFimSuspensao(termo.getDataFimSuspensao() == null ? termoAtualizado.getDataFimSuspensao() : termo.getDataFimSuspensao());
		termoAtualizado.setDataInicioRetomada(termo.getDataInicioRetomada() == null ? termoAtualizado.getDataInicioRetomada() : termo.getDataInicioRetomada());
		return termoRepo.save(termoAtualizado);
	}

	public TermoDeEstagio atualizarPlanoAtividades(Optional<TermoDeEstagio> termofind,
			PlanoDeAtividadesDTO planoAtividades) {
		TermoDeEstagio termoAtualizado = termofind.get();
		PlanoDeAtividades planoAtividadesAtualizado = termoAtualizado.getPlanoAtividades();
		planoAtividadesAtualizado.setLocal(planoAtividades.getLocal() == "" ? planoAtividadesAtualizado.getLocal() : planoAtividades.getLocal());
		planoAtividadesAtualizado.setDescricaoAtividades(planoAtividades.getDescricaoAtividades() == "" ? planoAtividadesAtualizado.getDescricaoAtividades() : planoAtividades.getDescricaoAtividades());
		planoRepo.save(planoAtividadesAtualizado);
		return termoRepo.save(termoAtualizado);
	}
}
