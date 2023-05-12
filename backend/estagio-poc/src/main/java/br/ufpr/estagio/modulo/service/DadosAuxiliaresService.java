package br.ufpr.estagio.modulo.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.DadosAuxiliares;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.repository.DadosAuxiliaresRepository;

@Service
@Transactional
public class DadosAuxiliaresService {
	
	@Autowired
	private DadosAuxiliaresRepository dadosRepo;
	
	public DadosAuxiliares atualizarDados(DadosAuxiliares dadosAtualizado) {
    	
    	DadosAuxiliares dadosExistente = buscarDadosAuxiliaresId(dadosAtualizado.getId())
    			.orElseThrow(() -> new NoSuchElementException("Dados não encontrados para o ID informado"));
    	
    	dadosExistente.setEstadoCivil(dadosAtualizado.getEstadoCivil());
    	dadosExistente.setDependentes(dadosAtualizado.getDependentes());
    	dadosExistente.setGrupoSanguineo(dadosAtualizado.getGrupoSanguineo());
    	dadosExistente.setDataDeChegadaNoPais(dadosAtualizado.getDataDeChegadaNoPais());
    	dadosExistente.setDataExpedicao(dadosAtualizado.getDataExpedicao());
    	dadosExistente.setTituloEleitoral(dadosAtualizado.getTituloEleitoral());
    	dadosExistente.setZona(dadosAtualizado.getZona());
    	dadosExistente.setSecao(dadosAtualizado.getSecao());
    	dadosExistente.setCertificadoMilitar(dadosAtualizado.getCertificadoMilitar());
    	dadosExistente.setOrgaoDeExpedicao(dadosAtualizado.getOrgaoDeExpedicao());
    	dadosExistente.setSerie(dadosAtualizado.getSerie());
    	dadosExistente.setEmailInstitucional(dadosAtualizado.getEmailInstitucional());
    	
    	dadosExistente.setAluno(dadosAtualizado.getAluno());
    	//aluno.save?
    	
    	return dadosRepo.save(dadosExistente);
    }

	public Optional<DadosAuxiliares> buscarDadosAuxiliaresId(long id) {
		return dadosRepo.findById(id);
	}
	
	public DadosAuxiliares buscarDadosAuxiliaresPorId(long id) {
		Optional<DadosAuxiliares> dadosFind = dadosRepo.findById(id);
		DadosAuxiliares dados = new DadosAuxiliares();

    	dados = dadosFind.get();
    	
        return dados;
	}
}
