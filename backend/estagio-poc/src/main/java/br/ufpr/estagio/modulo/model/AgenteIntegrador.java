package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "agente_integrador", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class AgenteIntegrador extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@OneToMany(mappedBy="agenteIntegrador", cascade=CascadeType.ALL)
	private List<Convenio> convenio;
	
	@OneToMany(mappedBy="agenteIntegrador", cascade=CascadeType.ALL)
	private List<TermoDeEstagio> termoDeEstagio;
	
	@OneToMany(mappedBy="agenteIntegrador", cascade=CascadeType.ALL)
	private List<Estagio> estagio;
	
	public AgenteIntegrador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgenteIntegrador(long id, String cnpj, List<Convenio> convenio,
			List<TermoDeEstagio> termoDeEstagio, List<Estagio> estagio) {
		super();
		this.id = id;
		this.cnpj = cnpj;
		this.convenio = convenio;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
