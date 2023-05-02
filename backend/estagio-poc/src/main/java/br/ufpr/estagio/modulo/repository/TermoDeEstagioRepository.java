package br.ufpr.estagio.modulo.repository;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public interface TermoDeEstagioRepository extends JpaRepository<TermoDeEstagio, Long>, JpaSpecificationExecutor<TermoDeEstagio>{

}
