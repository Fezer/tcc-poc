package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.repository.ContratanteRepository;

@Service
@Transactional
public class ContratanteService {
	
	@Autowired
	private ContratanteRepository contratanteRepository;
	
	public Contratante criarContratante(Contratante contratante) {
		return contratanteRepository.save(contratante);
	}

	public Optional<Contratante> buscarPorId(Integer id) {
		return contratanteRepository.findById(id);
	}

	public List<Contratante> listarContratantes() {
		return contratanteRepository.findAll();
	}

	public Contratante atualizarContratante(Contratante contratanteAtualizado) {
		Contratante contratanteExistente = buscarPorId((int) contratanteAtualizado.getId())
                .orElseThrow();

		contratanteExistente.setTipo(contratanteAtualizado.getTipo());
		contratanteExistente.setCnpj(contratanteAtualizado.getCnpj());
		contratanteExistente.setCpf(contratanteAtualizado.getCpf());
		contratanteExistente.setRepresentanteEmpresa(contratanteAtualizado.getRepresentanteEmpresa());
		contratanteExistente.setEstagio(contratanteAtualizado.getEstagio());

        return contratanteRepository.save(contratanteExistente);
	}

	public void excluirContratante(Contratante c) {
		Optional<Contratante> contratanteOptional = contratanteRepository.findById((int) c.getId());
        if (contratanteOptional.isPresent()) {
        	Contratante contratante = contratanteOptional.get();
            contratanteRepository.delete(contratante);
        } else {
        	// tratar. tava dando erro.
            return;
        }
		
	}
}
