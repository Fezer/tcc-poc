package br.ufpr.estagio.modulo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.estagio.modulo.model.Contratante;

public interface ContratanteRepository extends JpaRepository<Contratante, Long> {

	Optional<Contratante> findByNome(String nome);

	List<Contratante> findByNomeContaining(String nomeContratante);

	List<Contratante> findByNomeContainingIgnoreCase(String nomeContratante);

	List<Contratante> findByNomeStartsWithIgnoreCase(String nomeContratante);
	
}
