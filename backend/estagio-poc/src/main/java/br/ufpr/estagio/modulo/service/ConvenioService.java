package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
	
	@Autowired
	private AgenteIntegradorRepository agenteIntegradorRepository;
	
	public Convenio criarConvenio(Convenio convenio) {
		return convenioRepository.save(convenio);
	}

	public Optional<Convenio> buscarPorId(Long id) {
		return convenioRepository.findById(id);
	}

	public List<Convenio> listarConvenios() {
		return convenioRepository.findAll();
	}

	public Convenio atualizarConvenio(Convenio convenioAtualizado) {
		Convenio convenioExistente = buscarPorId(convenioAtualizado.getId())
                .orElseThrow(() -> new NoSuchElementException("Convenio não encontrado para o ID informado"));

		convenioExistente.setNumero(convenioAtualizado.getNumero());
        convenioExistente.setDescricao(convenioAtualizado.getDescricao());
        convenioExistente.setDataInicio(convenioAtualizado.getDataInicio());
        convenioExistente.setDataFim(convenioAtualizado.getDataFim());
        convenioExistente.setAgenteIntegrador(convenioAtualizado.getAgenteIntegrador());

        return convenioRepository.save(convenioExistente);
	}

	public void excluirConvenio(Convenio c) {
		Optional<Convenio> convenioOptional = convenioRepository.findById(c.getId());
        if (convenioOptional.isPresent()) {
        	Convenio convenio = convenioOptional.get();
        	convenioRepository.delete(convenio);
        } else {
        	throw new RuntimeException("Não foi encontrado um convenio com o ID informado.");
        }
		
	}

	public Convenio associarAgenteAoConvenio(Convenio convenio, AgenteIntegrador agente) {
		convenio.setAgenteIntegrador(agente);
		List<Convenio> listConvenio = agente.getConvenio();
		if(listConvenio == null) {
			listConvenio = new ArrayList<Convenio>();
		}
		if(!listConvenio.contains(convenio)) {
			listConvenio.add(convenio);
			agente.setConvenio(listConvenio);
		}
		
		agenteIntegradorRepository.save(agente);
		convenioRepository.save(convenio);
		
		return convenio;
	}
}
