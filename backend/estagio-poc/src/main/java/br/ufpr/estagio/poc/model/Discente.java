package br.ufpr.estagio.poc.model;

import java.util.Date;

public class Discente {

	private long idDiscente;
	private String nome;
	private boolean isPcd;
	private Date dataNascimento;
	private String documento;
	private String grr;
	private int periodoAtual;
	private String email;
	private String rg;
	private String curso;
	private String turno;
	
	public Discente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Discente(long idDiscente, String nome, boolean isPcd, Date dataNascimento, String documento, String grr,
			int periodoAtual, String email, String rg, String curso, String turno) {
		super();
		this.idDiscente = idDiscente;
		this.nome = nome;
		this.isPcd = isPcd;
		this.dataNascimento = dataNascimento;
		this.documento = documento;
		this.grr = grr;
		this.periodoAtual = periodoAtual;
		this.email = email;
		this.rg = rg;
		this.curso = curso;
		this.turno = turno;
	}

	public long getIdDiscente() {
		return idDiscente;
	}

	public void setIdDiscente(long idDiscente) {
		this.idDiscente = idDiscente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isPcd() {
		return isPcd;
	}

	public void setPcd(boolean isPcd) {
		this.isPcd = isPcd;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getGrr() {
		return grr;
	}

	public void setGrr(String grr) {
		this.grr = grr;
	}

	public int getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(int periodoAtual) {
		this.periodoAtual = periodoAtual;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

}
