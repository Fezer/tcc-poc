package br.ufpr.estagio.modulo.repository;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	
	
}
