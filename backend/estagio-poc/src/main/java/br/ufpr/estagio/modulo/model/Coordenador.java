package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public class Coordenador extends Pessoa implements Serializable{
	private long id;
	private String cpf;
	private Curso curso;
	private ArrayList<TermoDeEstagio> termoDeEstagio;

	public Coordenador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coordenador(long id, String cpf, Curso curso, ArrayList<TermoDeEstagio> termoDeEstagio) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.curso = curso;
		this.termoDeEstagio = termoDeEstagio;
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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public ArrayList<TermoDeEstagio> getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(ArrayList<TermoDeEstagio> termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}	
}
