package br.ufpr.estagio.modulo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.Coordenador;

public interface CoordenadorRepository extends JpaRepository<Coordenador, Long>{

	Optional<Coordenador> findByNome(String nome);

}
