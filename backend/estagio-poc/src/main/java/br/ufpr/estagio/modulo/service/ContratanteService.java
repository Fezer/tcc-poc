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

import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.repository.ContratanteRepository;
import br.ufpr.estagio.modulo.repository.EnderecoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
@Transactional
public class ContratanteService {

	// Busca somente por pessoa jurídica
	private static final String selectContratanteWithOptionalFields = "SELECT c FROM Contratante c "
			+ " WHERE c.tipo = PessoaJuridica ";

	@Autowired
	private ContratanteRepository contratanteRepo;

	@Autowired
	private EnderecoRepository enderecoRepo;

	@PersistenceContext
	private EntityManager em;

	public Contratante criarContratante(Contratante contratante) {
		return contratanteRepo.save(contratante);
	}

	public Optional<Contratante> buscarPorId(Long id) {
		return contratanteRepo.findById(id);
	}

	public List<Contratante> listarContratantes() {
		EnumTipoContratante tipoContratante = EnumTipoContratante.PessoaJuridica;
		return contratanteRepo.findByTipo(tipoContratante);
	}

	public Page<Contratante> listarContratantesPaginated(
			int page,
			Optional<String> nome,
			Optional<String> cnpj) {

		StringBuilder jpql = new StringBuilder(selectContratanteWithOptionalFields);

		if (nome.isPresent()) {
			jpql.append(" AND c.nome LIKE :nome ");
		}

		if (cnpj.isPresent()) {
			jpql.append(" AND c.cnpj LIKE :cnpj ");
		}

		TypedQuery<Contratante> query = em.createQuery(jpql.toString(), Contratante.class);

		if (nome.isPresent()) {
			query.setParameter("nome", "%" + nome.get() + "%");
		}

		if (cnpj.isPresent()) {
			query.setParameter("cnpj", "%" + cnpj.get() + "%");
		}

		// Configurar a paginação
		int totalRegistros = query.getResultList().size();
		PageRequest pageRequest = PageRequest.of(page, 10);

		// Executar a consulta paginada
		List<Contratante> contratantePaginada = query
				.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.setMaxResults(pageRequest.getPageSize())
				.getResultList();

		return new PageImpl<>(contratantePaginada, pageRequest, totalRegistros);

	}

	public Contratante atualizarContratante(Contratante contratanteAtualizado) {
		Contratante contratanteExistente = buscarPorId(contratanteAtualizado.getId())
				.orElseThrow(() -> new NoSuchElementException("Contratante não encontrado para o ID informado"));

		contratanteExistente.setTipo(contratanteAtualizado.getTipo());
		contratanteExistente.setCnpj(contratanteAtualizado.getCnpj());
		contratanteExistente.setCpf(contratanteAtualizado.getCpf());
		contratanteExistente.setRepresentanteEmpresa(contratanteAtualizado.getRepresentanteEmpresa());
		contratanteExistente.setEstagio(contratanteAtualizado.getEstagio());

		return contratanteRepo.save(contratanteExistente);
	}

	public void excluirContratante(Contratante c) {
		Optional<Contratante> contratanteOptional = contratanteRepo.findById(c.getId());
		if (contratanteOptional.isPresent()) {
			Contratante contratante = contratanteOptional.get();
			contratanteRepo.delete(contratante);
		} else {
			throw new RuntimeException("Não foi encontrado um contratante com o ID informado.");
		}

	}

	public List<Contratante> buscarPorNome(String nomeContratante) {
		return contratanteRepo.findByNome(nomeContratante);
	}

	public List<Contratante> buscarPorNomeContendo(String nomeContratante) {
		EnumTipoContratante tipoContratante = EnumTipoContratante.PessoaJuridica;
		return contratanteRepo.findByNomeContainingIgnoreCaseAndTipo(nomeContratante, tipoContratante);
	}

	public List<Contratante> buscarContratantePorNomeComecandoPor(String nomeContratante) {
		return contratanteRepo.findByNomeStartsWithIgnoreCase(nomeContratante);
	}

	public Contratante criarEnderecoContratante(Contratante contratante, Endereco endereco) {
		endereco = enderecoRepo.save(endereco);
		contratante.setEndereco(endereco);
		return contratanteRepo.save(contratante);
	}
}
