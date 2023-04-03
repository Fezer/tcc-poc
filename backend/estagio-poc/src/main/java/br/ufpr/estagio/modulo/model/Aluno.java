package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Aluno extends Pessoa implements Serializable {
	private long id;
	private String matricula;
	private String rg;
	private String cpf;
	private String email;
	private Date dataNascimento;
	private Curso curso;
	private DadosAuxiliares dadosAuxiliares;
	private DadosBancarios dadosBancarios;
	private ArrayList<Disciplina> disciplina;
	
	public Aluno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Aluno(long id, String matricula, String rg, String cpf, String email, Date dataNascimento, Curso curso,
			DadosAuxiliares dadosAuxiliares, DadosBancarios dadosBancarios, ArrayList<Disciplina> disciplina) {
		super();
		this.id = id;
		this.matricula = matricula;
		this.rg = rg;
		this.cpf = cpf;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.curso = curso;
		this.dadosAuxiliares = dadosAuxiliares;
		this.dadosBancarios = dadosBancarios;
		this.disciplina = disciplina;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public DadosAuxiliares getDadosAuxiliares() {
		return dadosAuxiliares;
	}

	public void setDadosAuxiliares(DadosAuxiliares dadosAuxiliares) {
		this.dadosAuxiliares = dadosAuxiliares;
	}

	public DadosBancarios getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(DadosBancarios dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	public ArrayList<Disciplina> getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(ArrayList<Disciplina> disciplina) {
		this.disciplina = disciplina;
	}
	
}
