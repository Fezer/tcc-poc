package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class PlanoDeAtividades implements Serializable{
	
	private long id;
	private String local;
	private Supervisor supervisor;
	private String descricaoAtividades;
	private Estagio estagio;
	private TermoDeEstagio termoDeEstagio;
	
	public PlanoDeAtividades() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanoDeAtividades(long id, String local, Supervisor supervisor, String descricaoAtividades, Estagio estagio,
			TermoDeEstagio termoDeEstagio) {
		super();
		this.id = id;
		this.local = local;
		this.supervisor = supervisor;
		this.descricaoAtividades = descricaoAtividades;
		this.estagio = estagio;
		this.termoDeEstagio = termoDeEstagio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public String getDescricaoAtividades() {
		return descricaoAtividades;
	}

	public void setDescricaoAtividades(String descricaoAtividades) {
		this.descricaoAtividades = descricaoAtividades;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public TermoDeEstagio getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(TermoDeEstagio termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}

}
