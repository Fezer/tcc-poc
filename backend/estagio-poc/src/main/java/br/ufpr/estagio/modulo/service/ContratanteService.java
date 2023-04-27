package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Optional<Contratante> buscarPorId(Long id) {
		return contratanteRepository.findById(id);
	}

	public List<Contratante> listarContratantes() {
		return contratanteRepository.findAll();
	}

	public Contratante atualizarContratante(Contratante contratanteAtualizado) {
		Contratante contratanteExistente = buscarPorId(contratanteAtualizado.getId())
                .orElseThrow(() -> new NoSuchElementException("Contratante não encontrado para o ID informado"));

		contratanteExistente.setTipo(contratanteAtualizado.getTipo());
		contratanteExistente.setCnpj(contratanteAtualizado.getCnpj());
		contratanteExistente.setCpf(contratanteAtualizado.getCpf());
		contratanteExistente.setRepresentanteEmpresa(contratanteAtualizado.getRepresentanteEmpresa());
		contratanteExistente.setEstagio(contratanteAtualizado.getEstagio());

        return contratanteRepository.save(contratanteExistente);
	}

	public void excluirContratante(Contratante c) {
		Optional<Contratante> contratanteOptional = contratanteRepository.findById(c.getId());
        if (contratanteOptional.isPresent()) {
        	Contratante contratante = contratanteOptional.get();
            contratanteRepository.delete(contratante);
        } else {
        	throw new RuntimeException("Não foi encontrado um contratante com o ID informado.");
        }
		
	}

	public Optional<Contratante> buscarPorNome(String nomeContratante) {
		return contratanteRepository.findByNome(nomeContratante);
	}
}
