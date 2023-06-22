package br.ufpr.estagio.modulo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public interface TermoDeEstagioRepository extends JpaRepository<TermoDeEstagio, Long>, JpaSpecificationExecutor<TermoDeEstagio>{

	List<TermoDeEstagio> findByAgenteIntegrador(AgenteIntegrador agenteIntegrador);
	
}
