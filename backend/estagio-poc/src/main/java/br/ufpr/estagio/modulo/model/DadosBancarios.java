package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "dados_bancarios", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class DadosBancarios implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "banco")
	private String banco;
	
	@Column(name = "numeroDaAgencia")
	private int numeroDaAgencia;
	
	@Column(name = "numeroDaConta")
	private int numeroDaConta;
	
	@Column(name = "nomeDaAgencia")
	private String nomeDaAgencia;
	
	@Column(name = "cidadeDaAgencia")
	private String cidadeDaAgencia;
	
	@Column(name = "enderecoDaAgencia")
	private String enderecoDaAgencia;
	
	@Column(name = "bairroDaAgencia")
	private String bairroDaAgencia;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="aluno_id", referencedColumnName="id", nullable=true)
	private Aluno aluno;

	public DadosBancarios() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DadosBancarios(long id, String banco, int numeroDaAgencia, int numeroDaConta, String nomeDaAgencia,
			String cidadeDaAgencia, String enderecoDaAgencia, String bairroDaAgencia, Aluno aluno) {
		super();
		this.id = id;
		this.banco = banco;
		this.numeroDaAgencia = numeroDaAgencia;
		this.numeroDaConta = numeroDaConta;
		this.nomeDaAgencia = nomeDaAgencia;
		this.cidadeDaAgencia = cidadeDaAgencia;
		this.enderecoDaAgencia = enderecoDaAgencia;
		this.bairroDaAgencia = bairroDaAgencia;
		this.aluno = aluno;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public int getNumeroDaAgencia() {
		return numeroDaAgencia;
	}

	public void setNumeroDaAgencia(int numeroDaAgencia) {
		this.numeroDaAgencia = numeroDaAgencia;
	}

	public int getNumeroDaConta() {
		return numeroDaConta;
	}

	public void setNumeroDaConta(int numeroDaConta) {
		this.numeroDaConta = numeroDaConta;
	}

	public String getNomeDaAgencia() {
		return nomeDaAgencia;
	}

	public void setNomeDaAgencia(String nomeDaAgencia) {
		this.nomeDaAgencia = nomeDaAgencia;
	}

	public String getCidadeDaAgencia() {
		return cidadeDaAgencia;
	}

	public void setCidadeDaAgencia(String cidadeDaAgencia) {
		this.cidadeDaAgencia = cidadeDaAgencia;
	}

	public String getEnderecoDaAgencia() {
		return enderecoDaAgencia;
	}

	public void setEnderecoDaAgencia(String enderecoDaAgencia) {
		this.enderecoDaAgencia = enderecoDaAgencia;
	}

	public String getBairroDaAgencia() {
		return bairroDaAgencia;
	}

	public void setBairroDaAgencia(String bairroDaAgencia) {
		this.bairroDaAgencia = bairroDaAgencia;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
		
}