package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "idCurso")
	private long idCurso;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "matricula")
	private String matricula;
	
	@Column(name = "idPrograma")
	private String idPrograma;
	
	@Column(name = "nivel")
	private String nivel;
	
	@Column(name = "peridiocidade")
	private int peridiocidade;
	
	@Column(name = "turno")
	private String turno;

	@JsonIgnore
	@OneToMany(mappedBy="curso", cascade=CascadeType.REMOVE)
	private List<Coordenador> coordenador;
	
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.REMOVE })
	@JoinTable(
	    name = "curso_orientador",
	    joinColumns = {@JoinColumn(name="orientador_id")},
	    inverseJoinColumns = {@JoinColumn(name="curso_id")})
	private List<Orientador> orientador;

//	@JsonIgnore
//	@OneToMany(mappedBy="curso", cascade=CascadeType.REMOVE)
//	private List<Disciplina> disciplina;
	
	@JsonIgnore
	@OneToMany(mappedBy="curso", cascade=CascadeType.REMOVE)
	private List<Aluno> aluno;

	public Curso() {
		super();
//		this.disciplina = new ArrayList<Disciplina>();
		this.coordenador = new ArrayList<Coordenador>();
		this.aluno = new ArrayList<Aluno>();
		this.orientador = new ArrayList<Orientador>();
	}



	public Curso(long id, long idCurso, String nome, String matricula, String idPrograma, String nivel,
			int peridiocidade, String turno, List<Coordenador> coordenador, List<Orientador> orientador,
			/**List<Disciplina> disciplina,**/ List<Aluno> aluno) {
		super();
		this.id = id;
		this.idCurso = idCurso;
		this.nome = nome;
		this.matricula = matricula;
		this.idPrograma = idPrograma;
		this.nivel = nivel;
		this.peridiocidade = peridiocidade;
		this.turno = turno;
		this.coordenador = coordenador;
		this.orientador = orientador;
//		this.disciplina = disciplina;
		this.aluno = aluno;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getIdCurso() {
		return idCurso;
	}


	public void setIdCurso(long idCurso) {
		this.idCurso = idCurso;
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

	public String getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
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

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
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

//	public List<Disciplina> getDisciplina() {
//		return disciplina;
//	}
//
//	public void setDisciplina(List<Disciplina> disciplina) {
//		this.disciplina = disciplina;
//	}

	public List<Aluno> getAluno() {
		return aluno;
	}

	public void setAluno(List<Aluno> aluno) {
		this.aluno = aluno;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
