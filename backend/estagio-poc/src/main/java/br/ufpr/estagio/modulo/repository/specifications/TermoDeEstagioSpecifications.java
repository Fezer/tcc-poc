package br.ufpr.estagio.modulo.repository.specifications;


import org.springframework.data.jpa.domain.Specification;

import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import jakarta.persistence.criteria.Join;
import br.ufpr.estagio.modulo.model.Estagio;

public class TermoDeEstagioSpecifications {
	
	public static Specification<TermoDeEstagio> hasEstagioWithTipoEstagio(EnumTipoEstagio estagioTipoEstagio) {
		return (root, query, criteriaBuilder) -> {
			Join<Estagio, TermoDeEstagio> termosEstagio = root.join("estagio");
			return criteriaBuilder.equal(termosEstagio.get("tipoEstagio"), estagioTipoEstagio);
		};
	}

}
