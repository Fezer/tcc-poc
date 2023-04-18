package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public Apolice atualizarApolice(Apolice apoliceAtualizada) {
		Apolice apoliceExistente = buscarPorId((int) apoliceAtualizada.getId())
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
	    Optional<Apolice> apoliceOptional = apoliceRepository.findById((int) apolice.getId());
	    if (apoliceOptional.isPresent()) {
	        Apolice apoliceExistente = apoliceOptional.get();
	        apoliceRepository.delete(apoliceExistente);
	    } else {
	    	throw new RuntimeException("Não foi encontrado uma apólice com o ID informado.");
	    }
	}

}
