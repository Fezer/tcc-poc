package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.repository.SeguradoraRepository;

@Service
@Transactional
public class SeguradoraService {

	@Autowired
	private SeguradoraRepository seguradoraRepository;
	
	public Seguradora criarSeguradora(Seguradora seguradora) {
		return seguradoraRepository.save(seguradora);
	}

	public Optional<Seguradora> buscarSeguradoraPorId(long id) {
		return seguradoraRepository.findById(id);
	}

	public List<Seguradora> listarSeguradoras() {
		return seguradoraRepository.findAll();
	}
	
}
