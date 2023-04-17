package br.ufpr.estagio.modulo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Estagio;

public interface EstagioRepository extends JpaRepository<Estagio, Long>{
	
	
	// consulta estágio com status EmPreenchimento com base no aluno_id
	//@Query("from Estagio where statusEstagio = :statusEstagio and Aluno = :aluno")
	//List<Estagio> findByStatusAndByAluno(@Param("statusEstagio") EnumStatusEstagio statusEstagio, @Param("aluno") Aluno aluno);

	List<Estagio> findByStatusEstagioAndAluno(EnumStatusEstagio statusEstagio, Aluno aluno);
	
}
