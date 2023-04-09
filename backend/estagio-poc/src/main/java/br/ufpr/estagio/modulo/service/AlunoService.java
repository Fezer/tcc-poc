package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import br.ufpr.estagio.modulo.dto.AlunoDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.CienciaCoordenacao;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.AlunoRepository;


 
@Service
@Transactional
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepo;
	private SigaApiAlunoService sigaApiAlunoService;
		
    public AlunoService(AlunoRepository alunoRepo,
    		SigaApiAlunoService sigaApiAlunoService) {
        this.alunoRepo = alunoRepo;
        this.sigaApiAlunoService = sigaApiAlunoService;
    }
     
    public List<Aluno> listarTodosAlunos() {
        return alunoRepo.findAll();
    }
     
    public Aluno novoAluno(Aluno aluno) {
        return alunoRepo.save(aluno);
    }
    
    public Aluno buscarAlunoPorId(long id) {   	
        return alunoRepo.findById(id).get();
    }
    
    public Aluno buscarAlunoPorGrr(String matricula) {
    	Optional<Aluno> alunoFind = alunoRepo.findByMatricula(matricula);
    	Aluno aluno = new Aluno();
    	if(alunoFind.isEmpty()) {
    		Discente discente = sigaApiAlunoService.buscarAlunoPorGrr(matricula);
    		aluno.setMatricula(discente.getGrr());
    		aluno = this.salvarAluno(aluno);
    	} else {
    		aluno = alunoFind.get();
    	}
        return aluno;
    }
     
    public Aluno salvarAluno(Aluno aluno) {
        return alunoRepo.save(aluno);
    }
     
    public Aluno atualizarAluno(Aluno aluno) {
    	return alunoRepo.save(aluno);
    }
     
    public void deletarAluno(long id) {
    	alunoRepo.deleteById(id);
    }

	public AlunoDTO toAlunoDTO(Aluno aluno) {
		return null;
	}
	
	public Estagio novoEstagio(Aluno aluno) {
		//Bloco de criação do novo estágio e associação deste novo estágio ao Aluno;
		Estagio estagio = new Estagio();
		estagio.setStatusEstagio(EnumStatusEstagio.EmPreenchimento);
		estagio.setAluno(aluno);
		List<Estagio> listaEstagios = aluno.getEstagio();
		if(listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		listaEstagios.add(estagio);
		aluno.setEstagio(listaEstagios);
		
		//Bloco de criação do Termo De Compromisso com os devidos status inicais pós criação e associação deste termo de compromisso ao Estagio;
		TermoDeEstagio termoDeCompromisso = new TermoDeEstagio();
		termoDeCompromisso.setTipoTermoDeEstagio(EnumTipoTermoDeEstagio.TermoDeCompromisso);
		termoDeCompromisso.setEtapaFluxo(EnumEtapaFluxo.Aluno);
		termoDeCompromisso.setStatusTermo(EnumStatusTermo.EmPreenchimento);
		termoDeCompromisso.setEstagio(estagio);
		estagio.setTermoDeCompromisso(termoDeCompromisso);
		CienciaCoordenacao cienciaCoordenacao = new CienciaCoordenacao();
		termoDeCompromisso.setCienciaCoordenacao(cienciaCoordenacao);
		
		//Bloco de criação do Plano De Atividades com as devidas associações entre Termo De Estágio e Estagio.
		PlanoDeAtividades planoDeAtividades = new PlanoDeAtividades();
		planoDeAtividades.setEstagio(estagio);
		planoDeAtividades.setTermoDeEstagio(termoDeCompromisso);
		estagio.setPlanoDeAtividades(planoDeAtividades);
		termoDeCompromisso.setPlanoAtividades(planoDeAtividades);
		
		return estagio;
	}
}
