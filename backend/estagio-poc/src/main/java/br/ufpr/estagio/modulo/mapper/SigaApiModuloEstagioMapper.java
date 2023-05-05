package br.ufpr.estagio.modulo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Coordenador;
import br.ufpr.estagio.modulo.model.Curso;
import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.model.DadosAuxiliares;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.Disciplina;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.AlunoRepository;
import br.ufpr.estagio.modulo.repository.CoordenadorRepository;
import br.ufpr.estagio.modulo.repository.CursoRepository;
import br.ufpr.estagio.modulo.repository.CursoSigaRepository;
import br.ufpr.estagio.modulo.repository.EnderecoRepository;
import br.ufpr.estagio.modulo.service.CoordenadorService;
import br.ufpr.estagio.modulo.service.CursoService;
import br.ufpr.estagio.modulo.service.siga.SigaApiCursoSigaService;

@Service
@Transactional
public class SigaApiModuloEstagioMapper {
	
	@Autowired
	private AlunoRepository alunoRepo;
	private CursoRepository cursoRepo;
	private CoordenadorRepository coordenadorRepo;
	
	private CursoService cursoService;
	private CoordenadorService coordenadorService;
	
	@Autowired
	private SigaApiCursoSigaService sigaApiCursoSigaService;

	@Autowired
	private CursoSigaRepository cursoSigaRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private ModelMapper mapper;
	
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
		//Optional<Aluno> alunoFind = alunoRepo.findByMatricula(discente.getGrr());
		Aluno aluno = new Aluno();
		//if(alunoFind.isEmpty()) {
			aluno.setNome(discente.getNome());
			aluno.setIdDiscente(discente.getIdDiscente());
			aluno.setPcd(discente.isPcD());
			aluno.setCpf(discente.getDocumento());
			aluno.setMatricula(discente.getGrr());
			aluno.setPeriodoAtual(discente.getPeriodoAtual());
			aluno.setEmail(discente.getEmail());
			aluno.setRg(discente.getRg());
			aluno.setCoordenador(discente.getCoordenador());
			aluno.setDataNascimento(discente.getDataNascimento());
			aluno.setIdCurso(discente.getIdCurso());
			aluno.setIdPrograma(discente.getIdPrograma());
			aluno.setIra(discente.getIra());
			//aluno.setMatriculado(discente.isMatriculado());
			aluno.setTurno(discente.getTurno());
			
			aluno.setTelefone("TELEFONE");
			
			//dadosauxiliares
		/*}else {
			System.out.println("Entrou");
			aluno = alunoFind.get();
		}*/
		
		
		Curso curso = mapearCursoSigaEmCurso(discente);
		
		//Curso curso = cursoService.mapearCursoDiscente(discente);
		
		Coordenador coordenador = coordenadorService.mapearCoordenadorDiscente(discente);
		
		// Jogar isso para dentro de algum método mapeador
		Endereco endereco = new Endereco();
		endereco.setCep(discente.getEndereco().getCep());
		endereco.setCidade(discente.getEndereco().getCidade());
		endereco.setComplemento(discente.getEndereco().getComplemento());
		endereco.setUf(discente.getEndereco().getUf());
		endereco.setId(discente.getEndereco().getId());
		endereco.setRua(discente.getEndereco().getRua());
		endereco.setNumero(discente.getEndereco().getNumero());
		endereco.setPessoa(aluno);
		
		DadosAuxiliares dadosAuxiliares = new DadosAuxiliares();
		dadosAuxiliares.setAluno(aluno);
		
		aluno.setEndereco(endereco);
		
		//Dados auxiliares aqui
//		DadosAuxiliares dados...;
		
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
		
		enderecoRepo.save(endereco);
		alunoRepo.save(aluno);
		coordenadorRepo.save(coordenador);
		cursoRepo.save(curso);
		
		return aluno;
		//return null;
	}
	
	public Curso mapearCursoSigaEmCurso(Discente discente) {
		//Optional<CursoSiga> cursoSigaFind = cursoSigaRepo.findById(discente.getIdCurso());
		//System.out.println(cursoSigaFind.get());
		
		Optional<Curso> cursoFind = cursoRepo.findByIdCurso(discente.getIdCurso());
		if(cursoFind.isEmpty()) {
			CursoSiga cursoSiga = sigaApiCursoSigaService.buscarCursoSigaPorIdCurso(discente.getIdCurso());
			Curso curso = new Curso();
			curso = mapper.map(cursoSiga, Curso.class);
			return curso;
		} else
		
	    
		return cursoFind.get();
	}
	
	public List<Orientador> mapearDocentesEmListaOrientadores(ArrayList<String> listaDocentes) {
		
		return null;
	}
	
}
