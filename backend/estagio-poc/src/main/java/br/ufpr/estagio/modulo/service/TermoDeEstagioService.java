package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.DescricaoAjustesDTO;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.dto.PlanoDeAtividadesDTO;
import br.ufpr.estagio.modulo.dto.TermoDeEstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.enums.EnumParecerAprovadores;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.CienciaCoordenacao;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.AgenteIntegradorRepository;
import br.ufpr.estagio.modulo.repository.ApoliceRepository;
import br.ufpr.estagio.modulo.repository.CienciaCoordenacaoRepository;
import br.ufpr.estagio.modulo.repository.ContratanteRepository;
import br.ufpr.estagio.modulo.repository.EnderecoRepository;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.OrientadorRepository;
import br.ufpr.estagio.modulo.repository.PlanoDeAtividadesRepository;
import br.ufpr.estagio.modulo.repository.SeguradoraRepository;
import br.ufpr.estagio.modulo.repository.TermoDeEstagioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.BeanUtils;

@Service
@Transactional
public class TermoDeEstagioService {

	private static final String selectTermosDeEstagioPorTipoTermoPorStatusTermo = "SELECT t FROM TermoDeEstagio t "
			+ "WHERE t.tipoTermoDeEstagio = :tipoTermoDeEstagio "
			+ "AND t.statusTermo = :statusTermo";

	private static final String selectTermosDeEstagioPorEtapaFluxoStatusTermoTipoTermo = "SELECT t FROM TermoDeEstagio t "
			+ "WHERE t.etapaFluxo = :etapaFluxo "
			+ "AND t.tipoTermoDeEstagio = :tipoTermoDeEstagio "
			+ "AND t.statusTermo = :statusTermo";

	private static final String selectTermoPorTipoEstagioEtapaFluxoStatusTermoTipoTermo = "SELECT t FROM TermoDeEstagio t INNER JOIN t.estagio e "
			+ "WHERE e.tipoEstagio = :tipoEstagio "
			+ "AND t.etapaFluxo = :etapaFluxo "
			+ "AND t.statusTermo = :statusTermo "
			+ "AND t.tipoTermoDeEstagio = :tipoTermoDeEstagio";

	private static final String selectTermoWithOptionalFields = "SELECT t FROM TermoDeEstagio t INNER JOIN t.estagio e WHERE 1=1 ";

	private static final String selectTermosDeEstagioTipoTermoPorAluno = "SELECT t FROM TermoDeEstagio t "
			+ "INNER JOIN t.estagio e "
			+ "INNER JOIN e.aluno a "
			+ "WHERE t.tipoTermoDeEstagio = :tipoTermoDeEstagio "
			+ "AND a.id = :idAluno";

	private static final String selectTermoDeEstagioPorIdPorTipoTermoPorAluno = "SELECT t FROM TermoDeEstagio t "
			+ "INNER JOIN t.estagio e "
			+ "INNER JOIN e.aluno a "
			+ "WHERE t.tipoTermoDeEstagio = :tipoTermoDeEstagio "
			+ "AND t.id = :id "
			+ "AND a.id = :idAluno";

	private static final String selectTermosDeEstagioTipoTermoPorStatusTermoPorAluno = "SELECT t FROM TermoDeEstagio t "
			+ "INNER JOIN t.estagio e "
			+ "INNER JOIN e.aluno a "
			+ "WHERE t.tipoTermoDeEstagio = :tipoTermoDeEstagio "
			+ "AND t.statusTermo = :statusTermo "
			+ "AND a.id = :idAluno";

	private static final String selectTermosDeEstagioIndeferidosPendentesCienciaCoordenacao = "SELECT t FROM TermoDeEstagio t "
			+ "WHERE t.etapaFluxo = :etapaFluxo "
			+ "AND t.statusTermo = :statusTermo "
			+ "AND t.tipoTermoDeEstagio = :tipoTermoDeEstagio "
			+ "AND (t.parecerCOAFE = :parecerCOAFE OR t.parecerCOE = :parecerCOE)";

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

	@Autowired
	private ApoliceRepository apoliceRepo;

	@Autowired
	private SeguradoraRepository seguradoraRepo;

	@Autowired
	private ContratanteRepository contratanteRepo;

	@Autowired
	private CienciaCoordenacaoRepository cienciaRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;

	@PersistenceContext
	private EntityManager em;

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

	public Optional<TermoDeEstagio> buscarPorIdOptional(long id) {
		return termoRepo.findById(id);
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
		termoAtualizado
				.setDataInicio(termo.getDataInicio() == null ? termoAtualizado.getDataInicio() : termo.getDataInicio());
		termoAtualizado.setDataTermino(
				termo.getDataTermino() == null ? termoAtualizado.getDataTermino() : termo.getDataTermino());
		termoAtualizado.setJornadaDiaria(
				termo.getJornadaDiaria() == 0 ? termoAtualizado.getJornadaDiaria() : termo.getJornadaDiaria());
		termoAtualizado.setJornadaSemanal(
				termo.getJornadaSemanal() == 0 ? termoAtualizado.getJornadaSemanal() : termo.getJornadaSemanal());
		termoAtualizado
				.setValorBolsa(termo.getValorBolsa() == 0 ? termoAtualizado.getValorBolsa() : termo.getValorBolsa());
		termoAtualizado.setValorTransporte(
				termo.getValorTransporte() == 0 ? termoAtualizado.getValorTransporte() : termo.getValorTransporte());
		termoAtualizado.setDataFimSuspensao(termo.getDataFimSuspensao());
		termoAtualizado.setDataInicioRetomada(termo.getDataInicioRetomada());
		return termoRepo.save(termoAtualizado);
	}

	public TermoDeEstagio atualizarPlanoAtividades(Optional<TermoDeEstagio> termofind,
			PlanoDeAtividadesDTO planoAtividades) {
		TermoDeEstagio termoAtualizado = termofind.get();
		PlanoDeAtividades planoAtividadesAtualizado = termoAtualizado.getPlanoAtividades();
		planoAtividadesAtualizado.setLocal(
				planoAtividades.getLocal() == "" ? planoAtividadesAtualizado.getLocal() : planoAtividades.getLocal());
		planoAtividadesAtualizado.setDescricaoAtividades(
				planoAtividades.getDescricaoAtividades() == "" ? planoAtividadesAtualizado.getDescricaoAtividades()
						: planoAtividades.getDescricaoAtividades());
		planoAtividadesAtualizado.setNomeSupervisor(
				planoAtividades.getNomeSupervisor() == "" ? planoAtividadesAtualizado.getNomeSupervisor()
						: planoAtividades.getNomeSupervisor());
		planoAtividadesAtualizado.setTelefoneSupervisor(
				planoAtividades.getTelefoneSupervisor() == null ? planoAtividadesAtualizado.getTelefoneSupervisor()
						: planoAtividades.getTelefoneSupervisor());
		planoAtividadesAtualizado.setCpfSupervisor(
				planoAtividades.getCpfSupervisor() == null ? planoAtividadesAtualizado.getCpfSupervisor()
						: planoAtividades.getCpfSupervisor());
		planoAtividadesAtualizado.setFormacaoSupervisor(
				planoAtividades.getFormacaoSupervisor() == null ? planoAtividadesAtualizado.getFormacaoSupervisor()
						: planoAtividades.getFormacaoSupervisor());

		planoRepo.save(planoAtividadesAtualizado);
		return termoRepo.save(termoAtualizado);
	}

	public TermoDeEstagio associarOrientadorAoTermoDeEstagio(TermoDeEstagio termoDeEstagio, Orientador orientador) {
		// Associa o orientador ao termo;
		termoDeEstagio.setOrientador(orientador);

		// Associa o orientador ao Estagio;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			termoDeEstagio.getEstagio().setOrientador(orientador);
		}

		// Associa o termo ao orientador;
		List<TermoDeEstagio> listaTermos = orientador.getTermoDeEstagio();
		if (listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if (!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			orientador.setTermoDeEstagio(listaTermos);
		}

		// Associa o Estagio ao orientador;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			List<Estagio> listaEstagio = orientador.getEstagio();
			if (listaEstagio == null) {
				listaEstagio = new ArrayList<Estagio>();
			}
			if (!listaEstagio.contains(termoDeEstagio.getEstagio())) {
				listaEstagio.add(termoDeEstagio.getEstagio());
				orientador.setEstagio(listaEstagio);
			}
		}

		orientadorRepo.save(orientador);
		estagioRepo.save(termoDeEstagio.getEstagio());
		termoDeEstagio = termoRepo.save(termoDeEstagio);

		return termoDeEstagio;
	}

	public TermoDeEstagio associarAgenteIntegradorAoTermoDeEstagio(TermoDeEstagio termoDeEstagio,
			AgenteIntegrador agenteIntegrador) {
		// Associa o agente integrador ao termo;
		termoDeEstagio.setAgenteIntegrador(agenteIntegrador);

		// Associa o agente integrador ao Estagio;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			termoDeEstagio.getEstagio().setAgenteIntegrador(agenteIntegrador);
		}

		// Associa o termo ao agente integrador;
		List<TermoDeEstagio> listaTermos = agenteIntegrador.getTermoDeEstagio();
		if (listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if (!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			agenteIntegrador.setTermoDeEstagio(listaTermos);
		}

		// Associa o estagio ao agente integrador;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			List<Estagio> listaEstagios = agenteIntegrador.getEstagio();
			if (listaEstagios == null) {
				listaEstagios = new ArrayList<Estagio>();
			}
			if (!listaEstagios.contains(termoDeEstagio.getEstagio())) {
				listaEstagios.add(termoDeEstagio.getEstagio());
				agenteIntegrador.setEstagio(listaEstagios);
			}
		}

		agenteIntegradorRepo.save(agenteIntegrador);
		estagioRepo.save(termoDeEstagio.getEstagio());
		termoDeEstagio = termoRepo.save(termoDeEstagio);

		return termoDeEstagio;
	}

	public TermoDeEstagio associarApoliceAoTermoDeEstagio(TermoDeEstagio termoDeEstagio, Apolice apolice) {
		// Associa o apolice e seguradora ao termo;
		termoDeEstagio.setApolice(apolice);
		termoDeEstagio.setSeguradora(apolice.getSeguradora());

		// Associa o apolice e seguradora ao Estagio;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			termoDeEstagio.getEstagio().setApolice(apolice);
			termoDeEstagio.getEstagio().setSeguradora(apolice.getSeguradora());
		}

		// Associa o Estagio e Termo a apolice;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			apolice.setEstagio(termoDeEstagio.getEstagio());
		}
		apolice.setTermoDeEstagio(termoDeEstagio);

		// Associa o termo a seguradora;
		List<TermoDeEstagio> listaTermos = apolice.getSeguradora().getTermoDeEstagio();
		if (listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if (!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			apolice.getSeguradora().setTermoDeEstagio(listaTermos);
		}

		// Associa o estagio a seguradora;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			List<Estagio> listaEstagios = apolice.getSeguradora().getEstagio();
			if (listaEstagios == null) {
				listaEstagios = new ArrayList<Estagio>();
			}
			if (!listaEstagios.contains(termoDeEstagio.getEstagio())) {
				listaEstagios.add(termoDeEstagio.getEstagio());
				apolice.getSeguradora().setEstagio(listaEstagios);
			}
		}

		estagioRepo.save(termoDeEstagio.getEstagio());
		seguradoraRepo.save(apolice.getSeguradora());
		apoliceRepo.save(apolice);

		termoDeEstagio = termoRepo.save(termoDeEstagio);

		return termoDeEstagio;
	}

	public TermoDeEstagio associarContratanteAoTermoDeEstagio(TermoDeEstagio termoDeEstagio,
			Contratante contratante) {
		// Associa o contratante ao termo;
		termoDeEstagio.setContratante(contratante);

		// Associa o contratante ao Estagio;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			termoDeEstagio.getEstagio().setContratante(contratante);
		}

		// Associa o termo ao contratante;
		List<TermoDeEstagio> listaTermos = contratante.getTermoDeEstagio();
		if (listaTermos == null) {
			listaTermos = new ArrayList<TermoDeEstagio>();
		}
		if (!listaTermos.contains(termoDeEstagio)) {
			listaTermos.add(termoDeEstagio);
			contratante.setTermoDeEstagio(listaTermos);
		}

		// Associa o estagio ao contratante;
		if (termoDeEstagio.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			List<Estagio> listaEstagios = contratante.getEstagio();
			if (listaEstagios == null) {
				listaEstagios = new ArrayList<Estagio>();
			}
			if (!listaEstagios.contains(termoDeEstagio.getEstagio())) {
				listaEstagios.add(termoDeEstagio.getEstagio());
				contratante.setEstagio(listaEstagios);
			}
		}

		contratanteRepo.save(contratante);
		estagioRepo.save(termoDeEstagio.getEstagio());
		termoDeEstagio = termoRepo.save(termoDeEstagio);

		return termoDeEstagio;
	}

	public Page<TermoDeEstagio> listarTermoCompromissoPaginated(int pagina,
			Optional<EnumStatusTermo> statusTermoOptional,
			Optional<EnumEtapaFluxo> etapaFluxoOptional,
			Optional<EnumTipoEstagio> tipoEstagioOptional,
			Optional<EnumTipoTermoDeEstagio> tipoTermoDeEstagioOptional,
			Optional<String> grrAluno) {

		StringBuilder jpql = new StringBuilder(selectTermoWithOptionalFields);

		// Configurar os parâmetros da consultas
		if (statusTermoOptional.isPresent()) {
			jpql.append(" AND t.statusTermo = :statusTermo");
		}
		if (etapaFluxoOptional.isPresent()) {
			jpql.append(" AND t.etapaFluxo = :etapaFluxo");
		}
		if (tipoEstagioOptional.isPresent()) {
			jpql.append(" AND e.tipoEstagio = :tipoEstagio");
		}
		if (tipoTermoDeEstagioOptional.isPresent()) {
			jpql.append(" AND t.tipoTermoDeEstagio = :tipoTermoDeEstagio");
		}
		if (grrAluno.isPresent()) {
			// add % to the beginning and end of the string
			grrAluno = Optional.of("%" + grrAluno.get() + "%");
			jpql.append(" AND e.aluno.matricula LIKE :grrAluno");
		}

		TypedQuery<TermoDeEstagio> query = em.createQuery(jpql.toString(), TermoDeEstagio.class);

		if (statusTermoOptional.isPresent()) {
			EnumStatusTermo statusTermo = statusTermoOptional.get();
			query.setParameter("statusTermo", statusTermo);
		}
		if (etapaFluxoOptional.isPresent()) {
			EnumEtapaFluxo etapaFluxo = etapaFluxoOptional.get();
			query.setParameter("etapaFluxo", etapaFluxo);
		}
		if (tipoEstagioOptional.isPresent()) {
			EnumTipoEstagio tipoEstagio = tipoEstagioOptional.get();
			query.setParameter("tipoEstagio", tipoEstagio);
		}
		if (tipoTermoDeEstagioOptional.isPresent()) {
			EnumTipoTermoDeEstagio tipoTermoDeEstagio = tipoTermoDeEstagioOptional.get();
			query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		}
		if (grrAluno.isPresent()) {
			String grr = grrAluno.get();
			query.setParameter("grrAluno", grr);
		}

		// Configurar a paginação
		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(pagina, 10);

		// Executar a consulta paginada
		List<TermoDeEstagio> termosPaginados = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(termosPaginados, pageRequest, totalRegistros);
	}

	public Page<TermoDeEstagio> listarTermosDeCompromissoPendenteAprovacaoCoe(int pagina) {
		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COE;
		EnumTipoEstagio tipoEstagio = EnumTipoEstagio.NaoObrigatorio;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermoPorTipoEstagioEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("tipoEstagio", tipoEstagio);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);

		// Configurar a paginação
		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(pagina, 10);

		// Executar a consulta paginada
		List<TermoDeEstagio> termosPaginados = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(termosPaginados, pageRequest, totalRegistros);

	}

	public TermoDeEstagio indeferirTermoDeEstagioCoe(TermoDeEstagio termo, JustificativaDTO justificativa) {
		EnumStatusTermo statusTermo = EnumStatusTermo.Reprovado;

		// Uma vez que a COE reprove o termo de compromisso, deve ser encaminhado para
		// ciencia da coordenação
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;

		EnumParecerAprovadores parecerCoe = EnumParecerAprovadores.Reprovado;
		termo.setStatusTermo(statusTermo);
		termo.setParecerCOE(parecerCoe);
		termo.setMotivoIndeferimento(justificativa.getJustificativa());

		// Após ter um termo indeferido, o Aluno terá que enviar um novo, então o
		// atributo upload volta a ser false
//		if (termo.getTipoTermoDeEstagio().equals(EnumTipoTermoDeEstagio.TermoDeCompromisso)) {
//			termo.setUploadCompromisso(false);
//		} else if (termo.getTipoTermoDeEstagio().equals(EnumTipoTermoDeEstagio.TermoAditivo)) {
//			termo.setUploadAditivo(false);
//		}

		// Uma vez que a COE reprove o termo de compromisso, deve ser encaminhado para
		// ciencia da coordenação
		termo.setEtapaFluxo(etapaFluxo);

		/**
		 * Só podemos considerar um estágio como reprovado nessa etapa caso seja termo
		 * de compromisso.
		 * A reprovação de um termo aditivo não pode reprovar automaticamente um estágio
		 * em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			Estagio estagio = termo.getEstagio();
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.Reprovado;
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public TermoDeEstagio solicitarAjutesTermoDeEstagioCoe(TermoDeEstagio termo,
			DescricaoAjustesDTO descricaoAjustes) {

		EnumStatusTermo statusTermo = EnumStatusTermo.EmRevisao;

		// Uma vez que a COE solicita ajustes no termo de compromisso, deve ser
		// encaminhado para revisão do Aluno
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;

		EnumParecerAprovadores parecerCoe = EnumParecerAprovadores.Ajustar;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCOE(parecerCoe);
		termo.setDescricaoAjustes(descricaoAjustes.getDescricaoAjustes());

		// Uma vez que a COE solicita ajustes no termo de compromisso, deve ser
		// encaminhado para revisão do Aluno
		termo.setEtapaFluxo(etapaFluxo);

		/**
		 * Só podemos mudar o status do Estagio nessa etapa caso seja termo de
		 * compromisso.
		 * A solicitação de ajustes de um termo aditivo não pode mudar o status de um
		 * estágio em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmPreenchimento;
			Estagio estagio = termo.getEstagio();
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public TermoDeEstagio aprovarTermoDeEstagioCoe(TermoDeEstagio termo) {

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;

		// Uma vez que a COE aprava o termo de compromisso, deve ser encaminhado para
		// análise da coordenação
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;

		EnumParecerAprovadores parecerCoe = EnumParecerAprovadores.Aprovado;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCOE(parecerCoe);

		// Uma vez que a COE aprava o termo de compromisso, deve ser encaminhado para
		// análise da coordenação
		termo.setEtapaFluxo(etapaFluxo);

		/**
		 * Só podemos mudar o status do Estagio nessa etapa caso seja termo de
		 * compromisso.
		 * A solicitação de ajustes de um termo aditivo não pode mudar o status de um
		 * estágio em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmAprovacao;
			Estagio estagio = termo.getEstagio();
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public Page<TermoDeEstagio> listarTermosDeCompromissoIndeferidos(int pagina) {

		EnumStatusTermo statusTermo = EnumStatusTermo.Reprovado;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioPorTipoTermoPorStatusTermo,
				TermoDeEstagio.class);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);

		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(pagina, 10);

		// Executar a consulta paginada
		List<TermoDeEstagio> termosPaginados = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(termosPaginados, pageRequest, totalRegistros);

	}

	public TermoDeEstagio indeferirTermoDeEstagioCoordenacao(TermoDeEstagio termo, JustificativaDTO justificativa) {

		EnumStatusTermo statusTermo = EnumStatusTermo.Reprovado;

		/**
		 * Uma vez que a Coordenação reprove o termo de compromisso, o fluxo se encerra,
		 * esse encerramento do fluxo é representado com o Termo de Compromisso
		 * retornato para o Aluno.
		 */
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;

		EnumParecerAprovadores parecerCoordenacao = EnumParecerAprovadores.Reprovado;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCoordenacao(parecerCoordenacao);
		termo.setMotivoIndeferimento(justificativa.getJustificativa());

		// Após ter um termo indeferido, o Aluno terá que enviar um novo, então o
		// atributo upload volta a ser false
		if (termo.getTipoTermoDeEstagio().equals(EnumTipoTermoDeEstagio.TermoDeCompromisso)) {
			termo.setUploadCompromisso(false);
		} else if (termo.getTipoTermoDeEstagio().equals(EnumTipoTermoDeEstagio.TermoAditivo)) {
			termo.setUploadAditivo(false);
		}

		/**
		 * Uma vez que a Coordenação reprove o termo de compromisso, o fluxo se encerra,
		 * esse encerramento do fluxo é representado com o Termo de Compromisso
		 * retornato para o Aluno.
		 */
		termo.setEtapaFluxo(etapaFluxo);

		/**
		 * Só podemos considerar um estágio como reprovado nessa etapa caso seja termo
		 * de compromisso.
		 * A reprovação de um termo aditivo não pode reprovar automaticamente um estágio
		 * em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.Reprovado;
			Estagio estagio = termo.getEstagio();
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public TermoDeEstagio aprovarTermoDeEstagioCoordenacao(TermoDeEstagio termo) {
		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;

		// Uma vez que a Coorenacao aprava o termo de compromisso, deve ser encaminhado
		// para análise da COAFE
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;

		EnumParecerAprovadores parecerCoordenacao = EnumParecerAprovadores.Aprovado;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCoordenacao(parecerCoordenacao);

		// Uma vez que a COE aprava o termo de compromisso, deve ser encaminhado para
		// análise da coordenação
		termo.setEtapaFluxo(etapaFluxo);

		/**
		 * Só podemos mudar o status do Estagio nessa etapa caso seja termo de
		 * compromisso.
		 * A solicitação de ajustes de um termo aditivo não pode mudar o status de um
		 * estágio em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmAprovacao;
			Estagio estagio = termo.getEstagio();
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public TermoDeEstagio solicitarAjutesTermoDeEstagioCoordenacao(TermoDeEstagio termo,
			DescricaoAjustesDTO descricaoAjustes) {

		EnumStatusTermo statusTermo = EnumStatusTermo.EmRevisao;

		// Uma vez que a Coordenação solicita ajustes no termo de compromisso, deve ser
		// encaminhado para revisão do Aluno
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;

		EnumParecerAprovadores parecerCoordenacao = EnumParecerAprovadores.Ajustar;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCoordenacao(parecerCoordenacao);
		termo.setDescricaoAjustes(descricaoAjustes.getDescricaoAjustes());

		// Uma vez que a Coordenação solicita ajustes no termo de compromisso, deve ser
		// encaminhado para revisão do Aluno
		termo.setEtapaFluxo(etapaFluxo);

		/**
		 * Só podemos mudar o status do Estagio nessa etapa caso seja termo de
		 * compromisso.
		 * A solicitação de ajustes de um termo aditivo não pode mudar o status de um
		 * estágio em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmPreenchimento;
			Estagio estagio = termo.getEstagio();
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public List<TermoDeEstagio> listarTermosDeCompromissoPendenteAprovacaoCoordenacao() {

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioPorEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);

		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosDeCompromissoPendenteAprovacaoCoordenacaoPorTipoEstagio(
			String tipoEstagioString) {

		EnumTipoEstagio tipoEstagio;
		tipoEstagioString = tipoEstagioString.toUpperCase();

		switch (tipoEstagioString) {
			case "OBRIGATORIO":
				tipoEstagio = EnumTipoEstagio.Obrigatorio;
				break;
			case "NAOOBRIGATORIO":
				tipoEstagio = EnumTipoEstagio.NaoObrigatorio;
				break;
			default:
				return null;
		}

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermoPorTipoEstagioEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("tipoEstagio", tipoEstagio);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public TermoDeEstagio darCienciaFormacaoSupervisorCoordenacao(TermoDeEstagio termo) {
		termo.getCienciaCoordenacao().setCienciaFormacaoSupervisor(true);
		return termoRepo.save(termo);
	}

	public TermoDeEstagio darCienciaPlanoAtividadesCoordenacao(TermoDeEstagio termo) {
		termo.getCienciaCoordenacao().setCienciaPlanoAtividades(true);
		return termoRepo.save(termo);
	}

	public TermoDeEstagio darCienciaIraCoordenacao(TermoDeEstagio termo) {
		termo.getCienciaCoordenacao().setCienciaIRA(true);
		return termoRepo.save(termo);
	}

	public TermoDeEstagio darCienciaIndeferimentoCoordenacao(TermoDeEstagio termo) {
		EnumStatusTermo statusTermo = EnumStatusTermo.Reprovado;

		// Uma vez que a Coordenação da ciencia no indeferimento da COE ou COAFE, o
		// fluxo se encerra e o termo deve ser encaminhado ao Aluno.
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;

		EnumParecerAprovadores parecerCoordenacao = EnumParecerAprovadores.Ciente;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCoordenacao(parecerCoordenacao);

		// Uma vez que a Coordenação da ciencia no indeferimento da COE ou COAFE, o
		// fluxo se encerra e o termo deve ser encaminhado ao Aluno.
		termo.setEtapaFluxo(etapaFluxo);
		
		// Após ter um termo indeferido, o Aluno terá que enviar um novo, então o
		// atributo upload volta a ser false quando a coordenação da ciencia no indeferimento.
		if (termo.getTipoTermoDeEstagio().equals(EnumTipoTermoDeEstagio.TermoDeCompromisso)) {
			termo.setUploadCompromisso(false);
		} else if (termo.getTipoTermoDeEstagio().equals(EnumTipoTermoDeEstagio.TermoAditivo)) {
			termo.setUploadAditivo(false);
		}

		/**
		 * Só podemos considerar um estágio como reprovado nessa etapa caso seja termo
		 * de compromisso.
		 * A reprovação de um termo aditivo não pode reprovar automaticamente um estágio
		 * em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.Reprovado;
			Estagio estagio = termo.getEstagio();
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public List<TermoDeEstagio> listarTermosDeCompromissoIndeferidosPendentesCienciaCoordenacao() {

		EnumStatusTermo statusTermo = EnumStatusTermo.Reprovado;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;
		EnumParecerAprovadores parecerCOE = EnumParecerAprovadores.Reprovado;
		EnumParecerAprovadores parecerCOAFE = EnumParecerAprovadores.Reprovado;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioIndeferidosPendentesCienciaCoordenacao,
				TermoDeEstagio.class);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("parecerCOE", parecerCOE);
		query.setParameter("parecerCOAFE", parecerCOAFE);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosDeCompromissoPendenteAprovacaoCoafe() {
		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioPorEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosDeCompromissoPendenteAprovacaoCoafePorTipoEstagio(
			String tipoEstagioString) {

		EnumTipoEstagio tipoEstagio;
		tipoEstagioString = tipoEstagioString.toUpperCase();

		switch (tipoEstagioString) {
			case "OBRIGATORIO":
				tipoEstagio = EnumTipoEstagio.Obrigatorio;
				break;
			case "NAOOBRIGATORIO":
				tipoEstagio = EnumTipoEstagio.NaoObrigatorio;
				break;
			default:
				return null;
		}

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermoPorTipoEstagioEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("tipoEstagio", tipoEstagio);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public TermoDeEstagio indeferirTermoDeEstagioCoafe(TermoDeEstagio termo, JustificativaDTO justificativa) {

		EnumStatusTermo statusTermo = EnumStatusTermo.Reprovado;

		// Uma vez que a COAFE reprove o termo de compromisso, deve ser encaminhado para
		// ciencia da coordenação
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;

		EnumParecerAprovadores parecerCoafe = EnumParecerAprovadores.Reprovado;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCOAFE(parecerCoafe);
		termo.setMotivoIndeferimento(justificativa.getJustificativa());

		// Após ter um termo indeferido, o Aluno terá que enviar um novo, então o
		// atributo upload volta a ser false
//		if (termo.getTipoTermoDeEstagio().equals(EnumTipoTermoDeEstagio.TermoDeCompromisso)) {
//			termo.setUploadCompromisso(false);
//		} else if (termo.getTipoTermoDeEstagio().equals(EnumTipoTermoDeEstagio.TermoAditivo)) {
//			termo.setUploadAditivo(false);
//		}

		// Uma vez que a COAFE reprove o termo de compromisso, deve ser encaminhado para
		// ciencia da coordenação
		termo.setEtapaFluxo(etapaFluxo);

		/**
		 * Só podemos considerar um estágio como reprovado nessa etapa caso seja termo
		 * de compromisso.
		 * A reprovação de um termo aditivo não pode reprovar automaticamente um estágio
		 * em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.Reprovado;
			Estagio estagio = termo.getEstagio();
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public TermoDeEstagio solicitarAjutesTermoDeEstagioCoafe(TermoDeEstagio termo,
			DescricaoAjustesDTO descricaoAjustes) {

		EnumStatusTermo statusTermo = EnumStatusTermo.EmRevisao;

		// Uma vez que a Coafe solicita ajustes no termo de compromisso, deve ser
		// encaminhado para revisão do Aluno
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;

		EnumParecerAprovadores parecerCoafe = EnumParecerAprovadores.Ajustar;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCOAFE(parecerCoafe);
		termo.setDescricaoAjustes(descricaoAjustes.getDescricaoAjustes());

		// Uma vez que a Coafe solicita ajustes no termo de compromisso, deve ser
		// encaminhado para revisão do Aluno
		termo.setEtapaFluxo(etapaFluxo);

		/**
		 * Só podemos mudar o status do Estagio nessa etapa caso seja termo de
		 * compromisso.
		 * A solicitação de ajustes de um termo aditivo não pode mudar o status de um
		 * estágio em andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmPreenchimento;
			Estagio estagio = termo.getEstagio();
			estagio.setStatusEstagio(statusEstagio);
			estagioRepo.save(estagio);
		}

		return termoRepo.save(termo);
	}

	public TermoDeEstagio aprovarTermoDeEstagioCoafe(TermoDeEstagio termo) {

		EnumStatusTermo statusTermo = EnumStatusTermo.Aprovado;

		// Uma vez que a COAFE aprava o termo de compromisso, o fluxo se encerra e o
		// termo é devolvido aprovado para o Aluno.
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;

		EnumParecerAprovadores parecerCoafe = EnumParecerAprovadores.Aprovado;

		termo.setStatusTermo(statusTermo);
		termo.setParecerCOAFE(parecerCoafe);

		// Uma vez que a COE aprava o termo de compromisso, deve ser encaminhado para
		// análise da coordenação
		termo.setEtapaFluxo(etapaFluxo);

		Estagio estagio = termo.getEstagio();

		/**
		 * Só podemos mudar o status do Estagio nessa etapa caso seja termo de
		 * compromisso.
		 * A aprovação de um termo aditivo não pode mudar o status de um estágio em
		 * andamento.
		 */
		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso) {
			EnumStatusEstagio statusEstagio = EnumStatusEstagio.Aprovado;
			estagio.setStatusEstagio(statusEstagio);
		}

		/**
		 * Como o termo de compromisso foi aprovado pela COAFE e o fluxo chegou ao fim,
		 * a consolidação das informações do Termo é realizada no Estágio.
		 */
		estagio.setDataInicio(termo.getDataInicio());
		estagio.setDataTermino(termo.getDataTermino());
		estagio.setJornadaDiaria(termo.getJornadaDiaria());
		estagio.setJornadaSemanal(termo.getJornadaSemanal());
		estagio.setValorBolsa(termo.getValorBolsa());
		estagio.setValorTransporte(termo.getValorTransporte());

		List<Estagio> listaEstagios = null;

		if (estagio.isEstagioUfpr() == false) {
			estagio.setContratante(termo.getContratante());
			listaEstagios = termo.getContratante().getEstagio();
			if (listaEstagios == null) {
				listaEstagios = new ArrayList<Estagio>();
			}
			if (!listaEstagios.contains(termo.getEstagio())) {
				listaEstagios.add(termo.getEstagio());
				termo.getContratante().setEstagio(listaEstagios);
			}
		}

		estagio.setSeguradora(termo.getSeguradora());
		listaEstagios = termo.getSeguradora().getEstagio();
		if (listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		if (!listaEstagios.contains(termo.getEstagio())) {
			listaEstagios.add(termo.getEstagio());
			termo.getSeguradora().setEstagio(listaEstagios);
		}

		estagio.setApolice(termo.getApolice());
		termo.getApolice().setEstagio(estagio);

		estagio.setOrientador(termo.getOrientador());
		listaEstagios = termo.getOrientador().getEstagio();
		if (listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		if (!listaEstagios.contains(termo.getEstagio())) {
			listaEstagios.add(termo.getEstagio());
			termo.getOrientador().setEstagio(listaEstagios);
		}

		estagio.setAgenteIntegrador(termo.getAgenteIntegrador());
		listaEstagios = termo.getAgenteIntegrador().getEstagio();
		if (listaEstagios == null) {
			listaEstagios = new ArrayList<Estagio>();
		}
		if (!listaEstagios.contains(termo.getEstagio())) {
			listaEstagios.add(termo.getEstagio());
			termo.getAgenteIntegrador().setEstagio(listaEstagios);
		}

		estagio.setPlanoDeAtividades(termo.getPlanoAtividades());
		termo.getPlanoAtividades().setEstagio(estagio);

		if (estagio.isEstagioUfpr() == false) {
			contratanteRepo.save(estagio.getContratante());
		}
		seguradoraRepo.save(estagio.getSeguradora());
		apoliceRepo.save(estagio.getApolice());
		orientadorRepo.save(estagio.getOrientador());
		agenteIntegradorRepo.save(estagio.getAgenteIntegrador());
		planoRepo.save(estagio.getPlanoDeAtividades());
		estagioRepo.save(estagio);

		return termoRepo.save(termo);
	}

	/**
	 * A ideia desse método é que o termoAditivo nasce espelhando as informações
	 * correntes do Estagio.
	 * Isso porque assume-se que o termoAditivo irá mudar algumas características do
	 * estágio já iniciado,
	 * e não todas elas. Desta forma, esse espelhamento visa facilitar, mantendo no
	 * termoAditivo
	 * informações atuais do Estágio que não serão alteradas pelo termoAditivo.
	 * 
	 * @param estagio
	 * @return
	 */
	public TermoDeEstagio novoTermoAditivo(Estagio estagio) {
		EnumTipoTermoDeEstagio tipoTermo = EnumTipoTermoDeEstagio.TermoAditivo;
		EnumStatusTermo statusTermo = EnumStatusTermo.EmPreenchimento;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;

		TermoDeEstagio termoAditivo = new TermoDeEstagio();
		BeanUtils.copyProperties(estagio, termoAditivo);

		TermoDeEstagio termoAditivoTemp = new TermoDeEstagio();
		termoAditivoTemp = termoRepo.save(termoAditivoTemp);

		termoAditivo.setId(termoAditivoTemp.getId());
		termoAditivo.setTipoTermoDeEstagio(tipoTermo);
		termoAditivo.setDataCriacao(termoAditivoTemp.getDataCriacao());
		termoAditivo.setParecerCoordenacao(termoAditivoTemp.getParecerCoordenacao());
		termoAditivo.setStatusTermo(statusTermo);
		termoAditivo.setEtapaFluxo(etapaFluxo);
		termoAditivo.setParecerCoordenacao(termoAditivoTemp.getParecerCoordenacao());
		termoAditivo.setParecerCOE(termoAditivoTemp.getParecerCOE());
		termoAditivo.setParecerCOAFE(termoAditivoTemp.getParecerCOAFE());
		termoAditivo.setDescricaoAjustes(termoAditivoTemp.getDescricaoAjustes());
		termoAditivo.setMotivoIndeferimento(termoAditivoTemp.getMotivoIndeferimento());

		PlanoDeAtividades planoAtividade = new PlanoDeAtividades();
		BeanUtils.copyProperties(estagio.getPlanoDeAtividades(), planoAtividade);

		PlanoDeAtividades planoAtividadeTemp = new PlanoDeAtividades();
		planoAtividadeTemp = planoRepo.save(planoAtividadeTemp);

		planoAtividade.setId(planoAtividadeTemp.getId());

		termoAditivo.setPlanoAtividades(planoAtividade);

		CienciaCoordenacao cienciaCoordenacao = new CienciaCoordenacao();

		cienciaCoordenacao = cienciaRepo.save(cienciaCoordenacao);
		
		termoAditivo.setCoordenador(estagio.getAluno().getCurso().getCoordenador().get(0));

		termoAditivo.setCienciaCoordenacao(cienciaCoordenacao);

		List<TermoDeEstagio> listaTermosAditivos = estagio.getTermoAdivito();
		if (listaTermosAditivos == null) {
			listaTermosAditivos = new ArrayList<TermoDeEstagio>();
		}

		listaTermosAditivos.add(termoAditivo);
		estagio.setTermoAdivito(listaTermosAditivos);

		termoAditivo.setEstagio(estagio);

		estagioRepo.save(estagio);

		planoRepo.save(planoAtividade);

		return termoRepo.save(termoAditivo);

	}

	public TermoDeEstagio cancelarTermoAditivo(TermoDeEstagio termoAditivo) {
		if (termoAditivo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoAditivo) {
			termoAditivo.setStatusTermo(EnumStatusTermo.Cancelado);
			termoAditivo.setEtapaFluxo(EnumEtapaFluxo.Aluno);
		}
		return termoRepo.save(termoAditivo);
	}

	public List<TermoDeEstagio> listarTermosAditivosPorAluno(Aluno aluno) {
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioTipoTermoPorAluno, TermoDeEstagio.class);

		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public TermoDeEstagio listarTermoAditivoPorId(Aluno aluno, Long id) {
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermoDeEstagioPorIdPorTipoTermoPorAluno,
				TermoDeEstagio.class);

		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		query.setParameter("id", id);
		query.setParameter("idAluno", aluno.getId());

		return query.getSingleResult();
	}

	public List<TermoDeEstagio> listarTermosAditivosEmPreenchimentoPorAluno(Aluno aluno) {
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;
		EnumStatusTermo statusTermo = EnumStatusTermo.EmPreenchimento;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioTipoTermoPorStatusTermoPorAluno,
				TermoDeEstagio.class);

		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosAditivosPorAlunoPorStatus(Aluno aluno, String statusTermoString) {
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;
		EnumStatusTermo statusTermo;
		statusTermoString = statusTermoString.toUpperCase();
		switch (statusTermoString) {
			case "EMPREENCHIMENTO":
				statusTermo = EnumStatusTermo.EmPreenchimento;
				break;
			case "EMAPROVACAO":
				statusTermo = EnumStatusTermo.EmAprovacao;
				break;
			case "EMREVISAO":
				statusTermo = EnumStatusTermo.EmRevisao;
				break;
			case "EMASSINATURA":
				statusTermo = EnumStatusTermo.EmRevisao;
				break;
			case "APROVADO":
				statusTermo = EnumStatusTermo.Aprovado;
				break;
			case "CANCELADO":
				statusTermo = EnumStatusTermo.Cancelado;
				break;
			case "REPROVADO":
				statusTermo = EnumStatusTermo.Reprovado;
				break;
			default:
				return null;
		}

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioTipoTermoPorStatusTermoPorAluno,
				TermoDeEstagio.class);

		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosDeCompromissoPorAluno(Aluno aluno) {
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioTipoTermoPorAluno, TermoDeEstagio.class);

		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosDeCompromissoEmPreenchimentoPorAluno(Aluno aluno) {
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;
		EnumStatusTermo statusTermo = EnumStatusTermo.EmPreenchimento;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioTipoTermoPorStatusTermoPorAluno,
				TermoDeEstagio.class);

		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosDeCompromissoPorAlunoPorStatus(Aluno aluno, String statusTermoString) {
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoDeCompromisso;
		EnumStatusTermo statusTermo;
		statusTermoString = statusTermoString.toUpperCase();
		switch (statusTermoString) {
			case "EMPREENCHIMENTO":
				statusTermo = EnumStatusTermo.EmPreenchimento;
				break;
			case "EMAPROVACAO":
				statusTermo = EnumStatusTermo.EmAprovacao;
				break;
			case "EMREVISAO":
				statusTermo = EnumStatusTermo.EmRevisao;
				break;
			case "EMASSINATURA":
				statusTermo = EnumStatusTermo.EmRevisao;
				break;
			case "APROVADO":
				statusTermo = EnumStatusTermo.Aprovado;
				break;
			case "CANCELADO":
				statusTermo = EnumStatusTermo.Cancelado;
				break;
			case "REPROVADO":
				statusTermo = EnumStatusTermo.Reprovado;
				break;
			default:
				return null;
		}

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioTipoTermoPorStatusTermoPorAluno,
				TermoDeEstagio.class);

		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public Page<TermoDeEstagio> listarTermosAditivosIndeferidos(int pagina) {

		EnumStatusTermo statusTermo = EnumStatusTermo.Reprovado;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioPorTipoTermoPorStatusTermo,
				TermoDeEstagio.class);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);

		// Configurar a paginação
		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(pagina, 10);

		// Executar a consulta paginada
		List<TermoDeEstagio> termosPaginados = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(termosPaginados, pageRequest, totalRegistros);

	}

	public List<TermoDeEstagio> listarTermosAditivosIndeferidosPendentesCienciaCoordenacao() {
		EnumStatusTermo statusTermo = EnumStatusTermo.Reprovado;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;
		EnumParecerAprovadores parecerCOE = EnumParecerAprovadores.Reprovado;
		EnumParecerAprovadores parecerCOAFE = EnumParecerAprovadores.Reprovado;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioIndeferidosPendentesCienciaCoordenacao,
				TermoDeEstagio.class);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("parecerCOE", parecerCOE);
		query.setParameter("parecerCOAFE", parecerCOAFE);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosAditivosPendenteAprovacaoCoordenacaoPorTipoEstagio(
			String tipoEstagioString) {

		EnumTipoEstagio tipoEstagio;
		tipoEstagioString = tipoEstagioString.toUpperCase();

		switch (tipoEstagioString) {
			case "OBRIGATORIO":
				tipoEstagio = EnumTipoEstagio.Obrigatorio;
				break;
			case "NAOOBRIGATORIO":
				tipoEstagio = EnumTipoEstagio.NaoObrigatorio;
				break;
			default:
				return null;
		}

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermoPorTipoEstagioEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("tipoEstagio", tipoEstagio);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosAditivosPendenteAprovacaoCoordenacao() {

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Coordenacao;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioPorEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public Page<TermoDeEstagio> listarTermosAditivoPendenteAprovacaoCoe(
			int pagina) {

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COE;
		EnumTipoEstagio tipoEstagio = EnumTipoEstagio.NaoObrigatorio;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermoPorTipoEstagioEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("tipoEstagio", tipoEstagio);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);

		// Configurar a paginação
		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(pagina, 10);

		// Executar a consulta paginada
		List<TermoDeEstagio> termosPaginados = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(termosPaginados, pageRequest, totalRegistros);
	}

	public List<TermoDeEstagio> listarTermosAditivosPendenteAprovacaoCoafe() {

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermosDeEstagioPorEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public List<TermoDeEstagio> listarTermosAditivosPendenteAprovacaoCoafePorTipoEstagio(String tipoEstagioString) {

		EnumTipoEstagio tipoEstagio;
		tipoEstagioString = tipoEstagioString.toUpperCase();

		switch (tipoEstagioString) {
			case "OBRIGATORIO":
				tipoEstagio = EnumTipoEstagio.Obrigatorio;
				break;
			case "NAOOBRIGATORIO":
				tipoEstagio = EnumTipoEstagio.NaoObrigatorio;
				break;
			default:
				return null;
		}

		EnumStatusTermo statusTermo = EnumStatusTermo.EmAprovacao;
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
		EnumTipoTermoDeEstagio tipoTermoDeEstagio = EnumTipoTermoDeEstagio.TermoAditivo;

		TypedQuery<TermoDeEstagio> query = em.createQuery(selectTermoPorTipoEstagioEtapaFluxoStatusTermoTipoTermo,
				TermoDeEstagio.class);
		query.setParameter("tipoEstagio", tipoEstagio);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("statusTermo", statusTermo);
		query.setParameter("tipoTermoDeEstagio", tipoTermoDeEstagio);
		return query.getResultList();
	}

	public TermoDeEstagio uploadTermoDeEstagio(TermoDeEstagio termo, String nomeArquivo) {

		if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoAditivo)
			termo.setUploadAditivo(true);

		else if (termo.getTipoTermoDeEstagio() == EnumTipoTermoDeEstagio.TermoDeCompromisso)
			termo.setUploadCompromisso(true);

		List<String> listaAux = new ArrayList<>();
		listaAux.add(nomeArquivo);

		termo.setArquivos(listaAux);

		return termoRepo.save(termo);
	}

	public boolean listarTermosDeEstagioPorAgenteIntegrador(AgenteIntegrador agenteIntegrador) {

		List<TermoDeEstagio> termos = termoRepo.findByAgenteIntegrador(agenteIntegrador);

		if (termos.size() == 0)
			return false;

		return true;
	}

	public boolean listarTermosDeEstagioPorApolice(Apolice apolice) {

		List<TermoDeEstagio> termos = termoRepo.findByApolice(apolice);

		if (termos.size() == 0)
			return false;

		return true;
	}

	public boolean listarTermosDeEstagioPorContratante(Contratante contratante) {

		List<TermoDeEstagio> termos = termoRepo.findByContratante(contratante);

		if (termos.size() == 0)
			return false;

		return true;
	}

	public boolean listarTermosDeEstagioPorSeguradora(Seguradora seguradora) {

		List<TermoDeEstagio> termos = termoRepo.findBySeguradora(seguradora);

		if (termos.size() == 0)
			return false;

		return true;
	}
	
	public TermoDeEstagio associarContratanteUfprAoTermo(TermoDeEstagio termo) {
	/**
	 * Caso seja um estágio UFPR, precisa associar a contratante
	 * como UFPR ao termo.
	 */
		if (termo.getEstagio().isEstagioUfpr()) {
			String cnpjFederal = "75.095.679/0001-49";
			Optional<Contratante> contratanteFind = contratanteRepo.findByCnpj(cnpjFederal);
			if (contratanteFind.isEmpty()) {
				Contratante contratante = new Contratante();
				contratante.setAtivo(true);
				contratante.setCnpj(cnpjFederal);
				contratante.setNome("Universidade Federal do Paraná");
				contratante.setRepresentanteEmpresa("Ricardo Marcelo Fonseca");
				contratante.setTelefone("4133102627");
				contratante.setTipo(EnumTipoContratante.PessoaJuridica);
				Endereco endereco = new Endereco();
				endereco.setCidade("Curitiba");
				endereco.setUf("PR");
				endereco.setRua("Rua XV de Novembro");
				endereco.setNumero(1299);
				endereco.setCep("80020-300");
				endereco = enderecoRepo.save(endereco);
				contratante.setEndereco(endereco);
				contratante = contratanteRepo.save(contratante);
				termo.setContratante(contratante);
			} else {
				termo.setContratante(contratanteFind.get());
			}
			termo.getEstagio().setContratante(termo.getContratante());
			termo = termoRepo.save(termo);
		}
		return termo;
	}

}
