package br.ufpr.estagio.modulo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.Estagio;

public interface EstagioRepository extends JpaRepository<Estagio, Long>{

}
