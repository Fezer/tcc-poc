package br.ufpr.estagio.modulo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.model.Apolice;

public interface ApoliceRepository extends JpaRepository<Apolice, Integer>{

}
