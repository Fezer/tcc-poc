package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "coordenador", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Coordenador extends Pessoa implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "cpf")
	private String cpf;
	
	//@OneToOne
	//@JoinColumn(name="curso")
	@OneToOne(mappedBy="coordenador")
	private Curso curso;
	
	@Column(name = "termo_de_estagio")
	//@OneToMany(mappedBy="coordenador")
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
