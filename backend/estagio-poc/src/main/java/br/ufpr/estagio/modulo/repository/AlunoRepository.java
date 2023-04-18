package br.ufpr.estagio.modulo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long>{

	Optional<Aluno> findByMatricula(String matricula);

}
