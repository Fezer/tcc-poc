package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.repository.AgenteIntegradorRepository;

@Service
@Transactional
public class AgenteIntegradorService {
	
	@Autowired
	private AgenteIntegradorRepository agenteIntegradorRepository;
	
	public AgenteIntegrador criarAgenteIntegrador(AgenteIntegrador agenteIntegrador) {
		return agenteIntegradorRepository.save(agenteIntegrador);
	}

	public Optional<AgenteIntegrador> buscarPorId(Integer id) {
		return agenteIntegradorRepository.findById(id);
	}
	
	public Optional<AgenteIntegrador> buscarPorId(long id) {
		return agenteIntegradorRepository.findById((int) id);
	}

	public List<AgenteIntegrador> listarAgentesIntegradores() {
		return agenteIntegradorRepository.findAll();
	}
	
	public AgenteIntegrador atualizarAgenteIntegrador(AgenteIntegrador agenteIntegradorAtualizado) {
        AgenteIntegrador agenteIntegradorExistente = buscarPorId(agenteIntegradorAtualizado.getId())
                .orElseThrow(() -> new NoSuchElementException("Agente integrador não encontrado para o ID informado"));

        agenteIntegradorExistente.setCnpj(agenteIntegradorAtualizado.getCnpj());
        agenteIntegradorExistente.setNome(agenteIntegradorAtualizado.getNome());
        agenteIntegradorExistente.setTelefone(agenteIntegradorAtualizado.getTelefone());
        agenteIntegradorExistente.setConvenio(agenteIntegradorAtualizado.getConvenio());
        agenteIntegradorExistente.setTermoDeEstagio(agenteIntegradorAtualizado.getTermoDeEstagio());
        agenteIntegradorExistente.setEstagio(agenteIntegradorAtualizado.getEstagio());

        return agenteIntegradorRepository.save(agenteIntegradorExistente);
    }
	
	public void excluirAgenteIntegrador(AgenteIntegrador agente) {
        Optional<AgenteIntegrador> agenteIntegradorOptional = agenteIntegradorRepository.findById((int) agente.getId());
        if (agenteIntegradorOptional.isPresent()) {
            AgenteIntegrador agenteIntegrador = agenteIntegradorOptional.get();
            agenteIntegradorRepository.delete(agenteIntegrador);
        } else {
        	throw new RuntimeException("Não foi encontrado um agente integrador com o ID informado.");
        }
    }
}
