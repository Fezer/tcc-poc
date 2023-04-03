package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Supervisor extends Pessoa implements Serializable{
	
	private long id;
	private String cpf;
	private String formacao;
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	private ArrayList<Estagio> estagio;
	
	public Supervisor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Supervisor(long id, String cpf, String formacao, ArrayList<TermoDeEstagio> termoDeEstagio,
			ArrayList<Estagio> estagio) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.formacao = formacao;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getFormacao() {
		return formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
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
