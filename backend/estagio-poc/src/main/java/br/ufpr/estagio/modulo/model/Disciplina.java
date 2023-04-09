package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "disciplina", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Disciplina implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToMany(cascade = { CascadeType.REMOVE })
	@JoinTable(
	    name = "disciplina_aluno", 
	    joinColumns = { @JoinColumn(name = "disciplina_id") }, 
	    inverseJoinColumns = { @JoinColumn(name = "aluno_id") }
	)
	private List<Aluno> aluno;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="curso_id", referencedColumnName="id", nullable=true)
	private Curso curso;

	public Disciplina() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Disciplina(long id, List<Aluno> aluno, Curso curso) {
		super();
		this.id = id;
		this.aluno = aluno;
		this.curso = curso;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Aluno> getAluno() {
		return aluno;
	}

	public void setAluno(List<Aluno> aluno) {
		this.aluno = aluno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
