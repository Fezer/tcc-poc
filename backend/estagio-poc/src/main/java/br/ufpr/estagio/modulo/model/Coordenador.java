package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "coordenador", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
//@Inheritance(strategy = InheritanceType.JOINED)
public class Coordenador extends Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "cpf")
	private String cpf;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="curso_id", referencedColumnName="id", nullable=true)
	private Curso curso;
	
	@JsonIgnore
	@OneToMany(mappedBy="coordenador", cascade=CascadeType.REMOVE)
	private List<TermoDeEstagio> termoDeEstagio;

	public Coordenador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coordenador(long id, String cpf, Curso curso, List<TermoDeEstagio> termoDeEstagio) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.curso = curso;
		this.termoDeEstagio = termoDeEstagio;
	}
	
	public Coordenador(long id, String nome, String telefone, String cpf, Curso curso, List<TermoDeEstagio> termoDeEstagio) {
		super(id, nome, telefone);
		this.id = id;
		this.cpf = cpf;
		this.curso = curso;
		this.termoDeEstagio = termoDeEstagio;
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

	public List<TermoDeEstagio> getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(List<TermoDeEstagio> termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}	
}
