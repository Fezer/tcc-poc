package br.ufpr.estagio.modulo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.dto.TermoDeRescisaoDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeRescisao;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.TermoDeRescisaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
@Transactional
public class TermoDeRescisaoService {

	private static final String selectTermoPorTipoEstagioEtapaFluxoPendenteCienciaCoe = "SELECT t FROM TermoDeRescisao t INNER JOIN t.estagio e "
			+ "WHERE e.tipoEstagio = :tipoEstagio "
			+ "AND t.etapaFluxo = :etapaFluxo "
			+ "AND t.cienciaCOE = :cienciaCoe";

	private static final String selectTermoPorEtapaFluxoPendenteCienciaOrientador = "SELECT t FROM TermoDeRescisao t "
			+ "INNER JOIN t.estagio e "
			+ "INNER JOIN e.orientador o "
			+ "WHERE o.id = :idOrientador "
			+ "AND t.etapaFluxo = :etapaFluxo "
			+ "AND t.cienciaOrientador = :cienciaOrientador";

	private static final String selectTermoPorEtapaFluxoPendenteCienciaCoordenacao = "SELECT t FROM TermoDeRescisao t INNER JOIN t.estagio e "
			+ "WHERE t.etapaFluxo = :etapaFluxo "
			+ "AND t.cienciaCoordenacao = :cienciaCoordenacao";

	private static final String selectTermoPorEtapaFluxoPendenteCienciaCoafe = "SELECT t FROM TermoDeRescisao t INNER JOIN t.estagio e "
			+ "WHERE t.etapaFluxo = :etapaFluxo "
			+ "AND t.cienciaCOAFE = :cienciaCoafe";

	private static final String selectTermosDeRescisaoPorAlunoPorEtapaFluxoPorCienciaCoafe = "SELECT t FROM TermoDeRescisao t "
			+ "INNER JOIN t.estagio e "
			+ "INNER JOIN e.aluno a "
			+ "WHERE t.etapaFluxo = :etapaFluxo "
			+ "AND t.cienciaCOAFE = :cienciaCoafe "
			+ "AND a.id = :idAluno";

	private static final String selectTermosDeRescisaoPorAluno = "SELECT t FROM TermoDeRescisao t "
			+ "INNER JOIN t.estagio e "
			+ "INNER JOIN e.aluno a "
			+ "WHERE a.id = :idAluno";

	private static final String selectTermosDeRescisaoPorAlunoEtapaCiencia = "SELECT t FROM TermoDeRescisao t "
			+ "INNER JOIN t.estagio e "
			+ "INNER JOIN e.aluno a "
			+ "WHERE t.etapaFluxo = :etapaFluxo "
			+ "AND t.cienciaCOAFE = :cienciaCoafe "
			+ "AND a.id = :idAluno";

	private static final String selectTermosDeRescisaoWithFilters = "SELECT t FROM TermoDeRescisao " +
			"t INNER JOIN t.estagio e " +
			"INNER JOIN e.aluno a " +
			"WHERE 1=1 ";

	@Autowired
	private TermoDeRescisaoRepository termoDeRescisaoRepo;

	@Autowired
	private EstagioRepository estagioRepo;

	@PersistenceContext
	private EntityManager em;

	public List<TermoDeRescisao> listarTodos() {
		return termoDeRescisaoRepo.findAll();
	}

	public Page<TermoDeRescisao> listarTodosPaginated(
			int page,
			Optional<String> grrAluno,
			Optional<EnumEtapaFluxo> etapaFluxo,
			Optional<Boolean> cienciaCOAFE,
			Optional<Boolean> cienciaCoordenacao,
			Optional<Boolean> cienciaOrientador,
			Optional<Boolean> cienciaCOE) {
		StringBuilder queryString = new StringBuilder(selectTermosDeRescisaoWithFilters);

		if (grrAluno.isPresent()) {
			queryString.append("AND a.matricula LIKE :grrAluno ");
		}

		if (etapaFluxo.isPresent()) {
			queryString.append("AND t.etapaFluxo = :etapaFluxo ");
		}

		if (cienciaCOAFE.isPresent()) {
			queryString.append("AND t.cienciaCOAFE = :cienciaCOAFE ");
		}

		if (cienciaCoordenacao.isPresent()) {
			queryString.append("AND t.cienciaCoordenacao = :cienciaCoordenacao ");
		}

		if (cienciaOrientador.isPresent()) {
			queryString.append("AND t.cienciaOrientador = :cienciaOrientador ");
		}

		if (cienciaCOE.isPresent()) {
			queryString.append("AND t.cienciaCOE = :cienciaCOE ");
		}

		TypedQuery<TermoDeRescisao> query = em.createQuery(queryString.toString(), TermoDeRescisao.class);

		if (grrAluno.isPresent()) {
			query.setParameter("grrAluno", "%" + grrAluno.get() + "%");
		}

		if (etapaFluxo.isPresent()) {
			query.setParameter("etapaFluxo", etapaFluxo.get());
		}

		if (cienciaCOAFE.isPresent()) {
			query.setParameter("cienciaCOAFE", cienciaCOAFE.get());
		}

		if (cienciaCoordenacao.isPresent()) {
			query.setParameter("cienciaCoordenacao", cienciaCoordenacao.get());
		}

		if (cienciaOrientador.isPresent()) {
			query.setParameter("cienciaOrientador", cienciaOrientador.get());
		}

		if (cienciaCOE.isPresent()) {
			query.setParameter("cienciaCOE", cienciaCOE.get());
		}

		query.setFirstResult(page * 10);

		query.setMaxResults(10);

		return new PageImpl<>(query.getResultList());

	}

	public TermoDeRescisao novo(TermoDeRescisao termoDeRescisao) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
		termoDeRescisao.setEtapaFluxo(etapaFluxo);
		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public Optional<TermoDeRescisao> buscarPorId(long id) {
		return termoDeRescisaoRepo.findById(id);
	}

	public TermoDeRescisao salvar(TermoDeRescisao termoDeRescisao) {
		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public TermoDeRescisao atualizar(TermoDeRescisao termoDeRescisao) {
		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public void deletar(long id) {
		termoDeRescisaoRepo.deleteById(id);
	}

	public TermoDeRescisao novoTermoDeRescisao(Estagio estagio) {
		/**
		 * Um estágio pode ter nenhum ou apenas uma Termo de Rescisão associado,
		 * desta forma, caso o Estágio já possua um Termo de Rescisão,
		 * retorna-se o termo de Rescisão já existente. A trava no fluxo para informar
		 * ao frontend caso o Estágio já possua um Termo de Rescisão associado
		 * é feita pela controller.
		 */
		if (estagio.getTermoDeRescisao() != null) {
			return estagio.getTermoDeRescisao();
		}

		TermoDeRescisao termoDeRescisao = new TermoDeRescisao();
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
		termoDeRescisao.setEtapaFluxo(etapaFluxo);
		termoDeRescisao.setEstagio(estagio);

		estagio.setTermoDeRescisao(termoDeRescisao);
		estagioRepo.save(estagio);

		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public void cancelarTermoDeRescisao(TermoDeRescisao termoDeRescisao) {
		/**
		 * Somente é possível cancelar um Termo de Rescisão caso a COAFE
		 * ainda não tenha dado ciência no Termo de Rescisão. Isso porque
		 * quando a COAFE dá ciência no Termo de Rescisão isso significada
		 * que o fluxo de ciência chegou ao fim. Ou seja, não é possível
		 * solicitar o cancelamento de um Termo de Rescisão cujo fluxo
		 * já chegou ao fim.
		 */
		if (termoDeRescisao.isCienciaCOAFE()) {
			return;
		}
		Estagio estagio = termoDeRescisao.getEstagio();
		termoDeRescisao.setEstagio(null);
		if (estagio != null) {
			estagio.setTermoDeRescisao(null);
			estagioRepo.save(estagio);
		}
		termoDeRescisaoRepo.delete(termoDeRescisao);
		return;
	}

	public TermoDeRescisao atualizarDados(TermoDeRescisao termofind, TermoDeRescisaoDTO termo) {
		TermoDeRescisao termoAtualizado = termofind;
		termoAtualizado.setDataTermino(
				termo.getDataTermino() == null ? termoAtualizado.getDataTermino() : termo.getDataTermino());
		termoAtualizado.setPeriodoTotalRecesso(termo.getPeriodoTotalRecesso());
		return termoDeRescisaoRepo.save(termoAtualizado);
	}

	public List<TermoDeRescisao> listarTermosDeRescisaoPendenteCienciaCoe() {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Ciencia;
		EnumTipoEstagio tipoEstagio = EnumTipoEstagio.NaoObrigatorio;
		boolean cienciaCoe = false;

		TypedQuery<TermoDeRescisao> query = em.createQuery(selectTermoPorTipoEstagioEtapaFluxoPendenteCienciaCoe,
				TermoDeRescisao.class);
		query.setParameter("tipoEstagio", tipoEstagio);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("cienciaCoe", cienciaCoe);
		return query.getResultList();
	}

	public List<TermoDeRescisao> listarTermosDeRescisaoPendenteCienciaOrientador(long idOrientador) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Ciencia;
		boolean cienciaOrientador = false;

		TypedQuery<TermoDeRescisao> query = em.createQuery(selectTermoPorEtapaFluxoPendenteCienciaOrientador,
				TermoDeRescisao.class);
		query.setParameter("idOrientador", idOrientador);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("cienciaOrientador", cienciaOrientador);
		return query.getResultList();
	}

	public List<TermoDeRescisao> listarTermosDeRescisaoPendenteCienciaCoordenacao() {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Ciencia;
		boolean cienciaCoordenacao = false;

		TypedQuery<TermoDeRescisao> query = em.createQuery(selectTermoPorEtapaFluxoPendenteCienciaCoordenacao,
				TermoDeRescisao.class);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("cienciaCoordenacao", cienciaCoordenacao);
		return query.getResultList();
	}

	public List<TermoDeRescisao> listarTermosDeRescisaoPendenteCienciaCoafe() {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
		boolean cienciaCoafe = false;

		TypedQuery<TermoDeRescisao> query = em.createQuery(selectTermoPorEtapaFluxoPendenteCienciaCoafe,
				TermoDeRescisao.class);
		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("cienciaCoafe", cienciaCoafe);
		return query.getResultList();
	}

	public List<TermoDeRescisao> listarTermoDeRescisaoEtapaAlunoPorAluno(Aluno aluno) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
		boolean cienciaCoafe = false;

		TypedQuery<TermoDeRescisao> query = em.createQuery(selectTermosDeRescisaoPorAlunoPorEtapaFluxoPorCienciaCoafe,
				TermoDeRescisao.class);

		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("cienciaCoafe", cienciaCoafe);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public List<TermoDeRescisao> listarTermosDeRescisaoPorAluno(Aluno aluno) {

		TypedQuery<TermoDeRescisao> query = em.createQuery(selectTermosDeRescisaoPorAluno, TermoDeRescisao.class);

		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public List<TermoDeRescisao> listarTermosDeRescisaoPorAlunoEtapaCiencia(Aluno aluno) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Ciencia;
		boolean cienciaCoafe = false;

		TypedQuery<TermoDeRescisao> query = em.createQuery(selectTermosDeRescisaoPorAlunoEtapaCiencia,
				TermoDeRescisao.class);

		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("cienciaCoafe", cienciaCoafe);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public List<TermoDeRescisao> listarTermosDeRescisaoPorAlunoProcessoFinalizado(Aluno aluno) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
		boolean cienciaCoafe = true;

		TypedQuery<TermoDeRescisao> query = em.createQuery(selectTermosDeRescisaoPorAlunoPorEtapaFluxoPorCienciaCoafe,
				TermoDeRescisao.class);

		query.setParameter("etapaFluxo", etapaFluxo);
		query.setParameter("cienciaCoafe", cienciaCoafe);
		query.setParameter("idAluno", aluno.getId());

		return query.getResultList();
	}

	public TermoDeRescisao solicitarCienciaTermoDeRescisaoAluno(TermoDeRescisao termoDeRescisao) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Ciencia;
		termoDeRescisao.setEtapaFluxo(etapaFluxo);
		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public TermoDeRescisao darCienciaTermoDeRescisaoCoe(TermoDeRescisao termoDeRescisao) {
		termoDeRescisao.setCienciaCOE(true);
		/**
		 * O fluxo de dar ciência ao termo de rescisão ocorre de forma paralela entre os
		 * atores
		 * COE, Coordenação e Orientador, desta forma, caso todos os 3 atores já tenham
		 * dado ciência,
		 * no caso de estágio não obrigatório, o termo deve ser encaminhado para a
		 * ciência final
		 * da COAFE. Caso seja um estágio obrigatório, apenas a ciência do Orientador e
		 * da
		 * Coordenação bastam, uma vez que a COE não participa do fluxo de Estágios
		 * obrigatórios.
		 */
		if (termoDeRescisao.getEstagio() != null) {
			if (termoDeRescisao.getEstagio().getTipoEstagio() != null) {
				if (termoDeRescisao.getEstagio().getTipoEstagio() == EnumTipoEstagio.NaoObrigatorio) {
					if (termoDeRescisao.isCienciaCOE() && termoDeRescisao.isCienciaCoordenacao()
							&& termoDeRescisao.isCienciaOrientador()) {
						EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
						termoDeRescisao.setEtapaFluxo(etapaFluxo);
					}
				} else {
					if (termoDeRescisao.isCienciaCoordenacao() && termoDeRescisao.isCienciaOrientador()) {
						EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
						termoDeRescisao.setEtapaFluxo(etapaFluxo);
					}
				}
			}
		}
		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public TermoDeRescisao darCienciaTermoDeRescisaoCoordenacao(TermoDeRescisao termoDeRescisao) {
		termoDeRescisao.setCienciaCoordenacao(true);
		/**
		 * O fluxo de dar ciência ao termo de rescisão ocorre de forma paralela entre os
		 * atores
		 * COE, Coordenação e Orientador, desta forma, caso todos os 3 atores já tenham
		 * dado ciência,
		 * no caso de estágio não obrigatório, o termo deve ser encaminhado para a
		 * ciência final
		 * da COAFE. Caso seja um estágio obrigatório, apenas a ciência do Orientador e
		 * da
		 * Coordenação bastam, uma vez que a COE não participa do fluxo de Estágios
		 * obrigatórios.
		 */
		if (termoDeRescisao.getEstagio() != null) {
			if (termoDeRescisao.getEstagio().getTipoEstagio() != null) {
				if (termoDeRescisao.getEstagio().getTipoEstagio() == EnumTipoEstagio.NaoObrigatorio) {
					if (termoDeRescisao.isCienciaCOE() && termoDeRescisao.isCienciaCoordenacao()
							&& termoDeRescisao.isCienciaOrientador()) {
						EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
						termoDeRescisao.setEtapaFluxo(etapaFluxo);
					}
				} else {
					if (termoDeRescisao.isCienciaCoordenacao() && termoDeRescisao.isCienciaOrientador()) {
						EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
						termoDeRescisao.setEtapaFluxo(etapaFluxo);
					}
				}
			}
		}
		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public TermoDeRescisao darCienciaTermoDeRescisaoOrientador(TermoDeRescisao termoDeRescisao) {
		termoDeRescisao.setCienciaOrientador(true);
		/**
		 * O fluxo de dar ciência ao termo de rescisão ocorre de forma paralela entre os
		 * atores
		 * COE, Coordenação e Orientador, desta forma, caso todos os 3 atores já tenham
		 * dado ciência,
		 * no caso de estágio não obrigatório, o termo deve ser encaminhado para a
		 * ciência final
		 * da COAFE. Caso seja um estágio obrigatório, apenas a ciência do Orientador e
		 * da
		 * Coordenação bastam, uma vez que a COE não participa do fluxo de Estágios
		 * obrigatórios.
		 */
		if (termoDeRescisao.getEstagio() != null) {
			if (termoDeRescisao.getEstagio().getTipoEstagio() != null) {
				if (termoDeRescisao.getEstagio().getTipoEstagio() == EnumTipoEstagio.NaoObrigatorio) {
					if (termoDeRescisao.isCienciaCOE() && termoDeRescisao.isCienciaCoordenacao()
							&& termoDeRescisao.isCienciaOrientador()) {
						EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
						termoDeRescisao.setEtapaFluxo(etapaFluxo);
					}
				} else {
					if (termoDeRescisao.isCienciaCoordenacao() && termoDeRescisao.isCienciaOrientador()) {
						EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COAFE;
						termoDeRescisao.setEtapaFluxo(etapaFluxo);
					}
				}
			}
		}
		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public TermoDeRescisao darCienciaTermoDeRescisaoCoafe(TermoDeRescisao termoDeRescisao) {
		/**
		 * Quando a COAFE dá ciência no Termo de Recisão o fluxo se encerra.
		 * A forma de sinalizar que o fluxo encerrou é mudando o denifindo a etapaFluxo
		 * como Aluno.
		 * Além disto, uma vez que COAFE dá ciência no Termo de Rescisão a data de
		 * término
		 * do Estágio é atualizada conforme data de término informada no Termo de
		 * Rescisão.
		 */
		termoDeRescisao.setCienciaCOAFE(true);
		termoDeRescisao.setEtapaFluxo(EnumEtapaFluxo.Aluno);
		termoDeRescisao.getEstagio().setStatusEstagio(EnumStatusEstagio.Rescindido);
		if (termoDeRescisao.getEstagio() != null) {
			termoDeRescisao.getEstagio().setDataTermino(termoDeRescisao.getDataTermino());
			estagioRepo.save(termoDeRescisao.getEstagio());
		}
		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

	public TermoDeRescisao uploadTermoDeRescisao(TermoDeRescisao termoDeRescisao, String nomeArquivo) {
		termoDeRescisao.setUpload(true);

		List<String> listaAux = new ArrayList<>();
		listaAux.add(nomeArquivo);

		termoDeRescisao.setArquivos(listaAux);

		return termoDeRescisaoRepo.save(termoDeRescisao);
	}

}
