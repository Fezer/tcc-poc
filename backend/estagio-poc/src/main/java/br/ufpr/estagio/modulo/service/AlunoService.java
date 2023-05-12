package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import br.ufpr.estagio.modulo.dto.AlunoDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.exception.NotFoundException;
import br.ufpr.estagio.modulo.mapper.SigaApiModuloEstagioMapper;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.CienciaCoordenacao;
import br.ufpr.estagio.modulo.model.DadosAuxiliares;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.AlunoRepository;
import br.ufpr.estagio.modulo.repository.CienciaCoordenacaoRepository;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.PlanoDeAtividadesRepository;
import br.ufpr.estagio.modulo.repository.TermoDeEstagioRepository;
import br.ufpr.estagio.modulo.service.siga.SigaApiAlunoService;


 
@Service
@Transactional
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepo;
	
	@Autowired
	private EstagioRepository estagioRepo;
	
	@Autowired
	private PlanoDeAtividadesRepository planoAtividadesRepo;
	
	@Autowired
	private TermoDeEstagioRepository termoRepo;
	
	@Autowired
	private CienciaCoordenacaoRepository cienciaRepo;
	
	@Autowired
	private SigaApiAlunoService sigaApiAlunoService;
	
	@Autowired
	private SigaApiModuloEstagioMapper sigaApiModuloEstagioMapping;

    public List<Aluno> listarTodosAlunos() {
        return alunoRepo.findAll();
    }
     
    public Aluno novoAluno(Aluno aluno) {
        return alunoRepo.save(aluno);
    }
    
    public Optional<Aluno> buscarAlunoPorId(long id) {   	
        return alunoRepo.findById(id);
    }
    
    public Aluno buscarAlunoPorGrr(String matricula) {
    	Optional<Aluno> alunoFind = alunoRepo.findByMatricula(matricula);
    	Aluno aluno = new Aluno();

    	if(alunoFind.isEmpty()) {
    		Discente discente = sigaApiAlunoService.buscarAlunoPorGrr(matricula);
    		aluno = sigaApiModuloEstagioMapping.mapearDiscenteEmAluno(discente);
    		aluno = this.salvarAluno(aluno);
    	} else {
    		aluno = alunoFind.get();
    	}
        return aluno;
    }
    
    public Optional<Aluno> buscarAlunoGrr(String matricula) {
    	Optional<Aluno> alunoFind = alunoRepo.findByMatricula(matricula);
    	Aluno aluno = new Aluno();

    	if(alunoFind.isEmpty()) {
    		Discente discente = sigaApiAlunoService.buscarAlunoPorGrr(matricula);
    		aluno = sigaApiModuloEstagioMapping.mapearDiscenteEmAluno(discente);
    		aluno = this.salvarAluno(aluno);
    	} else {
    		aluno = alunoFind.get();
    	}
        return Optional.ofNullable(aluno);
    }
     
    public Aluno salvarAluno(Aluno aluno) {
        return alunoRepo.save(aluno);
    }
    
    /////////////////////////////////////////////// 
    public Aluno atualizarAluno(Aluno alunoAtualizado) {
    	
    	System.out.println(alunoAtualizado.getId());
    	
    	Aluno alunoExistente = buscarAlunoGrr(alunoAtualizado.getMatricula())
    			.orElseThrow(() -> new NoSuchElementException("Aluno não encontrado para o ID informado"));
    	
    	DadosAuxiliares dadosExistente = alunoExistente.getDadosAuxiliares();
    	
    	System.out.println(dadosExistente.getNacionalidade());
    	System.out.println(alunoAtualizado.getDadosAuxiliares().getEstadoCivil());
    	
    	DadosAuxiliares dadosAtualizado = alunoAtualizado.getDadosAuxiliares();
    	
    	dadosExistente.setEstadoCivil(dadosAtualizado.getEstadoCivil());
    	dadosExistente.setDependentes(dadosAtualizado.getDependentes());
    	dadosExistente.setGrupoSanguineo(dadosAtualizado.getGrupoSanguineo());
    	dadosExistente.setDataDeChegadaNoPais(dadosAtualizado.getDataDeChegadaNoPais());
    	dadosExistente.setDataExpedicao(dadosAtualizado.getDataExpedicao());
    	dadosExistente.setTituloEleitoral(dadosAtualizado.getTituloEleitoral());
    	dadosExistente.setZona(dadosAtualizado.getZona());
    	dadosExistente.setSecao(dadosAtualizado.getSecao());
    	dadosExistente.setCertificadoMilitar(dadosAtualizado.getCertificadoMilitar());
    	dadosExistente.setOrgaoDeExpedicao(dadosAtualizado.getOrgaoDeExpedicao());
    	dadosExistente.setSerie(dadosAtualizado.getSerie());
    	dadosExistente.setEmailInstitucional(dadosAtualizado.getEmailInstitucional());
    	
    	//dados.save?
    	return alunoRepo.save(alunoExistente);
    }
     
    public void deletarAluno(long id) {
    	alunoRepo.deleteById(id);
    }

	public AlunoDTO toAlunoDTO(Aluno aluno) {
		return null;
	}
	
	public Estagio buscarEstagioPorId(long id) {   	
        return estagioRepo.findById(id).get();
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
		termoDeCompromisso.setCoordenador(aluno.getCurso().getCoordenador().get(0));
		
		
		
		//termoDeCompromisso.setCoordenador(new Coordenador(1, aluno.getCoordenador(), null, null));
		
		//Bloco de criação do Plano De Atividades com as devidas associações entre Termo De Estágio e Estagio.
		PlanoDeAtividades planoDeAtividades = new PlanoDeAtividades();
		planoDeAtividades.setEstagio(estagio);
		planoDeAtividades.setTermoDeEstagio(termoDeCompromisso);
		estagio.setPlanoDeAtividades(planoDeAtividades);
		termoDeCompromisso.setPlanoAtividades(planoDeAtividades);
		
		cienciaRepo.save(cienciaCoordenacao);
		planoAtividadesRepo.save(planoDeAtividades);
		estagioRepo.save(estagio);
		termoRepo.save(termoDeCompromisso);
		alunoRepo.save(aluno);
		
		return estagio;
	}

	public TermoDeEstagio solicitarAprovacaoTermo(Optional<TermoDeEstagio> termofind) {
		TermoDeEstagio termoAtualizado = termofind.get();
		Estagio estagio = termoAtualizado.getEstagio();
		
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmAprovacao;
		estagio.setStatusEstagio(statusEstagio);
		
		EnumEtapaFluxo etapaFluxo = null;
		//Se for estágio obrigatório não passa pela COE
		if(estagio.getTipoEstagio() == EnumTipoEstagio.Obrigatorio) {
			etapaFluxo = EnumEtapaFluxo.Coordenacao;
		} else {
			etapaFluxo = EnumEtapaFluxo.COE;
		}
		
		termoAtualizado.setEtapaFluxo(etapaFluxo);
		
		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		
		termoAtualizado.setStatusTermo(statusTermo);
		
		estagioRepo.save(estagio);
				
		return termoRepo.save(termoAtualizado);
	}
	
	public Aluno atualizarDadosAuxiliares(Aluno aluno) {
		
    	return alunoRepo.save(aluno);
    }
	
	public void cancelarEstagio(Estagio estagio) {
		
		// usado caso queira passar somente o id do estagio
	    /*Estagio estagio = estagioRepo.findById(idEstagio)
	        .orElseThrow(() -> new NotFoundException("Estágio não encontrado!"));*/
		
		//TO-DO: Considerar regras de negócio ao cancelar um estágio
		/** Somente é possível cancelar um estágio caso ele ainda não tenha sido aprovado, então
		 * precisamos fazer algumas validações antes de cancelar um estágio.
		 */
		TermoDeEstagio termoDeCompromisso = estagio.getTermoDeCompromisso();
		estagio.setStatusEstagio(EnumStatusEstagio.Cancelado);
		termoDeCompromisso.setStatusTermo(EnumStatusTermo.Cancelado);
			
		estagioRepo.save(estagio);
	    termoRepo.save(termoDeCompromisso);

	    /*TermoDeEstagio termoDeCompromisso = estagio.getTermoDeCompromisso();
	    //PlanoDeAtividades planoDeAtividades = estagio.getPlanoDeAtividades();
	    Aluno aluno = estagio.getAluno();
	    //List<Estagio> listaEstagios = aluno.getEstagio();
	    //listaEstagios.remove(estagio);
	    //aluno.setEstagio(listaEstagios);
	    estagio.setStatusEstagio(EnumStatusEstagio.Cancelado);
	    termoDeCompromisso.setStatusTermo(EnumStatusTermo.Cancelado);
	    
	    alunoRepo.save(aluno);*/
	    
//	    termoRepo.delete(termoDeCompromisso);
//	    planoAtividadesRepo.delete(planoDeAtividades);
//	    estagioRepo.delete(estagio);
	}

}
