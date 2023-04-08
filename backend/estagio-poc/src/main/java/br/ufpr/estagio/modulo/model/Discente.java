package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "discente", uniqueConstraints = { @UniqueConstraint(columnNames = { "id_discente" }) })
public class Discente implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_discente")
	private long idDiscente;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "isPcD")
	private boolean isPcD;
	
	@Column(name = "dataNascimento")
	private Date dataNascimento;
	
	@Column(name = "documento")
	private String documento;
	
	@Column(name = "grr")
	private String grr;
	
	@Column(name = "periodoAtual")
	private int periodoAtual;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "curso")
	private String curso;
	
	@Column(name = "turno")
	private String turno;
	
	public Discente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Discente(long idDiscente, String nome, boolean isPcD, Date dataNascimento, String documento, String grr,
			int periodoAtual, String email, String rg, String curso, String turno) {
		super();
		this.idDiscente = idDiscente;
		this.nome = nome;
		this.isPcD = isPcD;
		this.dataNascimento = dataNascimento;
		this.documento = documento;
		this.grr = grr;
		this.periodoAtual = periodoAtual;
		this.email = email;
		this.rg = rg;
		this.curso = curso;
		this.turno = turno;
	}

	public long getIdDiscente() {
		return idDiscente;
	}

	public void setIdDiscente(long idDiscente) {
		this.idDiscente = idDiscente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isPcD() {
		return isPcD;
	}

	public void setPcD(boolean isPcD) {
		this.isPcD = isPcD;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getGrr() {
		return grr;
	}

	public void setGrr(String grr) {
		this.grr = grr;
	}

	public int getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(int periodoAtual) {
		this.periodoAtual = periodoAtual;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

}
