package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.EnderecoDTO;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.repository.EnderecoRepository;

@Service
@Transactional
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	public Endereco criarEndereco(Endereco endereco) {
		return enderecoRepo.save(endereco);
	}

	public Optional<Endereco> buscarEndercoPorId(Long id) {
		return enderecoRepo.findById(id);
	}

	public List<Endereco> listarEnderecos() {
		return enderecoRepo.findAll();
	}

	public Endereco atualizarEndereco(Endereco endereco) {
        return enderecoRepo.save(endereco);
	}

	public void excluirEndereco(Endereco endereco) {
		enderecoRepo.delete(endereco);
	}

	public Endereco atualizarEndereco(Endereco enderecoAtual, EnderecoDTO endereco) {
		enderecoAtual.setCep(endereco.getCep());
		enderecoAtual.setCidade(endereco.getCidade());
		enderecoAtual.setComplemento(endereco.getComplemento());
		//enderecoAtual.setEstado(endereco.getEstado());
		enderecoAtual.setUf(endereco.getUf());
		//enderecoAtual.setLogradouro(endereco.getLogradouro());
		enderecoAtual.setRua(endereco.getRua());
		enderecoAtual.setNumero(endereco.getNumero());
		return enderecoRepo.save(enderecoAtual);
	}
}
