package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
	
//03-05-2023 - ApiSiga não retorna mais objeto curso
//	@ManyToOne
//  @JoinColumn(name = "curso_id")
//    private CursoSiga curso;

//15-04-2023 - Atributo dataNascimento não existe na versão da API do SIGA nesta data.	
//03-05-2023 - Atributo adicionado
	@Column(name = "dataNascimento")
	private Date dataNascimento;
	
	@Column(name = "turno")
	private String turno;
	
    @OneToOne
    @JoinColumn(name = "dadosComplementares_id")
	private DadosComplementaresSiga dadosComplementares;
	
	@Column(name = "coordenador")
	private String coordenador;
	
	@Column(name = "idPrograma")
	private String idPrograma;
	
	@OneToOne
    @JoinColumn(name = "endereco_id")
	private Endereco endereco;
	
	@OneToOne
    @JoinColumn(name = "dadosAuxiliares_id")
	private DadosAuxiliares dadosAuxiliares;
	
	@Column(name = "ira")
	private String ira;
	
	@Column(name = "idCurso")
	private Long idCurso;
	
	@Column(name = "isMatriculado")
	private boolean isMatriculado;
	
	public Discente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Discente(long idDiscente, String nome, boolean isPcD, String documento, String grr, int periodoAtual,
			String email, String rg, Date dataNascimento, String turno, DadosComplementaresSiga dadosComplementares,
			String coordenador, String idPrograma, Long idCurso, Endereco endereco, DadosAuxiliares dadosAuxiliares,
			String ira, boolean isMatriculado) {
		super();
		this.idDiscente = idDiscente;
		this.nome = nome;
		this.isPcD = isPcD;
		this.documento = documento;
		this.grr = grr;
		this.periodoAtual = periodoAtual;
		this.email = email;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
		this.turno = turno;
		this.dadosComplementares = dadosComplementares;
		this.coordenador = coordenador;
		this.idPrograma = idPrograma;
		this.idCurso = idCurso;
		this.endereco = endereco;
		this.dadosAuxiliares = dadosAuxiliares;
		this.ira = ira;
		this.isMatriculado = isMatriculado;
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public DadosAuxiliares getDadosAuxiliares() {
		return dadosAuxiliares;
	}

	public void setDadosAuxiliares(DadosAuxiliares dadosAuxiliares) {
		this.dadosAuxiliares = dadosAuxiliares;
	}

	public String getIra() {
		return ira;
	}

	public void setIra(String ira) {
		this.ira = ira;
	}

	public boolean isMatriculado() {
		return isMatriculado;
	}

	public void setMatriculado(boolean isMatriculado) {
		this.isMatriculado = isMatriculado;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
