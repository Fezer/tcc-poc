package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Curso;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.repository.OrientadorRepository;
 
@Service
@Transactional
public class OrientadorService {

	@Autowired
	private OrientadorRepository orientadorRepo;
		
    public OrientadorService(OrientadorRepository orientadorRepo) {
        this.orientadorRepo = orientadorRepo;
    }
     
    public List<Orientador> listarTodosOrientadors() {
        return orientadorRepo.findAll();
    }
     
    public Orientador novoOrientador(Orientador orientador) {
        return orientadorRepo.save(orientador);
    }
    
    public Optional<Orientador> buscarOrientadorPorId(long id) {
        return orientadorRepo.findById(id);
    }
     
    public Orientador salvarOrientador(Orientador orientador) {
        return orientadorRepo.save(orientador);
    }
     
    public Orientador atualizarOrientador(Orientador orientador) {
    	return orientadorRepo.save(orientador);
    }
     
    public void deletarOrientador(long id) {
    	orientadorRepo.deleteById(id);
    }

	public List<Orientador> salvarListaDocentes(List<String> docentes, Curso curso) {
		List<Orientador> listaOrientadores = new ArrayList<>();
		if (docentes != null) {
			for (String nome : docentes) {
				Orientador orientador = new Orientador();
				Optional<Orientador> orientadorFind = orientadorRepo.findByNome(nome);
				if (orientadorFind.isEmpty()) {
					orientador.setNome(nome);
					orientador = this.salvarOrientador(orientador);
				} else {
					orientador = orientadorFind.get();
				}
				listaOrientadores.add(orientador);
			}
		}
		return listaOrientadores;
	}

}
