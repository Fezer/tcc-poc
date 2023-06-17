package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "agente_integrador")
public class AgenteIntegrador extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Column(name = "cnpj")
	private String cnpj;
	
	@JsonIgnore
	@OneToMany(mappedBy="agenteIntegrador", cascade=CascadeType.ALL)
	private List<Convenio> convenio;
	
	@JsonIgnore
	@OneToMany(mappedBy="agenteIntegrador", cascade=CascadeType.ALL)
	private List<TermoDeEstagio> termoDeEstagio;
	
	@JsonIgnore
	@OneToMany(mappedBy="agenteIntegrador", cascade=CascadeType.ALL)
	private List<Estagio> estagio;
	
	public AgenteIntegrador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgenteIntegrador(long id, String cnpj, List<Convenio> convenio,
			List<TermoDeEstagio> termoDeEstagio, List<Estagio> estagio) {
		super();
		this.cnpj = cnpj;
		this.convenio = convenio;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}

	public long getId() {
		return super.getId();
	}

	public void setId(long id) {
		super.setId(id);
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Convenio> getConvenio() {
		return convenio;
	}

	public void setConvenio(List<Convenio> convenio) {
		this.convenio = convenio;
	}

	public List<TermoDeEstagio> getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(List<TermoDeEstagio> termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}

	public List<Estagio> getEstagio() {
		return estagio;
	}

	public void setEstagio(List<Estagio> estagio) {
		this.estagio = estagio;
	}

}
