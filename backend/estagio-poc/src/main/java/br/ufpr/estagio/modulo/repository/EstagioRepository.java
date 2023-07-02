package br.ufpr.estagio.modulo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Seguradora;

public interface EstagioRepository extends JpaRepository<Estagio, Long>{
	
	List<Estagio> findByStatusEstagioAndAluno(EnumStatusEstagio statusEstagio, Aluno aluno);

	List<Estagio> findByAluno(Aluno aluno);

	List<Estagio> findBySeguradoraSeguradoraUfprIsTrue();

	List<Estagio> findByAgenteIntegrador(AgenteIntegrador agenteIntegrador);
	
	List<Estagio> findByApolice(Apolice apolice);
	
	List<Estagio> findByContratante(Contratante contratante);

	List<Estagio> findBySeguradora(Seguradora seguradora);

	List<Estagio> findByConvenio(Convenio convenio);

	
}
