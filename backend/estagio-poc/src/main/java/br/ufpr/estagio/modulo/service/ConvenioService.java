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

	public Convenio atualizarConvenio(Convenio convenioAtualizado) {
		Convenio convenioExistente = buscarPorId((int) convenioAtualizado.getId())
                .orElseThrow();

		convenioExistente.setNumero(convenioAtualizado.getNumero());
        convenioExistente.setDescricao(convenioAtualizado.getDescricao());
        convenioExistente.setDataInicio(convenioAtualizado.getDataInicio());
        convenioExistente.setDataFim(convenioAtualizado.getDataFim());
        convenioExistente.setAgenteIntegrador(convenioAtualizado.getAgenteIntegrador());

        return convenioRepository.save(convenioExistente);
	}

	public void excluirConvenio(Convenio c) {
		Optional<Convenio> convenioOptional = convenioRepository.findById((int) c.getId());
        if (convenioOptional.isPresent()) {
        	Convenio convenio = convenioOptional.get();
        	convenioRepository.delete(convenio);
        } else {
        	// tratar. tava dando erro.
            return;
        }
		
	}
}
