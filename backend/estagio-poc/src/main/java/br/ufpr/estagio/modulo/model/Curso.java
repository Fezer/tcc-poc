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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "curso", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Curso implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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

	@OneToMany(mappedBy="curso", cascade=CascadeType.REMOVE)
	private List<Coordenador> coordenador;
	
	@ManyToMany(cascade = { CascadeType.REMOVE })
	@JoinTable(
	    name = "curso_orientador",
	    joinColumns = {@JoinColumn(name="orientador_id")},
	    inverseJoinColumns = {@JoinColumn(name="curso_id")})
	private List<Orientador> orientador;

	@OneToMany(mappedBy="curso", cascade=CascadeType.REMOVE)
	private List<Disciplina> disciplina;
	
	@OneToMany(mappedBy="curso", cascade=CascadeType.REMOVE)
	private List<Aluno> aluno;

	public Curso() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Curso(long id, String nome, String matricula, String nivel, int peridiocidade,
			List<Coordenador> coordenador, List<Orientador> orientador, List<Disciplina> disciplina,
			List<Aluno> aluno) {
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

	public List<Coordenador> getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(List<Coordenador> coordenador) {
		this.coordenador = coordenador;
	}

	public List<Orientador> getOrientador() {
		return orientador;
	}

	public void setOrientador(List<Orientador> orientador) {
		this.orientador = orientador;
	}

	public List<Disciplina> getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(List<Disciplina> disciplina) {
		this.disciplina = disciplina;
	}

	public List<Aluno> getAluno() {
		return aluno;
	}

	public void setAluno(List<Aluno> aluno) {
		this.aluno = aluno;
	}
	
}
