package br.ufpr.estagio.modulo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.repository.SeguradoraRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
@Transactional
public class SeguradoraService {

	private static final String selectSeguradoraWithFilters = "SELECT s FROM Seguradora s "
			+ " WHERE 1=1 ";

	@Autowired
	private SeguradoraRepository seguradoraRepository;

	@PersistenceContext
	private EntityManager em;

	public Seguradora criarSeguradora(Seguradora seguradora) {
		return seguradoraRepository.save(seguradora);
	}

	public Optional<Seguradora> buscarSeguradoraPorId(long id) {
		return seguradoraRepository.findById(id);
	}

	public List<Seguradora> listarSeguradoras() {
		return seguradoraRepository.findAll();
	}

	public Page<Seguradora> listarSeguradorasPaginated(
			int page,
			Optional<String> nome,
			Optional<Boolean> ativo,
			Optional<Boolean> seguradoraUFPR) {

		StringBuilder queryString = new StringBuilder(selectSeguradoraWithFilters);

		if (nome.isPresent()) {
			queryString.append(" AND s.nome LIKE :nome");
		}
		if (ativo.isPresent()) {
			queryString.append(" AND s.isAtiva = :ativo");
		}
		if (seguradoraUFPR.isPresent()) {
			queryString.append(" AND s.seguradoraUfpr = :seguradoraUFPR");
		}

		TypedQuery<Seguradora> query = em.createQuery(queryString.toString(), Seguradora.class);

		if (nome.isPresent()) {
			query.setParameter("nome", "%" + nome.get() + "%");
		}
		if (ativo.isPresent()) {
			query.setParameter("ativo", ativo.get());
		}
		if (seguradoraUFPR.isPresent()) {
			query.setParameter("seguradoraUFPR", seguradoraUFPR.get());
		}

		// Configurar a paginação
		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(page, 10);

		// Executar a consulta paginada
		List<Seguradora> seguradoraPaginada = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(seguradoraPaginada, pageRequest, totalRegistros);

	}

	public Seguradora atualizarSeguradora(Seguradora seguradoraAtualizada) {
		Seguradora seguradoraExistente = buscarSeguradoraPorId(seguradoraAtualizada.getId())
				.orElseThrow(() -> new NoSuchElementException("Seguradora não encontrada para o ID informado"));

		seguradoraExistente.setNome(seguradoraAtualizada.getNome());
		seguradoraExistente.setApolice(seguradoraAtualizada.getApolice());
		seguradoraExistente.setTermoDeEstagio(seguradoraAtualizada.getTermoDeEstagio());
		seguradoraExistente.setEstagio(seguradoraAtualizada.getEstagio());
		seguradoraExistente.setAtiva(seguradoraAtualizada.isAtiva());

		return seguradoraRepository.save(seguradoraExistente);
	}

	public Seguradora ativarDesativarSeguradora(Seguradora seguradoraAtualizada) {
		Seguradora seguradoraExistente = buscarSeguradoraPorId(seguradoraAtualizada.getId())
				.orElseThrow(() -> new NoSuchElementException("Seguradora não encontrada para o ID informado"));

		if (seguradoraExistente.isAtiva() == true && seguradoraAtualizada.isAtiva() == true) {
			seguradoraExistente.setError("Seguradora já está ativa.");
			return seguradoraExistente;
		}

		if (seguradoraExistente.isAtiva() == false && seguradoraAtualizada.isAtiva() == false) {
			seguradoraExistente.setError("Seguradora já está inativa.");
			return seguradoraExistente;
		}

		seguradoraExistente.setError(null);
		seguradoraExistente.setAtiva(seguradoraAtualizada.isAtiva());

		return seguradoraRepository.save(seguradoraExistente);
	}

	public void excluirSeguradora(Seguradora seguradora) {
		Optional<Seguradora> seguradoraOptional = seguradoraRepository.findById((long) seguradora.getId());
		if (seguradoraOptional.isPresent()) {
			Seguradora seguradoraExistente = seguradoraOptional.get();
			seguradoraRepository.delete(seguradoraExistente);
		} else {
			throw new RuntimeException("Não foi encontrada uma seguradora com o ID informado.");

		}
	}

}
