package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orientador")
public class Orientador extends Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "lotacao")
	private String lotacao;
	
	@Column(name = "departamento")
	private String departamento;

	@JsonIgnore
	@ManyToMany(mappedBy = "orientador", cascade=CascadeType.REMOVE)
	private List<Curso> curso;

	@JsonIgnore
	@OneToMany(mappedBy="orientador", cascade=CascadeType.REMOVE)
	private List<TermoDeEstagio> termoDeEstagio;
	
	@JsonIgnore
	@OneToMany(mappedBy="orientador", cascade=CascadeType.REMOVE)
	private List<Estagio> estagio;
	
	public Orientador() {
		super();
	}

	public Orientador(long id, String cpf, String lotacao, String departamento, List<Curso> curso,
			List<TermoDeEstagio> termoDeEstagio, List<Estagio> estagio) {
		super();
		this.cpf = cpf;
		this.lotacao = lotacao;
		this.departamento = departamento;
		this.curso = curso;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}
	
	public Orientador(long id, String nome, String telefone, String cpf, String lotacao, String departamento, List<Curso> curso,
			List<TermoDeEstagio> termoDeEstagio, List<Estagio> estagio) {
		super(id, nome, telefone);
		this.cpf = cpf;
		this.lotacao = lotacao;
		this.departamento = departamento;
		this.curso = curso;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}
	
	public String getNome() {
		return super.getNome();
	}
	
	public void setNome(String nome) {
		super.setNome(nome);
	}
	
	public String getTelefone() {
		return super.getTelefone();
	}
	
	public void setTelefone(String telefone) {
		super.setTelefone(telefone);
	}

	public long getId() {
		return super.getId();
	}

	public void setId(long id) {
		super.setId(id);
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

	public List<Curso> getCurso() {
		return curso;
	}

	public void setCurso(List<Curso> curso) {
		this.curso = curso;
	}

	public List<TermoDeEstagio> getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(List<TermoDeEstagio> termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}

	public List<Estagio> getEstagio() {
		return estagio;
	}

	public void setEstagio(List<Estagio> estagio) {
		this.estagio = estagio;
	}

}
