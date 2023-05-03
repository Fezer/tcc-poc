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
@Table(name = "DadosAuxiliaresSiga", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class DadosAuxiliaresSiga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "nomePai")
	private String nomePai;
	
	@Column(name = "corRaca")
	private String corRaca;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "nomeDaMae")
	private String nomeMae;
	
	@Column(name = "nacionalidade")
	private String nacionalidade;
	
	@Column(name = "estadoNascimento")
	private String estadoNascimento;
	
	@Column(name = "cidadeDeNascimento")
	private String cidadeDeNascimento;
	
	@Column(name = "expressaoGenero")
	private String expressaoGenero;
	
	@Column(name = "autoIdentificacaoGenero")
	private String autoIdentificacaoGenero;
	
	@Column(name = "orientacaoSexual")
	private String orientacaoSexual;

	public DadosAuxiliaresSiga() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DadosAuxiliaresSiga(long id, String nomePai, String corRaca, String sexo, String nomeMae,
			String nacionalidade, String estadoNascimento, String cidadeDeNascimento, String expressaoGenero,
			String autoIdentificacaoGenero, String orientacaoSexual) {
		super();
		this.id = id;
		this.nomePai = nomePai;
		this.corRaca = corRaca;
		this.sexo = sexo;
		this.nomeMae = nomeMae;
		this.nacionalidade = nacionalidade;
		this.estadoNascimento = estadoNascimento;
		this.cidadeDeNascimento = cidadeDeNascimento;
		this.expressaoGenero = expressaoGenero;
		this.autoIdentificacaoGenero = autoIdentificacaoGenero;
		this.orientacaoSexual = orientacaoSexual;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getCorRaca() {
		return corRaca;
	}

	public void setCorRaca(String corRaca) {
		this.corRaca = corRaca;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getEstadoNascimento() {
		return estadoNascimento;
	}

	public void setEstadoNascimento(String estadoNascimento) {
		this.estadoNascimento = estadoNascimento;
	}

	public String getCidadeDeNascimento() {
		return cidadeDeNascimento;
	}

	public void setCidadeDeNascimento(String cidadeDeNascimento) {
		this.cidadeDeNascimento = cidadeDeNascimento;
	}

	public String getExpressaoGenero() {
		return expressaoGenero;
	}

	public void setExpressaoGenero(String expressaoGenero) {
		this.expressaoGenero = expressaoGenero;
	}

	public String getAutoIdentificacaoGenero() {
		return autoIdentificacaoGenero;
	}

	public void setAutoIdentificacaoGenero(String autoIdentificacaoGenero) {
		this.autoIdentificacaoGenero = autoIdentificacaoGenero;
	}

	public String getOrientacaoSexual() {
		return orientacaoSexual;
	}

	public void setOrientacaoSexual(String orientacaoSexual) {
		this.orientacaoSexual = orientacaoSexual;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
