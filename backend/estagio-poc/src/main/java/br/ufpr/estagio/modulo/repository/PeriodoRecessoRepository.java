package br.ufpr.estagio.modulo.repository;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.PeriodoRecesso;

public interface PeriodoRecessoRepository extends JpaRepository<PeriodoRecesso, Long>{
	
}
