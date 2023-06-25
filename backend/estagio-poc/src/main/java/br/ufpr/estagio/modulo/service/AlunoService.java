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
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.model.CienciaCoordenacao;
import br.ufpr.estagio.modulo.model.DadosAuxiliares;
import br.ufpr.estagio.modulo.model.Discente;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.AlunoRepository;
import br.ufpr.estagio.modulo.repository.CienciaCoordenacaoRepository;
import br.ufpr.estagio.modulo.repository.DadosAuxiliaresRepository;
import br.ufpr.estagio.modulo.repository.EnderecoRepository;
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
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private EstagioRepository estagioRepo;
	
	@Autowired
	private PlanoDeAtividadesRepository planoAtividadesRepo;
	
	@Autowired
	private TermoDeEstagioRepository termoRepo;
	
	@Autowired
	private CienciaCoordenacaoRepository cienciaRepo;
	
	@Autowired
	private DadosAuxiliaresRepository dadosRepo;
	
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
    
    public Aluno buscarAlunoPorGrr(String matricula, String accessToken) {
    	Optional<Aluno> alunoFind = alunoRepo.findByMatricula(matricula);
    	Aluno aluno = new Aluno();

    	if(alunoFind.isEmpty()) {
    		Discente discente = sigaApiAlunoService.buscarAlunoPorGrr(matricula, accessToken);
    		aluno = sigaApiModuloEstagioMapping.mapearDiscenteEmAluno(discente, accessToken);
    	} else {
    		aluno = alunoFind.get();
    		Optional<Endereco> enderecoFind = enderecoRepo.findByAlunoId(aluno.getId());
        	Endereco endereco = new Endereco();
        	endereco.setCep(enderecoFind.get().getCep());
        	endereco.setCidade(enderecoFind.get().getCidade());
        	endereco.setComplemento(enderecoFind.get().getComplemento());
        	endereco.setId(enderecoFind.get().getId());
        	endereco.setNumero(enderecoFind.get().getNumero());
        	endereco.setPessoa(enderecoFind.get().getPessoa());
        	endereco.setRua(enderecoFind.get().getRua());
        	endereco.setUf(enderecoFind.get().getUf());
        	
        	endereco.setAluno(enderecoFind.get().getAluno());
        	aluno.setEndereco(endereco);	
        	enderecoRepo.save(endereco);

    	}
        return aluno;
    }
    
    public Optional<Aluno> buscarAlunoGrr(String matricula, String accessToken) {
    	Optional<Aluno> alunoFind = alunoRepo.findByMatricula(matricula);
    	Aluno aluno = new Aluno();

    	if(alunoFind.isEmpty()) {
    		Discente discente = sigaApiAlunoService.buscarAlunoPorGrr(matricula, accessToken);
    		aluno = sigaApiModuloEstagioMapping.mapearDiscenteEmAluno(discente, accessToken);
    		aluno = this.salvarAluno(aluno);
    	} else {
    		aluno = alunoFind.get();
    	}
        return Optional.ofNullable(aluno);
    }
     
    public Aluno salvarAluno(Aluno aluno) {
        return alunoRepo.save(aluno);
    }
    
    public Aluno atualizarAluno(Aluno alunoAtualizado, String accessToken) {
    	
    	System.out.println(alunoAtualizado.getId());
    	
    	Aluno alunoExistente = buscarAlunoGrr(alunoAtualizado.getMatricula(), accessToken)
    			.orElseThrow(() -> new NoSuchElementException("Aluno não encontrado para o ID informado"));
    	
    	DadosAuxiliares dadosAuxiliaresExistente = alunoExistente.getDadosAuxiliares();
    	
    	System.out.println(dadosAuxiliaresExistente.getNacionalidade());
    	System.out.println(alunoAtualizado.getDadosAuxiliares().getEstadoCivil());
    	
    	DadosAuxiliares dadosAtualizado = alunoAtualizado.getDadosAuxiliares();
    	
    	dadosAuxiliaresExistente.setEstadoCivil(dadosAtualizado.getEstadoCivil());
    	dadosAuxiliaresExistente.setDependentes(dadosAtualizado.getDependentes());
    	dadosAuxiliaresExistente.setGrupoSanguineo(dadosAtualizado.getGrupoSanguineo());
    	dadosAuxiliaresExistente.setDataDeChegadaNoPais(dadosAtualizado.getDataDeChegadaNoPais());
    	dadosAuxiliaresExistente.setDataExpedicao(dadosAtualizado.getDataExpedicao());
    	dadosAuxiliaresExistente.setTituloEleitoral(dadosAtualizado.getTituloEleitoral());
    	dadosAuxiliaresExistente.setZona(dadosAtualizado.getZona());
    	dadosAuxiliaresExistente.setSecao(dadosAtualizado.getSecao());
    	dadosAuxiliaresExistente.setCertificadoMilitar(dadosAtualizado.getCertificadoMilitar());
    	dadosAuxiliaresExistente.setOrgaoDeExpedicao(dadosAtualizado.getOrgaoDeExpedicao());
    	dadosAuxiliaresExistente.setSerie(dadosAtualizado.getSerie());
    	dadosAuxiliaresExistente.setEmailInstitucional(dadosAtualizado.getEmailInstitucional());
    	
    	/**
    	 * TO-DO: 
    	 * 		-> Colocar os outros dados auxiliares no bloco acima.
    	 *		-> Colocar os dados bancários no bloco a ser criado abaixo.
    	 */
    	
    	dadosRepo.save(dadosAuxiliaresExistente);
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
		/**
		 * Bloco de criação do novo estágio e associação deste novo estágio ao Aluno.
		**/
		Estagio estagio = new Estagio();
		estagio.setStatusEstagio(EnumStatusEstagio.EmPreenchimento);
		estagio.setAluno(aluno);
		List<Estagio> listaEstagios = aluno.getEstagio();
		if(listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		listaEstagios.add(estagio);
		aluno.setEstagio(listaEstagios);
		
		/**
		 * Bloco de criação do Termo De Compromisso com os devidos status inicais
		 * pós criação e associação deste termo de compromisso ao Estagio.
		**/
		TermoDeEstagio termoDeCompromisso = new TermoDeEstagio();
		termoDeCompromisso.setTipoTermoDeEstagio(EnumTipoTermoDeEstagio.TermoDeCompromisso);
		termoDeCompromisso.setEtapaFluxo(EnumEtapaFluxo.Aluno);
		termoDeCompromisso.setStatusTermo(EnumStatusTermo.EmPreenchimento);
		termoDeCompromisso.setEstagio(estagio);
		estagio.setTermoDeCompromisso(termoDeCompromisso);
		CienciaCoordenacao cienciaCoordenacao = new CienciaCoordenacao();
		termoDeCompromisso.setCienciaCoordenacao(cienciaCoordenacao);
		termoDeCompromisso.setCoordenador(aluno.getCurso().getCoordenador().get(0));
		
		/**
		 * Bloco de criação do Plano De Atividades com as devidas
		 * associações entre Termo De Estágio e Estagio.
		 */
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
		
		if(termoAtualizado.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmAprovacao;
			estagio.setStatusEstagio(statusEstagio);
		}
		
		EnumEtapaFluxo etapaFluxo = null;
		/**
		 * Se for estágio da SEED o termo é encaminhado direto para a COAFE.
		 */
		if(estagio.isEstagioSeed()) {
			etapaFluxo = EnumEtapaFluxo.COAFE;
		} else {
			/**
			 * Se for estágio obrigatório não passa pela COE.
			 */
			if(estagio.getTipoEstagio() == EnumTipoEstagio.Obrigatorio) {
				etapaFluxo = EnumEtapaFluxo.Coordenacao;
			} else {
				etapaFluxo = EnumEtapaFluxo.COE;
			}
		}
		termoAtualizado.setEtapaFluxo(etapaFluxo);
		
		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		termoAtualizado.setStatusTermo(statusTermo);
		
		/**
		 * Pode se estar solicitando uma reaprovação de um termo que o Aluno ajustou,
		 * pelo motivo de algum dos atores terem solicitado ajuste. Desta forma,
		 * é importante zerar todos os pareceres e ciências, bem como a descrição
		 * do havia sido pedido pro aluno ajustar.
		 */
		termoAtualizado.setParecerCOAFE(null);
		termoAtualizado.setParecerCOE(null);
		termoAtualizado.setParecerCoordenacao(null);
		termoAtualizado.getCienciaCoordenacao().setCienciaFormacaoSupervisor(false);
		termoAtualizado.getCienciaCoordenacao().setCienciaIRA(false);
		termoAtualizado.getCienciaCoordenacao().setCienciaPlanoAtividades(false);
		termoAtualizado.setDescricaoAjustes(null);
		
		estagioRepo.save(estagio);
				
		return termoRepo.save(termoAtualizado);
	}
	
	public Aluno atualizarDadosAuxiliares(Aluno aluno) {
    	return alunoRepo.save(aluno);
    }
	
public Aluno atualizarDadosBancarios(Aluno aluno) {
    	return alunoRepo.save(aluno);
    }
	
	public void cancelarEstagio(Estagio estagio) {
		
		TermoDeEstagio termoDeCompromisso = estagio.getTermoDeCompromisso();
		
		/**
		 * Só é possível cancelar um estágio desta forma caso o estágio
		 * se encontre em um dos estados abaixo.
		 */
		if(estagio.getStatusEstagio() == EnumStatusEstagio.EmAprovacao || estagio.getStatusEstagio() == EnumStatusEstagio.EmPreenchimento) {
			estagio.setStatusEstagio(EnumStatusEstagio.Cancelado);
			termoDeCompromisso.setStatusTermo(EnumStatusTermo.Cancelado);
		}
		
		estagioRepo.save(estagio);
	    termoRepo.save(termoDeCompromisso);
	}

	public List<CertificadoDeEstagio> listarCertificadosDeEstagioAluno(Aluno aluno) {
		List<Estagio> listaEstagios = aluno.getEstagio();
		if (listaEstagios == null || listaEstagios.isEmpty()) {
			return null;
		}
		List<CertificadoDeEstagio> listaCertificados = new ArrayList<>();
		for (Estagio e : listaEstagios) {
			listaCertificados.add(e.getCertificadoDeEstagio());
		}
		return listaCertificados;
	}

	public List<TermoDeEstagio> listarTermosCompromissoAluno(Aluno aluno) {
		List<Estagio> listaEstagios = aluno.getEstagio();
		if (listaEstagios == null || listaEstagios.isEmpty()) {
			return null;
		}
		List<TermoDeEstagio> listaTermos = new ArrayList<>();
		for (Estagio e : listaEstagios) {
			listaTermos.add((TermoDeEstagio) e.getTermoAdivito());
		}
		return listaTermos;
	}

	public List<RelatorioDeEstagio> listarRelatoriosEstagioAluno(Aluno aluno) {
		List<Estagio> listaEstagios = aluno.getEstagio();
		if (listaEstagios == null || listaEstagios.isEmpty()) {
			return null;
		}
		List<RelatorioDeEstagio> listaRelatorios = new ArrayList<>();
		for (Estagio e : listaEstagios) {
			listaRelatorios.add((RelatorioDeEstagio) e.getRelatorioDeEstagio());
		}
		return listaRelatorios;
	}

	public Optional<Aluno> buscarAlunoPorEmail(String emailAluno, String accessToken) {
    	Optional<Aluno> alunoFind = alunoRepo.findByEmail(emailAluno);
        return alunoFind;
	}

}
