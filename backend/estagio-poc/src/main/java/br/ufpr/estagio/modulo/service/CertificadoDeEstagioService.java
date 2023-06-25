package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import br.ufpr.estagio.modulo.dto.JustificativaDTO;
import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumParecerAprovadores;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;
import br.ufpr.estagio.modulo.repository.EstagioRepository;
import br.ufpr.estagio.modulo.repository.CertificadoDeEstagioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
@Transactional
public class CertificadoDeEstagioService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CertificadoDeEstagioRepository certificadoRepo;

	@Autowired
	private EstagioRepository estagioRepo;

	private static final String selectPorIdOrientador = "SELECT c FROM CertificadoDeEstagio c "
			+ "INNER JOIN c.estagio e "
			+ "INNER JOIN e.orientador o "
			+ "WHERE o.id = :idOrientador";

	private static final String selectCertificadoWithFilters = "SELECT c FROM CertificadoDeEstagio c "
			+ "INNER JOIN c.estagio e "
			+ "INNER JOIN e.aluno a "
			+ "WHERE 1=1 ";

	public List<CertificadoDeEstagio> listarTodosCertificadosDeEstagio() {
		return certificadoRepo.findAll();
	}

	public Page<CertificadoDeEstagio> listarCertificadoPaginated(
			int page,
			Optional<String> grrAluno,
			Optional<EnumEtapaFluxo> etapaFluxo) {

		StringBuilder sb = new StringBuilder(selectCertificadoWithFilters);

		if (grrAluno.isPresent()) {
			sb.append("AND a.matricula LIKE :grrAluno ");
		}

		if (etapaFluxo.isPresent()) {
			sb.append("AND c.etapaFluxo = :etapaFluxo ");
		}

		TypedQuery<CertificadoDeEstagio> query = em.createQuery(sb.toString(), CertificadoDeEstagio.class);

		if (grrAluno.isPresent()) {
			query.setParameter("grrAluno", "%" + grrAluno.get() + "%");
		}

		if (etapaFluxo.isPresent()) {
			query.setParameter("etapaFluxo", etapaFluxo.get());
		}

		query.setFirstResult(page * 10);

		query.setMaxResults(10);

		return new PageImpl<>(query.getResultList());
	}

	public CertificadoDeEstagio novoCertificadoDeEstagio(CertificadoDeEstagio relatorioDeEstagio) {
		return certificadoRepo.save(relatorioDeEstagio);
	}

	public Optional<CertificadoDeEstagio> buscarCertificadoDeEstagioPorId(long id) {
		return certificadoRepo.findById(id);
	}

	public CertificadoDeEstagio salvarCertificadoDeEstagio(CertificadoDeEstagio certificadoDeEstagio) {
		return certificadoRepo.save(certificadoDeEstagio);
	}

	public CertificadoDeEstagio criarCertificadoDeEstagio(Estagio estagio) {

		CertificadoDeEstagio certificadoDeEstagio = new CertificadoDeEstagio();
		certificadoDeEstagio.setEstagio(estagio);

		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COE;

		certificadoDeEstagio.setEtapaFluxo(etapaFluxo);

		estagio.setCertificadoDeEstagio(certificadoDeEstagio);

		estagioRepo.save(estagio);
		certificadoRepo.save(certificadoDeEstagio);

		return certificadoDeEstagio;
	}

	public void deletarCertificadoDeEstagio(CertificadoDeEstagio certificadoDeEstagio) {

		Estagio estagio = certificadoDeEstagio.getEstagio();
		estagio.setCertificadoDeEstagio(null);

		certificadoDeEstagio.setEstagio(null);

		estagioRepo.save(estagio);
		certificadoRepo.delete(certificadoDeEstagio);

		return;
	}

	public List<CertificadoDeEstagio> listarCertificadosPendentesAprovacaoCoe() {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.COE;
		return certificadoRepo.findByEtapaFluxo(etapaFluxo);
	}

	public CertificadoDeEstagio aprovarCertificadoDeEstagioCoe(CertificadoDeEstagio certificado) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
		EnumParecerAprovadores aprovacaoCoe = EnumParecerAprovadores.Aprovado;
		certificado.setEtapaFluxo(etapaFluxo);
		certificado.setParecerCOE(aprovacaoCoe);
		return certificadoRepo.save(certificado);
	}

	public CertificadoDeEstagio reprovarCertificadoDeEstagioCoe(CertificadoDeEstagio certificado,
			JustificativaDTO justificativa) {
		EnumEtapaFluxo etapaFluxo = EnumEtapaFluxo.Aluno;
		EnumParecerAprovadores aprovacaoCoe = EnumParecerAprovadores.Reprovado;
		certificado.setEtapaFluxo(etapaFluxo);
		certificado.setParecerCOE(aprovacaoCoe);
		certificado.setMotivoReprovacao(justificativa.getJustificativa());
		return certificadoRepo.save(certificado);
	}

	public List<CertificadoDeEstagio> listarCertificadosPorIdOrientador(long idOrientador) {

		TypedQuery<CertificadoDeEstagio> query = em.createQuery(selectPorIdOrientador, CertificadoDeEstagio.class);

		query.setParameter("idOrientador", idOrientador);

		return query.getResultList();
	}

	public CertificadoDeEstagio uploadCertificadoDeEstagio(CertificadoDeEstagio certificado) {
		certificado.setUpload(true);

		return certificadoRepo.save(certificado);
	}

}
