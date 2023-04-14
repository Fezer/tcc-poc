package br.ufpr.estagio.modulo.service;

import java.util.List;
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

	public List<AgenteIntegrador> listarAgentesIntegradores() {
		return agenteIntegradorRepository.findAll();
	}
}
