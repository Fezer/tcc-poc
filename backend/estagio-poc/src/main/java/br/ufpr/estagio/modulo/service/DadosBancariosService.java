package br.ufpr.estagio.modulo.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.DadosBancarios;
import br.ufpr.estagio.modulo.repository.DadosBancariosRepository;

@Service
@Transactional
public class DadosBancariosService {
	
	@Autowired
	private DadosBancariosRepository dadosRepo;
	
	public DadosBancarios atualizarDados(DadosBancarios dadosAtualizado) {
	    	
		DadosBancarios dadosExistente = buscarDadosBancariosId(dadosAtualizado.getId())
	    			.orElseThrow(() -> new NoSuchElementException("Dados não encontrados para o ID informado"));
    	
    	dadosExistente.setBairroDaAgencia(dadosAtualizado.getBairroDaAgencia());
    	dadosExistente.setBanco(dadosAtualizado.getBanco());
    	dadosExistente.setCidadeDaAgencia(dadosAtualizado.getCidadeDaAgencia());
    	dadosExistente.setEnderecoDaAgencia(dadosAtualizado.getEnderecoDaAgencia());
    	dadosExistente.setId(dadosAtualizado.getId());
    	dadosExistente.setNomeDaAgencia(dadosAtualizado.getNomeDaAgencia());
    	dadosExistente.setNumeroDaAgencia(dadosAtualizado.getNumeroDaAgencia());
    	dadosExistente.setNumeroDaConta(dadosAtualizado.getNumeroDaConta());
    	    	
    	dadosExistente.setAluno(dadosAtualizado.getAluno());
    	//aluno.save?
    	
    	return dadosRepo.save(dadosExistente);
    }

	public Optional<DadosBancarios> buscarDadosBancariosId(long id) {
		return dadosRepo.findById(id);
	}
	
	public DadosBancarios buscarDadosBancariosPorId(long id) {
		Optional<DadosBancarios> dadosFind = dadosRepo.findById(id);
		DadosBancarios dados = new DadosBancarios();

    	dados = dadosFind.get();
    	
        return dados;
	}

	public DadosBancarios criarDadosBancarios(Aluno aluno, DadosBancarios dadosBancarios) {
		aluno.setDadosBancarios(dadosBancarios);
		dadosBancarios.setAluno(aluno);
		
		return dadosRepo.save(dadosBancarios);
	}
}
