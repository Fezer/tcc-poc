package br.ufpr.estagio.modulo.service.siga;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Coordenador;
import br.ufpr.estagio.modulo.model.Curso;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.repository.AlunoRepository;
import br.ufpr.estagio.modulo.repository.CoordenadorRepository;
import br.ufpr.estagio.modulo.repository.CursoRepository;

@Service
@Transactional
public class SigaApiModuloEstagioMapping {
	
	@Autowired
	private AlunoRepository alunoRepo;
	private CursoRepository cursoRepo;
	private CoordenadorRepository coordenadorRepo;
	
	public SigaApiModuloEstagioMapping(AlunoRepository alunoRepo,
			CursoRepository cursoRepo,
			CoordenadorRepository coordenadorRepo) {
		this.alunoRepo = alunoRepo;
		this.cursoRepo = cursoRepo;
		this.coordenadorRepo = coordenadorRepo;
	}

	public Aluno mapearDiscenteEmAluno (Discente discente) {
		
		Optional<Aluno> alunoFind = alunoRepo.findByMatricula(discente.getGrr());
		Aluno aluno = new Aluno();
		if(alunoFind.isEmpty()) {
			aluno.setNome(discente.getNome());
			aluno.setIdDiscente(discente.getIdDiscente());
			aluno.setPcd(discente.isPcD());
			aluno.setCpf(discente.getDocumento());
			aluno.setMatricula(discente.getGrr());
			aluno.setPeriodoAtual(discente.getPeriodoAtual());
			aluno.setEmail(discente.getEmail());
			aluno.setRg(discente.getRg());			
		}else {
			aluno = alunoFind.get();
		}
		Optional<Curso> cursoFind = cursoRepo.findByNome(discente.getCurso().getNome());
		Curso curso = new Curso();
		if(cursoFind.isEmpty()) {
			curso.setNome(discente.getCurso().getNome());
		}else {
			curso = cursoFind.get();
		}
		Optional<Coordenador> coordenadorFind = coordenadorRepo.findByNome(discente.getCoordenador());
		Coordenador coordenador = new Coordenador();
		if(coordenadorFind.isEmpty()) {
			coordenador.setNome(discente.getCoordenador());
		}else {
			coordenador = coordenadorFind.get();
		}		
		
		aluno.setCurso(curso);
		
		List<Aluno> listAluno = curso.getAluno();
		listAluno.add(aluno);
		curso.setAluno(listAluno);
		
		List<Coordenador> listCoordenador = curso.getCoordenador();
		listCoordenador.add(coordenador);
		curso.setCoordenador(listCoordenador);
		
		alunoRepo.save(aluno);
		coordenadorRepo.save(coordenador);
		cursoRepo.save(curso);
		
		return aluno;
	}
}
