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

import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.repository.AgenteIntegradorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
@Transactional
public class AgenteIntegradorService {

    // Busca somente por pessoa jurídica
    private static final String selectAgenteIntegradorWithFilters = "SELECT a FROM AgenteIntegrador a "
            + " WHERE 1=1 ";

    @Autowired
    private AgenteIntegradorRepository agenteIntegradorRepository;

    @PersistenceContext
    private EntityManager em;

    public AgenteIntegrador criarAgenteIntegrador(AgenteIntegrador agenteIntegrador) {
        return agenteIntegradorRepository.save(agenteIntegrador);
    }

    public Optional<AgenteIntegrador> buscarPorId(Integer id) {
        return agenteIntegradorRepository.findById(id);
    }

    public Optional<AgenteIntegrador> buscarPorId(long id) {
        return agenteIntegradorRepository.findById((int) id);
    }

    public List<AgenteIntegrador> listarAgentesIntegradores() {
        return agenteIntegradorRepository.findAll();
    }

    public Page<AgenteIntegrador> listarAgentesPaginated(
            int page,
            Optional<String> nome,
            Optional<String> cnpj) {

        StringBuilder queryString = new StringBuilder(selectAgenteIntegradorWithFilters);

        if (nome.isPresent()) {
            queryString.append(" AND a.nome LIKE :nome ");
        }

        if (cnpj.isPresent()) {
            queryString.append(" AND a.cnpj LIKE :cnpj ");
        }

        TypedQuery<AgenteIntegrador> query = em.createQuery(queryString.toString(), AgenteIntegrador.class);

        if (nome.isPresent()) {
            query.setParameter("nome", "%" + nome.get() + "%");
        }

        if (cnpj.isPresent()) {
            query.setParameter("cnpj", "%" + cnpj.get() + "%");
        }

        int totalRegistros = query.getResultList().size();
        PageRequest pageRequest = PageRequest.of(page, 10);

        // Executar a consulta paginada
        List<AgenteIntegrador> agentePaginado = query
                .setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();

        return new PageImpl<>(agentePaginado, pageRequest, totalRegistros);

    }

    public AgenteIntegrador atualizarAgenteIntegrador(AgenteIntegrador agenteIntegradorAtualizado) {
        AgenteIntegrador agenteIntegradorExistente = buscarPorId(agenteIntegradorAtualizado.getId())
                .orElseThrow(() -> new NoSuchElementException("Agente integrador não encontrado para o ID informado"));

        agenteIntegradorExistente.setCnpj(agenteIntegradorAtualizado.getCnpj());
        agenteIntegradorExistente.setNome(agenteIntegradorAtualizado.getNome());
        agenteIntegradorExistente.setTelefone(agenteIntegradorAtualizado.getTelefone());
        agenteIntegradorExistente.setConvenio(agenteIntegradorAtualizado.getConvenio());
        agenteIntegradorExistente.setTermoDeEstagio(agenteIntegradorAtualizado.getTermoDeEstagio());
        agenteIntegradorExistente.setEstagio(agenteIntegradorAtualizado.getEstagio());

        return agenteIntegradorRepository.save(agenteIntegradorExistente);
    }

    public void excluirAgenteIntegrador(AgenteIntegrador agente) {
        Optional<AgenteIntegrador> agenteIntegradorOptional = agenteIntegradorRepository.findById((int) agente.getId());
        if (agenteIntegradorOptional.isPresent()) {
            AgenteIntegrador agenteIntegrador = agenteIntegradorOptional.get();
            agenteIntegradorRepository.delete(agenteIntegrador);
        } else {
            throw new RuntimeException("Não foi encontrado um agente integrador com o ID informado.");
        }
    }
}
