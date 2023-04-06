package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "aluno", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Aluno extends Pessoa implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "matricula")
	private String matricula;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "dataNascimento")
	private Date dataNascimento;
	
	//@OneToOne
	//@JoinColumn(name="id_curso")
	@OneToOne(mappedBy="aluno")
	private Curso curso;
	
	//@OneToOne
	//@JoinColumn(name="id_dadosAuxiliares")
	@OneToOne(mappedBy="aluno")
	private DadosAuxiliares dadosAuxiliares;
	
	//@OneToOne
	//@JoinColumn(name="id_dadosBancarios")
	@OneToOne(mappedBy="aluno")
	private DadosBancarios dadosBancarios;
	
	// Nao existe o relacionamento aluno-disciplina no diagrama
	@Column(name = "disciplina")
	//@OneToMany(mappedBy="aluno")
	private ArrayList<Disciplina> disciplina;
	
	//Faltou o relacionamento aluno-pessoa:
	
	//@OneToOne
	//@JoinColumn(name="id_dadosBancarios")
	@OneToOne(mappedBy="aluno")
	private Pessoa pessoa;
	
	public Aluno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Aluno(long id, String matricula, String rg, String cpf, String email, Date dataNascimento, Curso curso,
			DadosAuxiliares dadosAuxiliares, DadosBancarios dadosBancarios, ArrayList<Disciplina> disciplina, Pessoa pessoa) {
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
		this.pessoa = pessoa;
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

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
}
