package br.ufpr.estagio.modulo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.Orientador;

public interface OrientadorRepository extends JpaRepository<Orientador, Long>{

	Optional<Orientador> findByNome(String nome);

}
