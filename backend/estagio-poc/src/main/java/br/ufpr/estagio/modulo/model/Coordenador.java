package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "coordenador")
public class Coordenador extends Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
		this.cpf = cpf;
		this.curso = curso;
		this.termoDeEstagio = termoDeEstagio;
	}
	
	public Coordenador(long id, String nome, String telefone, String cpf, Curso curso, List<TermoDeEstagio> termoDeEstagio) {
		super(id, nome, telefone);
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
