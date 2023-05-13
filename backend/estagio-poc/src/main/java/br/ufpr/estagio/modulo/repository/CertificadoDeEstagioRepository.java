package br.ufpr.estagio.modulo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.model.CertificadoDeEstagio;

public interface CertificadoDeEstagioRepository extends JpaRepository<CertificadoDeEstagio, Long>{

	List<CertificadoDeEstagio> findByEtapaFluxo(EnumEtapaFluxo etapaFluxo);
	
}
