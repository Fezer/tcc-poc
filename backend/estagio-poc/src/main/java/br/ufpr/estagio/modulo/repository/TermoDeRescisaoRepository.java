package br.ufpr.estagio.modulo.repository;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.TermoDeRescisao;

public interface TermoDeRescisaoRepository extends JpaRepository<TermoDeRescisao, Long>{
	
}
