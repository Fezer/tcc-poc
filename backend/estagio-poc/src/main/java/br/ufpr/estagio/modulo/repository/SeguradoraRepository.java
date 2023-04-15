package br.ufpr.estagio.modulo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.estagio.modulo.model.Seguradora;

public interface SeguradoraRepository extends JpaRepository<Seguradora, Long> {

}
