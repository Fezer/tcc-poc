package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "disciplina", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Disciplina implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "aluno")
	//@OneToMany(mappedBy="disciplina")
	private ArrayList<Aluno> aluno;
	
	//@OneToOne
	//@JoinColumn(name="curso")
	@OneToOne(mappedBy="disciplina")
	private Curso curso;
}
