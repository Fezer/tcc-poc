package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "discente", uniqueConstraints = { @UniqueConstraint(columnNames = { "idDiscente" }) })
public class Discente implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idDiscente")
	private long idDiscente;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "isPcD")
	private boolean isPcD;

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
	
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoSiga curso;

//15-04-2023 - Atributo dataNascimento não existe na versão da API do SIGA nesta data.	
//	@Column(name = "dataNascimento")
//	private Date dataNascimento;
	
	@Column(name = "turno")
	private String turno;
	
    @OneToOne
    @JoinColumn(name = "dadosComplementares_id")
	private DadosComplementaresSiga dadosComplementares;
	
	@Column(name = "coordenador")
	private String coordenador;
	
	@Column(name = "idPrograma")
	private String idPrograma;
	
	public Discente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Discente(long idDiscente, String nome, boolean isPcD, String documento, String grr, int periodoAtual,
			String email, String rg, CursoSiga curso, String turno, DadosComplementaresSiga dadosComplementares, String coordenador,
			String idPrograma) {
		super();
		this.idDiscente = idDiscente;
		this.nome = nome;
		this.isPcD = isPcD;
		this.documento = documento;
		this.grr = grr;
		this.periodoAtual = periodoAtual;
		this.email = email;
		this.rg = rg;
		this.curso = curso;
		this.turno = turno;
		this.dadosComplementares = dadosComplementares;
		this.coordenador = coordenador;
		this.idPrograma = idPrograma;
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

	public CursoSiga getCurso() {
		return curso;
	}

	public void setCurso(CursoSiga curso) {
		this.curso = curso;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public DadosComplementaresSiga getDadosComplementares() {
		return dadosComplementares;
	}

	public void setDadosComplementares(DadosComplementaresSiga dadosComplementares) {
		this.dadosComplementares = dadosComplementares;
	}

	public String getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(String coordenador) {
		this.coordenador = coordenador;
	}

	public String getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
