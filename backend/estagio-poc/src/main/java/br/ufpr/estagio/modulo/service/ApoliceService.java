package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.repository.ApoliceRepository;
import br.ufpr.estagio.modulo.repository.SeguradoraRepository;

@Service
@Transactional
public class ApoliceService {

	@Autowired
	private ApoliceRepository apoliceRepository;
	
	@Autowired
	private SeguradoraRepository seguradoraRepository;
	
	public Apolice criarApolice(Apolice apolice) {
		return apoliceRepository.save(apolice);
	}

	public Optional<Apolice> buscarPorId(Long id) {
		return apoliceRepository.findById(id);
	}

	public List<Apolice> listarApolices() {
		return apoliceRepository.findAll();
	}
	
	public Apolice atualizarApolice(Apolice apoliceAtualizada) {
		Apolice apoliceExistente = buscarPorId(apoliceAtualizada.getId())
		.orElseThrow(() -> new NoSuchElementException("Apólice não encontrada para o ID informado"));

	    apoliceExistente.setNumero(apoliceAtualizada.getNumero());
	    apoliceExistente.setDataInicio(apoliceAtualizada.getDataInicio());
	    apoliceExistente.setDataFim(apoliceAtualizada.getDataFim());
	    apoliceExistente.setSeguradora(apoliceAtualizada.getSeguradora());
	    apoliceExistente.setTermoDeEstagio(apoliceAtualizada.getTermoDeEstagio());
	    apoliceExistente.setEstagio(apoliceAtualizada.getEstagio());

	    return apoliceRepository.save(apoliceExistente);
	}
	
	public void excluirApolice(Apolice apolice) {
	    Optional<Apolice> apoliceOptional = apoliceRepository.findById(apolice.getId());
	    if (apoliceOptional.isPresent()) {
	        Apolice apoliceExistente = apoliceOptional.get();
	        apoliceRepository.delete(apoliceExistente);
	    } else {
	    	throw new RuntimeException("Não foi encontrado uma apólice com o ID informado.");
	    }
	}

	public Apolice associarSeguradoraApolice(Apolice apolice, Seguradora seguradora) {
		apolice.setSeguradora(seguradora);
		List<Apolice> listApolice = seguradora.getApolice();
		if(listApolice == null) {
			listApolice = new ArrayList<Apolice>();
		}
		if(!listApolice.contains(apolice)) {
			listApolice.add(apolice);
			seguradora.setApolice(listApolice);
		}
		
		seguradoraRepository.save(seguradora);
		apoliceRepository.save(apolice);
		
		return apolice;
	}

}
