package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "aluno", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Aluno extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="curso_id", referencedColumnName="id", nullable=true)
	private Curso curso;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="dados_auxiliares_id", referencedColumnName="id", nullable=true)
	private DadosAuxiliares dadosAuxiliares;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="dados_bancarios_id", referencedColumnName="id", nullable=true)
	private DadosBancarios dadosBancarios;
	
	@ManyToMany(mappedBy = "aluno", cascade=CascadeType.REMOVE)
	private List<Disciplina> disciplina;
	
	//Faltou o relacionamento aluno-pessoa:
	//Pessoa é herança não tem relacionamento
	
	@JsonIgnore
	@OneToMany(mappedBy="aluno", cascade=CascadeType.REMOVE)
	private List<Estagio> estagio;
	
	public Aluno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Aluno(long id, String matricula, String rg, String cpf, String email, Date dataNascimento, Curso curso,
			DadosAuxiliares dadosAuxiliares, DadosBancarios dadosBancarios, List<Disciplina> disciplina,
			Pessoa pessoa, List<Estagio> estagio) {
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
		this.estagio = estagio;
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

	public List<Disciplina> getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(List<Disciplina> disciplina) {
		this.disciplina = disciplina;
	}

	public List<Estagio> getEstagio() {
		return estagio;
	}

	public void setEstagio(List<Estagio> estagio) {
		this.estagio = estagio;
	}

	public Estagio novoEstagio() {
		Estagio estagio = new Estagio();
		estagio.setAluno(this);
		estagio.novoTermoCompromisso();
		this.setEstagio(getEstagio());
		return estagio;
	}

}
