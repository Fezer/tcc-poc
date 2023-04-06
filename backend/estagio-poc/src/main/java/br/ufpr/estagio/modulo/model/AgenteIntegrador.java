package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "agente_integrador", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class AgenteIntegrador extends Pessoa implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "id_convenio")
	//@OneToMany(mappedBy="agente_integrador")
	private ArrayList<Convenio> convenio;
	
	@Column(name = "TermoDeEstagio_id")
	//@OneToMany(mappedBy="agente_integrador")
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	
	@Column(name = "Estagio_id")
	//@OneToMany(mappedBy="agente_integrador")
	private ArrayList<Estagio> estagio;
	
	public AgenteIntegrador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgenteIntegrador(long id, String cnpj, ArrayList<Convenio> convenio,
			ArrayList<TermoDeEstagio> termoDeEstagio, ArrayList<Estagio> estagio) {
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

	public ArrayList<Convenio> getConvenio() {
		return convenio;
	}

	public void setConvenio(ArrayList<Convenio> convenio) {
		this.convenio = convenio;
	}

	public ArrayList<TermoDeEstagio> getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(ArrayList<TermoDeEstagio> termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}

	public ArrayList<Estagio> getEstagio() {
		return estagio;
	}

	public void setEstagio(ArrayList<Estagio> estagio) {
		this.estagio = estagio;
	}

}
