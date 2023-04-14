package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.repository.ApoliceRepository;

@Service
@Transactional
public class ApoliceService {

	@Autowired
	private ApoliceRepository apoliceRepository;
	
	public Apolice criarApolice(Apolice apolice) {
		return apoliceRepository.save(apolice);
	}

	public Optional<Apolice> buscarPorId(Integer id) {
		return apoliceRepository.findById(id);
	}

	public List<Apolice> listarApolices() {
		return apoliceRepository.findAll();
	}

}
