package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "orientador", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Orientador extends Pessoa implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "lotacao")
	private String lotacao;
	
	@Column(name = "departamento")
	private String departamento;
	
	@Column(name = "curso")
	//@OneToMany(mappedBy="orientador")
	private ArrayList<Curso> curso;
	
	@Column(name = "termo_de_estagio")
	//@OneToMany(mappedBy="orientador")
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	
	@Column(name = "estagio")
	//@OneToMany(mappedBy="orientador")
	private ArrayList<Estagio> estagio;
	
	public Orientador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Orientador(long id, String cpf, String lotacao, String departamento, ArrayList<Curso> curso,
			ArrayList<TermoDeEstagio> termoDeEstagio, ArrayList<Estagio> estagio) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.lotacao = lotacao;
		this.departamento = departamento;
		this.curso = curso;
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

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public ArrayList<Curso> getCurso() {
		return curso;
	}

	public void setCurso(ArrayList<Curso> curso) {
		this.curso = curso;
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
