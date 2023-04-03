package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class AgenteIntegrador extends Pessoa implements Serializable {
	
	private long id;
	private String cnpj;
	private ArrayList<Convenio> convenio;
	private ArrayList<TermoDeEstagio> termoDeEstagio;
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
