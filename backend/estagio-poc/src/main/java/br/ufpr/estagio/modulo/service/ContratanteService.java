package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.repository.ContratanteRepository;
import br.ufpr.estagio.modulo.repository.EnderecoRepository;

@Service
@Transactional
public class ContratanteService {
	
	@Autowired
	private ContratanteRepository contratanteRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	public Contratante criarContratante(Contratante contratante) {
		return contratanteRepo.save(contratante);
	}

	public Optional<Contratante> buscarPorId(Long id) {
		return contratanteRepo.findById(id);
	}

	public List<Contratante> listarContratantes() {
		return contratanteRepo.findAll();
	}

	public Contratante atualizarContratante(Contratante contratanteAtualizado) {
		Contratante contratanteExistente = buscarPorId(contratanteAtualizado.getId())
                .orElseThrow(() -> new NoSuchElementException("Contratante não encontrado para o ID informado"));

		contratanteExistente.setTipo(contratanteAtualizado.getTipo());
		contratanteExistente.setCnpj(contratanteAtualizado.getCnpj());
		contratanteExistente.setCpf(contratanteAtualizado.getCpf());
		contratanteExistente.setRepresentanteEmpresa(contratanteAtualizado.getRepresentanteEmpresa());
		contratanteExistente.setEstagio(contratanteAtualizado.getEstagio());

        return contratanteRepo.save(contratanteExistente);
	}

	public void excluirContratante(Contratante c) {
		Optional<Contratante> contratanteOptional = contratanteRepo.findById(c.getId());
        if (contratanteOptional.isPresent()) {
        	Contratante contratante = contratanteOptional.get();
            contratanteRepo.delete(contratante);
        } else {
        	throw new RuntimeException("Não foi encontrado um contratante com o ID informado.");
        }
		
	}

	public Optional<Contratante> buscarPorNome(String nomeContratante) {
		return contratanteRepo.findByNome(nomeContratante);
	}

	public List<Contratante> buscarPorNomeContendo(String nomeContratante) {
		return contratanteRepo.findByNomeContainingIgnoreCase(nomeContratante);
	}

	public List<Contratante> buscarContratantePorNomeComecandoPor(String nomeContratante) {
		return contratanteRepo.findByNomeStartsWithIgnoreCase(nomeContratante);
	}

	public Contratante criarEnderecoContratante(Contratante contratante, Endereco endereco) {
		endereco = enderecoRepo.save(endereco);
		contratante.setEndereco(endereco);
		return contratanteRepo.save(contratante);
	}
}
