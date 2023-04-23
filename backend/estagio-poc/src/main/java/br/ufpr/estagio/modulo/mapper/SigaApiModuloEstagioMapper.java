package br.ufpr.estagio.modulo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Coordenador;
import br.ufpr.estagio.modulo.model.Curso;
import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.repository.AlunoRepository;
import br.ufpr.estagio.modulo.repository.CoordenadorRepository;
import br.ufpr.estagio.modulo.repository.CursoRepository;
import br.ufpr.estagio.modulo.service.CoordenadorService;
import br.ufpr.estagio.modulo.service.CursoService;

@Service
@Transactional
public class SigaApiModuloEstagioMapper {
	
	@Autowired
	private AlunoRepository alunoRepo;
	private CursoRepository cursoRepo;
	private CoordenadorRepository coordenadorRepo;
	
	private CursoService cursoService;
	private CoordenadorService coordenadorService;

	
	public SigaApiModuloEstagioMapper(AlunoRepository alunoRepo,
			CursoRepository cursoRepo,
			CoordenadorRepository coordenadorRepo,
			CursoService cursoService,
			CoordenadorService coordenadorService) {
		this.alunoRepo = alunoRepo;
		this.cursoRepo = cursoRepo;
		this.coordenadorRepo = coordenadorRepo;
		this.cursoService = cursoService;
		this.coordenadorService = coordenadorService;
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
		
		Curso curso = cursoService.mapearCursoDiscente(discente);
		
		Coordenador coordenador = coordenadorService.mapearCoordenadorDiscente(discente);
		
		aluno.setCurso(curso);
		//TO-DO: Avaliar se não é melhor colocar essa lógica dentro da classe Curso.
		List<Aluno> listAluno = curso.getAluno();				
		if (!listAluno.contains(aluno)){
			listAluno.add(aluno);
			curso.setAluno(listAluno);
		}
		
		coordenador.setCurso(curso);
		//TO-DO: Avaliar se não é melhor colocar essa lógica dentro da classe Curso.
		List<Coordenador> listCoordenador = curso.getCoordenador();
		if (!listCoordenador.contains(coordenador)) {
			listCoordenador.add(coordenador);
			curso.setCoordenador(listCoordenador);
		}
		
		alunoRepo.save(aluno);
		coordenadorRepo.save(coordenador);
		cursoRepo.save(curso);
		
		return aluno;
	}
	
	public Curso mapearCursoSigaEmCurso(CursoSiga cursoSiga) {
		
		return null;
	}
	
	public List<Orientador> mapearDocentesEmListaOrientadores(ArrayList<String> listaDocentes) {
		
		return null;
	}
	
}
