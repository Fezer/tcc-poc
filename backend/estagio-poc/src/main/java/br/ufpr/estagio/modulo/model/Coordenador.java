package br.ufpr.estagio.modulo.model;

import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public class Coordenador {
	private int id;
	private String cpf;
	private String curso; // Estou satisfeito só com o nome do curso
	private TermoDeEstagio termoDeEstagio;

	public Coordenador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coordenador(int id, String cpf, String curso, TermoDeEstagio termoDeEstagio) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.curso = curso;
		this.termoDeEstagio = termoDeEstagio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public TermoDeEstagio getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(TermoDeEstagio termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}
	
	
}
