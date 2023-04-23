package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Coordenador;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.repository.CoordenadorRepository;
 
@Service
@Transactional
public class CoordenadorService {
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CoordenadorRepository coordenadorRepo;
	
    public CoordenadorService(CoordenadorRepository coordenadorRepo) {
        this.coordenadorRepo = coordenadorRepo;
    }
     
    public List<Coordenador> listarTodosCoordenadors() {
        return coordenadorRepo.findAll();
    }
     
    public Coordenador novoCoordenador(Coordenador coordenador) {
        return coordenadorRepo.save(coordenador);
    }
    
    public Coordenador buscarCoordenadorPorId(long id) {
        return coordenadorRepo.findById(id).get();
    }
     
    public Coordenador salvarCoordenador(Coordenador coordenador) {
        return coordenadorRepo.save(coordenador);
    }
     
    public Coordenador atualizarCoordenador(Coordenador coordenador) {
    	return coordenadorRepo.save(coordenador);
    }
     
    public void deletarCoordenador(long id) {
    	coordenadorRepo.deleteById(id);
    }

	public Coordenador mapearCoordenadorDiscente(Discente discente) {
		Optional<Coordenador> coordenadorFind = coordenadorRepo.findByNome(discente.getCoordenador());
		Coordenador coordenador = new Coordenador();
		if(coordenadorFind.isEmpty()) {
			coordenador.setNome(discente.getCoordenador());
			coordenador = this.salvarCoordenador(coordenador);
		}else {
			coordenador = coordenadorFind.get();
		}	
		return coordenador;
	}

}
