/*package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.SupervisorDTO;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.Supervisor;
import br.ufpr.estagio.modulo.repository.SupervisorRepository;
 
@Service
@Transactional
public class SupervisorService {

	@Autowired
	private SupervisorRepository supervisorRepo;
		
    public SupervisorService(SupervisorRepository supervisorRepo) {
        this.supervisorRepo = supervisorRepo;
    }
     
    public List<Supervisor> listarTodosSupervisors() {
        return supervisorRepo.findAll();
    }
     
    public Supervisor novoSupervisor(Supervisor supervisor) {
        return supervisorRepo.save(supervisor);
    }
    
    public Optional<Supervisor> buscarSupervisorPorId(long id) {
        return supervisorRepo.findById(id);
    }
     
    public Supervisor salvarSupervisor(Supervisor supervisor) {
        return supervisorRepo.save(supervisor);
    }
     
    public Supervisor atualizarSupervisor(Supervisor supervisor) {
    	return supervisorRepo.save(supervisor);
    }
     
    public void deletarSupervisor(long id) {
    	supervisorRepo.deleteById(id);
    }

	public Supervisor atualizarDados(Supervisor supervisor, SupervisorDTO supervisorDTO) {
		supervisor.setCpf(supervisorDTO.getCpf());
		supervisor.setNome(supervisorDTO.getNome());
		supervisor.setTelefone(supervisorDTO.getTelefone());
		supervisor.setFormacao(supervisorDTO.getFormacao());
		return supervisorRepo.save(supervisor);
	}
}
*/