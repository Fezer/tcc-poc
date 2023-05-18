package br.ufpr.estagio.modulo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Estagio;

public interface EstagioRepository extends JpaRepository<Estagio, Long>{
	
	List<Estagio> findByStatusEstagioAndAluno(EnumStatusEstagio statusEstagio, Aluno aluno);

	List<Estagio> findByAluno(Aluno aluno);
	
}
