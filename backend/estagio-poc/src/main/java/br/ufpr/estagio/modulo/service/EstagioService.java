package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.EstagioDTO;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.RelatorioDeEstagio;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
@Transactional
public class EstagioService {

	private static final String selectPorIdOrientadorFiltroStatusEstagio = "SELECT e FROM Estagio e INNER JOIN e.orientador o "
			+ "WHERE o.id = :idOrientador "
			+ "and e.statusEstagio = :statusEstagio";
	private static final String selectPorIdAlunoFiltroStatusTermoCompromisso = "SELECT e FROM Estagio e "
			+ "INNER JOIN e.aluno a "
			+ "INNER JOIN e.termoDeCompromisso t "
			+ "WHERE a.id = :idAluno "
			+ "and t.statusTermo = :statusTermo";

	private static final String selectEstagioWithCustomFilters = "SELECT e FROM Estagio e "
			+ "INNER JOIN e.aluno a "
			+ "WHERE 1=1 ";

	private static final String selectEstagioAtivoOuEmAprovacaoOuEmRevisao = "SELECT e FROM Estagio e "
			+ "INNER JOIN e.aluno a "
			+ "INNER JOIN e.termoDeCompromisso t "
			+ "WHERE a.id = :idAluno "
			+ "and e.statusEstagio IN :statusEstagio";

	@Autowired
	private EstagioRepository estagioRepo;

	@PersistenceContext
	private EntityManager em;

	public List<Estagio> listarTodosEstagios() {

		return estagioRepo.findAll();
	}

	public Page<Estagio> listarEstagioPaginated(
			int page,
			Optional<String> grrAluno,
			Optional<String> nomeEmpresa,
			Optional<EnumTipoEstagio> tipoEstagio,
			Optional<EnumStatusEstagio> statusEstagio

	) {

		StringBuilder jpql = new StringBuilder(selectEstagioWithCustomFilters);

		// Configurar os parâmetros da consultas
		if (grrAluno.isPresent()) {
			jpql.append(" AND e.aluno.matricula LIKE :grrAluno");
		}
		if (nomeEmpresa.isPresent()) {
			jpql.append(" AND e.contratante.nome  LIKE :nomeEmpresa");
		}
		if (tipoEstagio.isPresent()) {
			jpql.append(" AND e.tipoEstagio = :tipoEstagio");
		}
		if (statusEstagio.isPresent()) {
			jpql.append(" AND e.statusEstagio = :statusEstagio");
		}

		TypedQuery<Estagio> query = em.createQuery(jpql.toString(), Estagio.class);

		// Configurar os parâmetros da consultas
		if (grrAluno.isPresent()) {
			query.setParameter("grrAluno", "%" + grrAluno.get() + "%");
		}
		if (nomeEmpresa.isPresent()) {
			query.setParameter("nomeEmpresa", "%" + nomeEmpresa.get() + "%");
		}
		if (tipoEstagio.isPresent()) {
			query.setParameter("tipoEstagio", tipoEstagio.get());
		}
		if (statusEstagio.isPresent()) {
			query.setParameter("statusEstagio", statusEstagio.get());
		}

		// Configurar a paginação
		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(page, 10);

		// Executar a consulta paginada
		List<Estagio> estagioPaginado = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(estagioPaginado, pageRequest, totalRegistros);
	}

	public Estagio novoEstagio(Estagio estagio) {
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmPreenchimento;
		estagio.setStatusEstagio(statusEstagio);
		return estagioRepo.save(estagio);
	}

	public Optional<Estagio> buscarEstagioPorId(long id) {
		return estagioRepo.findById(id);
	}

	public Estagio salvarEstagio(Estagio estagio) {
		return estagioRepo.save(estagio);
	}

	public Estagio atualizarEstagio(Estagio estagio) {
		return estagioRepo.save(estagio);
	}

	public void deletarEstagio(long id) {
		estagioRepo.deleteById(id);
	}

	public EstagioDTO toEstagioDTO(Estagio estagio) {
		EnumTipoTermoDeEstagio tipoTermoAditivo = EnumTipoTermoDeEstagio.TermoAditivo;
		EstagioDTO estagioDTO = new EstagioDTO();
		estagioDTO.setId(estagio.getId());
		estagioDTO.setTipoEstagio(estagio.getTipoEstagio());
		estagioDTO.setStatusEstagio(estagio.getStatusEstagio());
		estagioDTO.setEstagioUfpr(estagio.isEstagioUfpr());
		estagioDTO.setEstagioSeed(estagio.isEstagioSeed());
		estagioDTO.setAluno(estagio.getAluno());
		estagioDTO.setContratante(estagio.getContratante());
		estagioDTO.setSeguradora(estagio.getSeguradora());
		estagioDTO.setApolice(estagio.getApolice());
		estagioDTO.setAgenteIntegrador(estagio.getAgenteIntegrador());
		estagioDTO.setOrientador(estagio.getOrientador());
		estagioDTO.setPlanoDeAtividades(estagio.getPlanoDeAtividades());
		estagioDTO.setDataInicio(estagio.getDataInicio());
		estagioDTO.setDataTermino(estagio.getDataTermino());
		estagioDTO.setJornadaDiaria(estagio.getJornadaDiaria());
		estagioDTO.setJornadaSemanal(estagio.getJornadaSemanal());
		estagioDTO.setValorBolsa(estagio.getValorBolsa());
		estagioDTO.setValorTransporte(estagio.getValorTransporte());
		estagioDTO.setTermoDeCompromisso(
				estagio.getTermoDeCompromisso() == null ? 0 : estagio.getTermoDeCompromisso().getId());
		List<Long> termoAditivo = new ArrayList<Long>();
		if (estagio.getTermoAdivito() != null) {
			for (TermoDeEstagio t : estagio.getTermoAdivito()) {
				if (t.getTipoTermoDeEstagio() == tipoTermoAditivo) {
					termoAditivo.add(t.getId());
				}
			}
		} else {
			termoAditivo.add((long) 0);
		}
		estagioDTO.setTermoAdivito(termoAditivo);
		estagioDTO.setTermoDeRescisao(estagio.getTermoDeRescisao() == null ? 0 : estagio.getTermoDeRescisao().getId());
		List<Long> relatorioDeEstagio = new ArrayList<Long>();
		if (estagio.getRelatorioDeEstagio() != null) {
			for (RelatorioDeEstagio r : estagio.getRelatorioDeEstagio()) {
				relatorioDeEstagio.add(r.getId());
			}
		} else {
			relatorioDeEstagio.add((long) 0);
		}
		estagioDTO.setRelatorioDeEstagio(relatorioDeEstagio);
		estagioDTO
				.setFichaDeAvaliacao(estagio.getFichaDeAvaliacao() == null ? 0 : estagio.getFichaDeAvaliacao().getId());
		estagioDTO.setCertificadoDeEstagio(
				estagio.getCertificadoDeEstagio() == null ? 0 : estagio.getCertificadoDeEstagio().getId());
		estagioDTO.setDataCriacao(estagio.getDataCriacao());
		return estagioDTO;
	}

	public Estagio definirTipoEstagio(Estagio estagio, EnumTipoEstagio tipoEstagio) {
		estagio.setTipoEstagio(tipoEstagio);
		return estagioRepo.save(estagio);
	}

	public Estagio definirEstagioUfpr(Estagio estagio, Boolean estagioUfpr) {
		estagio.setEstagioUfpr(estagioUfpr);
		return estagioRepo.save(estagio);
	}

	public Estagio definirEstagioSeed(Estagio estagio, Boolean estagioSeed) {
		estagio.setEstagioSeed(estagioSeed);
		return estagioRepo.save(estagio);
	}

	public List<Estagio> buscarEstagioEmPreenchimentoPorAluno(Aluno aluno) {
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmPreenchimento;
		List<Estagio> estagio = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);
		return estagio;
	}

	/**
	 * Neste caso, estágio em progresso é um estágio já aprovado, mas ainda não
	 * iniciado ou um estágio já aprovado e já iniciado ou seja, um estágio em
	 * andamento.
	 **/
	public List<Estagio> buscarEstagioEmProgressoPorAluno(Aluno aluno) {
		// Primeiro busca por estágio aprovado.
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.Aprovado;
		List<Estagio> estagioAprovado = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);

		// Depois busca por estágio iniciado.
		statusEstagio = EnumStatusEstagio.Iniciado;
		List<Estagio> estagioIniciado = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);

		// Por fim, concatena as duas listas em uma única lista.
		List<Estagio> estagio = new ArrayList<>();
		estagio.addAll(estagioAprovado);
		estagio.addAll(estagioIniciado);
		return estagio;
	}

	public List<Estagio> buscarEstagioEmAprovacaoPorAluno(Aluno aluno) {
		EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmAprovacao;
		List<Estagio> estagio = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);
		return estagio;
	}

	public List<Estagio> buscarEstagioPorStatusEstagio(Aluno aluno, String statusEstagioString) {
		EnumStatusEstagio statusEstagio;
		statusEstagioString = statusEstagioString.toUpperCase();
		switch (statusEstagioString) {
			case "EMPREENCHIMENTO":
				statusEstagio = EnumStatusEstagio.EmPreenchimento;
				break;
			case "EMAPROVACAO":
				statusEstagio = EnumStatusEstagio.EmAprovacao;
				break;
			case "APROVADO":
				statusEstagio = EnumStatusEstagio.Aprovado;
				break;
			case "INICIADO":
				statusEstagio = EnumStatusEstagio.Iniciado;
				break;
			case "CONCLUIDO":
				statusEstagio = EnumStatusEstagio.Concluido;
				break;
			case "CANCELADO":
				statusEstagio = EnumStatusEstagio.Cancelado;
				break;
			case "RESCINDIDO":
				statusEstagio = EnumStatusEstagio.Rescindido;
				break;
			case "REPROVADO":
				statusEstagio = EnumStatusEstagio.Reprovado;
				break;
			default:
				return null;
		}
		List<Estagio> estagio = estagioRepo.findByStatusEstagioAndAluno(statusEstagio, aluno);
		return estagio;
	}

	public List<Estagio> buscarEstagioPorStatusTermoCompromisso(Aluno aluno, String statusTermoString) {
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
		TypedQuery<Estagio> query = em.createQuery(selectPorIdAlunoFiltroStatusTermoCompromisso, Estagio.class);

		query.setParameter("idAluno", aluno.getId());
		query.setParameter("statusTermo", statusTermo);

		return query.getResultList();
	}

	public List<Estagio> listarTodosEstagiosPendenteAprovacaoCoe() {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<Estagio> listarEstagiosPorIdOrientador(long idOrientador,
			int page,
			Optional<String> grrAluno,
			Optional<EnumStatusEstagio> statusEstagio) {

		StringBuilder queryString = new StringBuilder(selectEstagioWithCustomFilters);

		queryString.append(" AND e.orientador.id = :idOrientador");

		if (grrAluno.isPresent()) {
			queryString.append(" AND e.aluno.matricula LIKE :grrAluno");
		}

		if (statusEstagio.isPresent()) {
			queryString.append(" AND e.statusEstagio = :statusEstagio");
		}

		queryString.append(" ORDER BY e.id DESC ");

		TypedQuery<Estagio> query = em.createQuery(queryString.toString(), Estagio.class);

		query.setParameter("idOrientador", idOrientador);

		if (grrAluno.isPresent()) {
			query.setParameter("grrAluno", "%" + grrAluno.get() + "%");
		}

		if (statusEstagio.isPresent()) {
			query.setParameter("statusEstagio", statusEstagio.get());
		}

		// Configurar a paginação
		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(page, 10);

		// Executar a consulta paginada
		List<Estagio> termosPaginados = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(termosPaginados, pageRequest, totalRegistros);

	}

	public List<Estagio> listarEstagiosPendenteAprovacaoPorIdOrientador(long idOrientador) {

		EnumStatusEstagio statusEstagio = EnumStatusEstagio.EmAprovacao;

		TypedQuery<Estagio> query = em.createQuery(selectPorIdOrientadorFiltroStatusEstagio, Estagio.class);

		query.setParameter("idOrientador", idOrientador);
		query.setParameter("statusEstagio", statusEstagio);

		return query.getResultList();
	}

	public List<Estagio> listarEstagiosIndeferidosPorIdOrientador(long idOrientador) {

		EnumStatusEstagio statusEstagio = EnumStatusEstagio.Reprovado;

		TypedQuery<Estagio> query = em.createQuery(selectPorIdOrientadorFiltroStatusEstagio, Estagio.class);

		query.setParameter("idOrientador", idOrientador);
		query.setParameter("statusEstagio", statusEstagio);

		return query.getResultList();
	}

	public List<Estagio> buscarEstagioPorAluno(Aluno aluno) {
		List<Estagio> estagio = estagioRepo.findByAluno(aluno);
		return estagio;
	}

	public List<Estagio> buscarEstagioPorSeguradoraUfpr() {
		List<Estagio> estagio = estagioRepo.findBySeguradoraSeguradoraUfprIsTrue();

		return estagio;
	}

	public boolean listarEstagiosPorAgenteIntegrador(AgenteIntegrador agenteIntegrador) {

		List<Estagio> estagios = estagioRepo.findByAgenteIntegrador(agenteIntegrador);

		if (estagios.size() == 0)
			return false;

		return true;
	}

	public boolean listarEstagiosPorApolice(Apolice apolice) {

		List<Estagio> estagios = estagioRepo.findByApolice(apolice);

		if (estagios.size() == 0)
			return false;

		return true;
	}

	public boolean listarEstagiosPorContratante(Contratante contratante) {

		List<Estagio> estagios = estagioRepo.findByContratante(contratante);

		if (estagios.size() == 0)
			return false;

		return true;
	}

	public boolean listarEstagiosPorSeguradora(Seguradora seguradora) {

		List<Estagio> estagios = estagioRepo.findBySeguradora(seguradora);

		if (estagios.size() == 0)
			return false;

		return true;
	}

	public boolean verificarAlunoPodeCriarEstagio(Aluno aluno) {
		TypedQuery<Long> query = em.createQuery(selectEstagioAtivoOuEmAprovacaoOuEmRevisao, Long.class);
		query.setParameter("idAluno", aluno.getId());
		query.setParameter("statusEstagio",
				Arrays.asList(EnumStatusEstagio.EmPreenchimento, EnumStatusEstagio.EmAprovacao,
						EnumStatusEstagio.Aprovado, EnumStatusEstagio.Iniciado));

		List<Long> result = query.getResultList();

		if (result.size() > 0)
			return false;

		return true;
	}

}
