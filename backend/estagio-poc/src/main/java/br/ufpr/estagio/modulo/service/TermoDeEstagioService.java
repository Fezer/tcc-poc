package br.ufpr.estagio.modulo.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.PlanoDeAtividadesDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.AgenteIntegradorRepository;
import br.ufpr.estagio.modulo.repository.ApoliceRepository;
import br.ufpr.estagio.modulo.repository.ContratanteRepository;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.OrientadorRepository;
import br.ufpr.estagio.modulo.repository.PlanoDeAtividadesRepository;
import br.ufpr.estagio.modulo.repository.SeguradoraRepository;
import br.ufpr.estagio.modulo.repository.TermoDeEstagioRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
 
@Service
@Transactional
public class TermoDeEstagioService {
	
	@Autowired
	private TermoDeEstagioRepository termoRepo;
	@Autowired
	private PlanoDeAtividadesRepository planoRepo;
	@Autowired
	private OrientadorRepository orientadorRepo;
	@Autowired
	private EstagioRepository estagioRepo;
	@Autowired
	private AgenteIntegradorRepository agenteIntegradorRepo;
	/*@Autowired
	private SupervisorRepository supervisorRepo;*/
	@Autowired
	private ApoliceRepository apoliceRepo;
	@Autowired
	private SeguradoraRepository seguradoraRepo;
	@Autowired
	private ContratanteRepository contratanteRepo;
	
    public TermoDeEstagioService(TermoDeEstagioRepository termoRepo,
    		PlanoDeAtividadesRepository planoRepo,
    		OrientadorRepository orientadorRepo,
    		EstagioRepository estagioRepo) {
        this.termoRepo = termoRepo;
        this.planoRepo = planoRepo;
        this.orientadorRepo = orientadorRepo;
        this.estagioRepo = estagioRepo;
    }
     
    public List<TermoDeEstagio> listarTodos() {
        return termoRepo.findAll();
    }
     
    public TermoDeEstagio novo(TermoDeEstagio termoDeEstagio) {
    	EnumStatusTermo statusTermo = EnumStatusTermo.EmPreenchimento;
    	EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
    	termoDeEstagio.setStatusTermo(statusTermo);
    	termoDeEstagio.setEtapaFluxo(etapaFluxo);
        return termoRepo.save(termoDeEstagio);
    }
    
    public TermoDeEstagio buscarPorId(long id) {
        return termoRepo.findById(id).get();
    }
     
    public TermoDeEstagio salvar(TermoDeEstagio termoDeEstagio) {
        return termoRepo.save(termoDeEstagio);
    }
     
    public TermoDeEstagio atualizar(TermoDeEstagio termoDeEstagio) {
    	return termoRepo.save(termoDeEstagio);
    }
     
    public void deletar(long id) {
    	termoRepo.deleteById(id);
    }

	public TermoDeEstagio atualizarDados(Optional<TermoDeEstagio> termofind, TermoDeEstagioDTO termo) {
		TermoDeEstagio termoAtualizado = termofind.get();		
		termoAtualizado.setDataInicio(termo.getDataInicio() == null ? termoAtualizado.getDataInicio() : termo.getDataInicio());
		termoAtualizado.setDataTermino(termo.getDataTermino() == null ? termoAtualizado.getDataTermino() : termo.getDataTermino());
		termoAtualizado.setJornadaDiaria(termo.getJornadaDiaria() == 0 ? termoAtualizado.getJornadaDiaria() : termo.getJornadaDiaria());
		termoAtualizado.setJornadaSemanal(termo.getJornadaSemanal() == 0 ? termoAtualizado.getJornadaSemanal() : termo.getJornadaSemanal());
		termoAtualizado.setValorBolsa(termo.getValorBolsa() == 0 ? termoAtualizado.getValorBolsa() : termo.getValorBolsa());
		termoAtualizado.setValorTransporte(termo.getValorTransporte() == 0 ? termoAtualizado.getValorTransporte() : termo.getValorTransporte());
		termoAtualizado.setDataFimSuspensao(termo.getDataFimSuspensao() == null ? termoAtualizado.getDataFimSuspensao() : termo.getDataFimSuspensao());
		termoAtualizado.setDataInicioRetomada(termo.getDataInicioRetomada() == null ? termoAtualizado.getDataInicioRetomada() : termo.getDataInicioRetomada());
		return termoRepo.save(termoAtualizado);
	}

	public TermoDeEstagio atualizarPlanoAtividades(Optional<TermoDeEstagio> termofind,
			PlanoDeAtividadesDTO planoAtividades) {
		TermoDeEstagio termoAtualizado = termofind.get();
		PlanoDeAtividades planoAtividadesAtualizado = termoAtualizado.getPlanoAtividades();
		planoAtividadesAtualizado.setLocal(planoAtividades.getLocal() == "" ? planoAtividadesAtualizado.getLocal() : planoAtividades.getLocal());
		planoAtividadesAtualizado.setDescricaoAtividades(planoAtividades.getDescricaoAtividades() == "" ? planoAtividadesAtualizado.getDescricaoAtividades() : planoAtividades.getDescricaoAtividades());
		planoAtividadesAtualizado.setNomeSupervisor(planoAtividades.getNomeSupervisor() == "" ? planoAtividadesAtualizado.getNomeSupervisor() : planoAtividades.getNomeSupervisor());
		planoAtividadesAtualizado.setTelefoneSupervisor(planoAtividades.getTelefoneSupervisor() == null ? planoAtividadesAtualizado.getTelefoneSupervisor() : planoAtividades.getTelefoneSupervisor());
		planoAtividadesAtualizado.setCpfSupervisor(planoAtividades.getCpfSupervisor() == null ? planoAtividadesAtualizado.getCpfSupervisor() : planoAtividades.getCpfSupervisor());
		planoAtividadesAtualizado.setFormacaoSupervisor(planoAtividades.getFormacaoSupervisor() == null ? planoAtividadesAtualizado.getFormacaoSupervisor() : planoAtividades.getFormacaoSupervisor());

		planoRepo.save(planoAtividadesAtualizado);
		return termoRepo.save(termoAtualizado);
	}

	public TermoDeEstagio associarOrientadorAoTermo(TermoDeEstagio termoDeEstagio, Orientador orientador) {
		//Associa o orientador ao termo;
		termoDeEstagio.setOrientador(orientador);
		
		//Associa o orientador ao Estagio;
		termoDeEstagio.getEstagio().setOrientador(orientador);
		
		//Associa o termo ao orientador;
		List<TermoDeEstagio> listaTermos = orientador.getTermoDeEstagio();
		if(listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if(!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			orientador.setTermoDeEstagio(listaTermos);
		}
		
		orientadorRepo.save(orientador);
		estagioRepo.save(termoDeEstagio.getEstagio());
		termoDeEstagio = termoRepo.save(termoDeEstagio);
		
		return termoDeEstagio;
	}

	public TermoDeEstagio associarAgenteIntegradorAoTermo(TermoDeEstagio termoDeEstagio,
			AgenteIntegrador agenteIntegrador) {
		//Associa o agente integrador ao termo;
		termoDeEstagio.setAgenteIntegrador(agenteIntegrador);
		
		//Associa o agente integrador ao Estagio;
		termoDeEstagio.getEstagio().setAgenteIntegrador(agenteIntegrador);
		
		//Associa o termo ao agente integrador;
		List<TermoDeEstagio> listaTermos = agenteIntegrador.getTermoDeEstagio();
		if(listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if(!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			agenteIntegrador.setTermoDeEstagio(listaTermos);
		}
		
		//Associa o estagio ao agente integrador;
		List<Estagio> listaEstagios = agenteIntegrador.getEstagio();
		if(listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		if(!listaEstagios.contains(termoDeEstagio.getEstagio())) {
			listaEstagios.add(termoDeEstagio.getEstagio());
			agenteIntegrador.setEstagio(listaEstagios);
		}
		
		agenteIntegradorRepo.save(agenteIntegrador);
		estagioRepo.save(termoDeEstagio.getEstagio());
		termoDeEstagio = termoRepo.save(termoDeEstagio);
		
		return termoDeEstagio;
	}

	/*public TermoDeEstagio associarSupervisorAoTermo(TermoDeEstagio termoDeEstagio, Supervisor supervisor) {
		//Associa o supervisor ao termo;
		termoDeEstagio.setSupervisor(supervisor);
		
		//Associa o supervisor ao Estagio;
		termoDeEstagio.getEstagio().setSupervisor(supervisor);
		
		//Associa o supervisor ao Plano de Atividades;
		termoDeEstagio.getPlanoAtividades().setSupervisor(supervisor);
		
		//Associa o termo ao supervisor;
		List<TermoDeEstagio> listaTermos = supervisor.getTermoDeEstagio();
		if(listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if(!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			supervisor.setTermoDeEstagio(listaTermos);
		}
		
		//Associa o estagio ao supervisor;
		List<Estagio> listaEstagios = supervisor.getEstagio();
		if(listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		if(!listaEstagios.contains(termoDeEstagio.getEstagio())) {
			listaEstagios.add(termoDeEstagio.getEstagio());
			supervisor.setEstagio(listaEstagios);
		}
		
		//Associa o plano de atividades ao supervisor;
		List<PlanoDeAtividades> listaPlanoDeAtividades = supervisor.getPlanoDeAtividades();
		if(listaPlanoDeAtividades == null) {
			listaPlanoDeAtividades = new ArrayList<PlanoDeAtividades>();
		}
		if(!listaPlanoDeAtividades.contains(termoDeEstagio.getPlanoAtividades())) {
			listaPlanoDeAtividades.add(termoDeEstagio.getPlanoAtividades());
			supervisor.setPlanoDeAtividades(listaPlanoDeAtividades);
		}
				
		supervisorRepo.save(supervisor);
		estagioRepo.save(termoDeEstagio.getEstagio());
		planoRepo.save(termoDeEstagio.getPlanoAtividades());
		termoDeEstagio = termoRepo.save(termoDeEstagio);
		
		return termoDeEstagio;
	}*/

	public TermoDeEstagio associarApoliceAoTermo(TermoDeEstagio termoDeEstagio, Apolice apolice) {
		//Associa o apolice e seguradora ao termo;
		termoDeEstagio.setApolice(apolice);
		termoDeEstagio.setSeguradora(apolice.getSeguradora());
		
		//Associa o apolice e seguradora ao Estagio;
		termoDeEstagio.getEstagio().setApolice(apolice);
		termoDeEstagio.getEstagio().setSeguradora(apolice.getSeguradora());
		
		//Associa o Estagio e Termo a apolice;
		apolice.setEstagio(termoDeEstagio.getEstagio());
		apolice.setTermoDeEstagio(termoDeEstagio);
		
		//Associa o termo a seguradora;
		List<TermoDeEstagio> listaTermos = apolice.getSeguradora().getTermoDeEstagio();
		if(listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if(!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			apolice.getSeguradora().setTermoDeEstagio(listaTermos);
		}
		
		//Associa o estagio a seguradora;
		List<Estagio> listaEstagios = apolice.getSeguradora().getEstagio();
		if(listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		if(!listaEstagios.contains(termoDeEstagio.getEstagio())) {
			listaEstagios.add(termoDeEstagio.getEstagio());
			apolice.getSeguradora().setEstagio(listaEstagios);
		}
		
		estagioRepo.save(termoDeEstagio.getEstagio());
		seguradoraRepo.save(apolice.getSeguradora());
		apoliceRepo.save(apolice);

		termoDeEstagio = termoRepo.save(termoDeEstagio);
		
		return termoDeEstagio;
	}
	
	public TermoDeEstagio associarContratanteAoTermo(TermoDeEstagio termoDeEstagio,
			Contratante contratante) {
		//Associa o contratante ao termo;
		termoDeEstagio.setContratante(contratante);
		
		//Associa o contratante ao Estagio;
		termoDeEstagio.getEstagio().setContratante(contratante);
		
		//Associa o termo ao contratante;
		List<TermoDeEstagio> listaTermos = contratante.getTermoDeEstagio();
		if(listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if(!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			contratante.setTermoDeEstagio(listaTermos);
		}
		
		//Associa o estagio ao contratante;
		List<Estagio> listaEstagios = contratante.getEstagio();
		if(listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		if(!listaEstagios.contains(termoDeEstagio.getEstagio())) {
			listaEstagios.add(termoDeEstagio.getEstagio());
			contratante.setEstagio(listaEstagios);
		}
		
		contratanteRepo.save(contratante);
		estagioRepo.save(termoDeEstagio.getEstagio());
		termoDeEstagio = termoRepo.save(termoDeEstagio);
		
		return termoDeEstagio;
	}

	public List<TermoDeEstagio> listarTermosPendenteAprovacaoCoe() {
    	EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
    	EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;
    	EnumTipoEstagio tipoEstagio = EnumTipoEstagio.Obrigatorio;
		
    	// o `return termoRepo.findAll()` já não funciona. verificar a partir daí.
    	return termoRepo.findAll((root, query, builder) -> {

            Join<TermoDeEstagio, Object> estagioJoin = root.join("estagio", JoinType.INNER);
            query.where(
                builder.and(
                    builder.equal(root.get("statusTermo"), statusTermo),
                    builder.equal(root.get("etapaFluxo"), etapaFluxo),
                    builder.equal(estagioJoin.get("tipoEstagio"), tipoEstagio)
                )
            );
            return query.getRestriction();
        });

	}
}
