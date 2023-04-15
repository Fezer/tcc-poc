package br.ufpr.estagio.modulo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.estagio.modulo.model.Contratante;

public interface ContratanteRepository extends JpaRepository<Contratante, Integer> {

}
