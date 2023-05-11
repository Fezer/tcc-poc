package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.NoSuchElementException;
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
	
	public Seguradora atualizarSeguradora(Seguradora seguradoraAtualizada) {
	    Seguradora seguradoraExistente = buscarSeguradoraPorId(seguradoraAtualizada.getId())
	            .orElseThrow(() -> new NoSuchElementException("Seguradora não encontrada para o ID informado"));

	    seguradoraExistente.setNome(seguradoraAtualizada.getNome());
	    seguradoraExistente.setApolice(seguradoraAtualizada.getApolice());
	    seguradoraExistente.setTermoDeEstagio(seguradoraAtualizada.getTermoDeEstagio());
	    seguradoraExistente.setEstagio(seguradoraAtualizada.getEstagio());
	    seguradoraExistente.setAtiva(seguradoraAtualizada.isAtiva());

	    return seguradoraRepository.save(seguradoraExistente);
	}
	
	public Seguradora ativarDesativarSeguradora(Seguradora seguradoraAtualizada) {
		Seguradora seguradoraExistente = buscarSeguradoraPorId(seguradoraAtualizada.getId())
	            .orElseThrow(() -> new NoSuchElementException("Seguradora não encontrada para o ID informado"));
		
		if (seguradoraExistente.isAtiva() == true && seguradoraAtualizada.isAtiva() == true) {
			seguradoraExistente.setError("Seguradora já está ativa.");
			return seguradoraExistente;
		}
		
		if (seguradoraExistente.isAtiva() == false && seguradoraAtualizada.isAtiva() == false) {
			seguradoraExistente.setError("Seguradora já está inativa.");
			return seguradoraExistente;
		}
		
		seguradoraExistente.setError(null);
		seguradoraExistente.setAtiva(seguradoraAtualizada.isAtiva());
	
		return seguradoraRepository.save(seguradoraExistente);
	}

	public void excluirSeguradora(Seguradora seguradora) {
	    Optional<Seguradora> seguradoraOptional = seguradoraRepository.findById((long) seguradora.getId());
	    if (seguradoraOptional.isPresent()) {
	        Seguradora seguradoraExistente = seguradoraOptional.get();
	        seguradoraRepository.delete(seguradoraExistente);
	    } else {
        	throw new RuntimeException("Não foi encontrada uma seguradora com o ID informado.");

	    }
	}

	
}
