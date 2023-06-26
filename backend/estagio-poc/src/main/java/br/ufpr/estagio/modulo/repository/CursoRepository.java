package br.ufpr.estagio.modulo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	Optional<Curso> findByNome(String nome);

	Curso findByIdPrograma(String idPrograma);

	Optional<Curso> getById(Curso id);

	Optional<Curso> findByIdCurso(Long idCurso);

}
