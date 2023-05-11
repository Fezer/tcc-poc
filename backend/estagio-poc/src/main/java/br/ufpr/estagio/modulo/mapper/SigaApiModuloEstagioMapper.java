package br.ufpr.estagio.modulo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Coordenador;
import br.ufpr.estagio.modulo.model.Curso;
import br.ufpr.estagio.modulo.model.CursoSiga;
import br.ufpr.estagio.modulo.model.DadosAuxiliares;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.repository.AlunoRepository;
import br.ufpr.estagio.modulo.repository.CoordenadorRepository;
import br.ufpr.estagio.modulo.repository.CursoRepository;
import br.ufpr.estagio.modulo.repository.DadosAuxiliaresRepository;
import br.ufpr.estagio.modulo.repository.EnderecoRepository;
import br.ufpr.estagio.modulo.service.CoordenadorService;
import br.ufpr.estagio.modulo.service.siga.SigaApiCursoSigaService;

@Service
@Transactional
public class SigaApiModuloEstagioMapper {
	
	@Autowired
	private AlunoRepository alunoRepo;
	
	@Autowired
	private CursoRepository cursoRepo;
	
	@Autowired
	private CoordenadorRepository coordenadorRepo;
	
	@Autowired
	private CoordenadorService coordenadorService;
	
	@Autowired
	private SigaApiCursoSigaService sigaApiCursoSigaService;

	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private DadosAuxiliaresRepository dadosAuxiliaresRepo;
	
	@Autowired
	private ModelMapper mapper;
	
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
			aluno.setCoordenador(discente.getCoordenador());
			aluno.setDataNascimento(discente.getDataNascimento());
			aluno.setIdCurso(discente.getIdCurso());
			aluno.setIdPrograma(discente.getIdPrograma());
			aluno.setIra(discente.getIra());
			aluno.setTurno(discente.getTurno());
			aluno.setMatriculado(discente.isMatriculado());
			aluno.setTelefone(discente.getTelefone());
			
		}else {
			aluno = alunoFind.get();
		}
		
		
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
		
		aluno.setEndereco(endereco);
		
		DadosAuxiliares dados = mapearDadosAuxiliaresAluno(discente, aluno);
		
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
		dadosAuxiliaresRepo.save(dados);
		alunoRepo.save(aluno);
		coordenadorRepo.save(coordenador);
		cursoRepo.save(curso);
		
		return aluno;
		//return null;
	}
	
	public Curso mapearCursoSigaEmCurso(Discente discente) {
		
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
	
	/*public Endereco mapearEnderecoAluno(Discente discente, Aluno aluno) {
		Endereco endereco = new Endereco();
		endereco.setCep(discente.getEndereco().getCep());
		endereco.setCidade(discente.getEndereco().getCidade());
		endereco.setComplemento(discente.getEndereco().getComplemento());
		endereco.setUf(discente.getEndereco().getUf());
		endereco.setId(discente.getEndereco().getId());
		endereco.setRua(discente.getEndereco().getRua());
		endereco.setNumero(discente.getEndereco().getNumero());
		endereco.setPessoa(aluno);
		
		return endereco;
	}*/
	
	public DadosAuxiliares mapearDadosAuxiliaresAluno(Discente discente, Aluno aluno) {
		DadosAuxiliares dadosAuxiliares = new DadosAuxiliares();
		
		dadosAuxiliares.setAutoIdentificacaoGenero(discente.getDadosAuxiliares().getAutoIdentificacaoGenero());
		dadosAuxiliares.setCertificadoMilitar(discente.getDadosAuxiliares().getCertificadoMilitar());
		dadosAuxiliares.setCidadeNascimento(discente.getDadosAuxiliares().getCidadeNascimento());
		dadosAuxiliares.setAutoIdentificacaoGenero(discente.getDadosAuxiliares().getAutoIdentificacaoGenero());
		dadosAuxiliares.setCorDaPele(discente.getDadosAuxiliares().getCorDaPele());
		dadosAuxiliares.setCorRaca(discente.getDadosAuxiliares().getCorRaca());
		dadosAuxiliares.setDataDeChegadaNoPais(discente.getDadosAuxiliares().getDataDeChegadaNoPais());
		dadosAuxiliares.setDataDeEmissao(discente.getDadosAuxiliares().getDataDeEmissao());
		dadosAuxiliares.setDataExpedicao(discente.getDadosAuxiliares().getDataExpedicao());
		dadosAuxiliares.setDependentes(discente.getDadosAuxiliares().getDependentes());
		dadosAuxiliares.setEmailInstitucional(discente.getDadosAuxiliares().getEmailInstitucional());
		dadosAuxiliares.setEstadoCivil(discente.getDadosAuxiliares().getEstadoCivil());
		dadosAuxiliares.setEstadoNascimento(discente.getDadosAuxiliares().getEstadoNascimento());
		dadosAuxiliares.setExpressaoGenero(discente.getDadosAuxiliares().getExpressaoGenero());
		dadosAuxiliares.setGrupoSanguineo(discente.getDadosAuxiliares().getGrupoSanguineo());
		dadosAuxiliares.setId(discente.getDadosAuxiliares().getId());
		dadosAuxiliares.setNacionalidade(discente.getDadosAuxiliares().getNacionalidade());
		dadosAuxiliares.setNomeMae(discente.getDadosAuxiliares().getNomeMae());
		dadosAuxiliares.setNomePai(discente.getDadosAuxiliares().getNomePai());
		dadosAuxiliares.setOrgaoDeExpedicao(discente.getDadosAuxiliares().getOrgaoDeExpedicao());
		dadosAuxiliares.setOrgaoEmissor(discente.getDadosAuxiliares().getOrgaoEmissor());
		dadosAuxiliares.setOrientacaoSexual(discente.getDadosAuxiliares().getOrientacaoSexual());
		dadosAuxiliares.setSecao(discente.getDadosAuxiliares().getSecao());
		dadosAuxiliares.setSerie(discente.getDadosAuxiliares().getSerie());
		dadosAuxiliares.setSexo(discente.getDadosAuxiliares().getSexo());
		dadosAuxiliares.setTituloEleitoral(discente.getDadosAuxiliares().getTituloEleitoral());
		dadosAuxiliares.setUf(discente.getDadosAuxiliares().getUf());
		dadosAuxiliares.setZona(discente.getDadosAuxiliares().getZona());
		dadosAuxiliares.setAluno(aluno);
		dadosAuxiliares.setOrgaoEmissor(discente.getOrgao());
		dadosAuxiliares.setUf(discente.getUf());
		dadosAuxiliares.setDataDeEmissao(discente.getDataEmissao());
		
		aluno.setDadosAuxiliares(dadosAuxiliares);
		
		
		return dadosAuxiliares;
	}
	
}
