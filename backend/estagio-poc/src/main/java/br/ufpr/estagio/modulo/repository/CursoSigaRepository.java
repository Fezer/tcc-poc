package br.ufpr.estagio.modulo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.CursoSiga;

public interface CursoSigaRepository extends JpaRepository<CursoSiga, Long>{

	Optional<CursoSiga> findByNome(String nome);

}
