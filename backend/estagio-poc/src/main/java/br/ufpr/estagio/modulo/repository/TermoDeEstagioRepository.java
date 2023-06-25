package br.ufpr.estagio.modulo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public interface TermoDeEstagioRepository extends JpaRepository<TermoDeEstagio, Long>, JpaSpecificationExecutor<TermoDeEstagio>{

	List<TermoDeEstagio> findByAgenteIntegrador(AgenteIntegrador agenteIntegrador);
	
	List<TermoDeEstagio> findByApolice(Apolice apolice);
	
	List<TermoDeEstagio> findByContratante(Contratante contratante);
	
	List<TermoDeEstagio> findBySeguradora(Seguradora seguradora);

}
