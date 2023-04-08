package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	//@Column(name = "aluno")
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(
	    name = "disciplina_aluno", 
	    joinColumns = { @JoinColumn(name = "disciplina_id") }, 
	    inverseJoinColumns = { @JoinColumn(name = "aluno_id") }
	)
	private ArrayList<Aluno> aluno;
	
	@ManyToOne
	@JoinColumn(name="curso_id", referencedColumnName="id", nullable=true)
	private Curso curso;
}
