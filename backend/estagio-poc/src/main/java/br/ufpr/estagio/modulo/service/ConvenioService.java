package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.repository.AgenteIntegradorRepository;
import br.ufpr.estagio.modulo.repository.ConvenioRepository;

@Service
@Transactional
public class ConvenioService {
	
	@Autowired
	private ConvenioRepository convenioRepository;
	
	public Convenio criarConvenio(Convenio convenio) {
		return convenioRepository.save(convenio);
	}

	public Optional<Convenio> buscarPorId(Integer id) {
		return convenioRepository.findById(id);
	}

	public List<Convenio> listarConvenios() {
		return convenioRepository.findAll();
	}
}
