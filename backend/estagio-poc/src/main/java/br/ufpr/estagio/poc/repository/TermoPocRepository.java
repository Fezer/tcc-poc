package br.ufpr.estagio.poc.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.poc.model.TermoPoc;

public interface TermoPocRepository extends JpaRepository<TermoPoc, Long>{
	
	Optional<TermoPoc> findByGrrAluno(String grr);
	
}
