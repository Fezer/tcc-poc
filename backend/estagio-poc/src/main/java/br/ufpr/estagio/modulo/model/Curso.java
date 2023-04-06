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
@Table(name = "curso", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Curso implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "matricula")
	private String matricula;
	
	@Column(name = "nivel")
	private String nivel;
	
	@Column(name = "peridiocidade")
	private int peridiocidade;
	
	@Column(name = "coordenador")
	//@OneToMany(mappedBy="curso")
	private ArrayList<Coordenador> coordenador;
	
	@Column(name = "orientador")
	//@OneToMany(mappedBy="curso")
	private ArrayList<Orientador> orientador;
	
	@Column(name = "disciplina")
	//@OneToMany(mappedBy="curso")
	private ArrayList<Disciplina> disciplina;
	
	@Column(name = "relatorio_de_estagio")
	//@OneToMany(mappedBy="estagio")
	private ArrayList<Aluno> aluno;

	public Curso() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Curso(long id, String nome, String matricula, String nivel, int peridiocidade,
			ArrayList<Coordenador> coordenador, ArrayList<Orientador> orientador, ArrayList<Disciplina> disciplina,
			ArrayList<Aluno> aluno) {
		super();
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.nivel = nivel;
		this.peridiocidade = peridiocidade;
		this.coordenador = coordenador;
		this.orientador = orientador;
		this.disciplina = disciplina;
		this.aluno = aluno;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public int getPeridiocidade() {
		return peridiocidade;
	}

	public void setPeridiocidade(int peridiocidade) {
		this.peridiocidade = peridiocidade;
	}

	public ArrayList<Coordenador> getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(ArrayList<Coordenador> coordenador) {
		this.coordenador = coordenador;
	}

	public ArrayList<Orientador> getOrientador() {
		return orientador;
	}

	public void setOrientador(ArrayList<Orientador> orientador) {
		this.orientador = orientador;
	}

	public ArrayList<Disciplina> getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(ArrayList<Disciplina> disciplina) {
		this.disciplina = disciplina;
	}

	public ArrayList<Aluno> getAluno() {
		return aluno;
	}

	public void setAluno(ArrayList<Aluno> aluno) {
		this.aluno = aluno;
	}
	
}
