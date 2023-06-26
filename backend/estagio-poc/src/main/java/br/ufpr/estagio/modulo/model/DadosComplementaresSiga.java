package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(name = "DadosComplementaresSiga", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class DadosComplementaresSiga implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "estadoNascimento")
	private int estadoNascimento;

	@Column(name = "corRaca")
	private int corRaca;

	@Column(name = "numeroEndereco")
	private int numeroEndereco;

	@Column(name = "estado")
	private int estado;

	@Column(name = "expressaoGenero")
	private int expressaoGenero;

	@Column(name = "autoIdentificacaoGenero")
	private int autoIdentificacaoGenero;

	@Column(name = "orientacaoSexual")
	private int orientacaoSexual;

	@Column(name = "emailInstitucional")
	private String emailInstitucional;

	public DadosComplementaresSiga() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DadosComplementaresSiga(long id, int estadoNascimento, int corRaca, int numeroEndereco, int estado,
			int expressaoGenero, int autoIdentificacaoGenero, int orientacaoSexual, String emailInstitucional) {
		super();
		this.id = id;
		this.estadoNascimento = estadoNascimento;
		this.corRaca = corRaca;
		this.numeroEndereco = numeroEndereco;
		this.estado = estado;
		this.expressaoGenero = expressaoGenero;
		this.autoIdentificacaoGenero = autoIdentificacaoGenero;
		this.orientacaoSexual = orientacaoSexual;
		this.emailInstitucional = emailInstitucional;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getEstadoNascimento() {
		return estadoNascimento;
	}

	public void setEstadoNascimento(int estadoNascimento) {
		this.estadoNascimento = estadoNascimento;
	}

	public int getCorRaca() {
		return corRaca;
	}

	public void setCorRaca(int corRaca) {
		this.corRaca = corRaca;
	}

	public int getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(int numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getExpressaoGenero() {
		return expressaoGenero;
	}

	public void setExpressaoGenero(int expressaoGenero) {
		this.expressaoGenero = expressaoGenero;
	}

	public int getAutoIdentificacaoGenero() {
		return autoIdentificacaoGenero;
	}

	public void setAutoIdentificacaoGenero(int autoIdentificacaoGenero) {
		this.autoIdentificacaoGenero = autoIdentificacaoGenero;
	}

	public int getOrientacaoSexual() {
		return orientacaoSexual;
	}

	public void setOrientacaoSexual(int orientacaoSexual) {
		this.orientacaoSexual = orientacaoSexual;
	}

	public String getEmailInstitucional() {
		return emailInstitucional;
	}

	public void setEmailInstitucional(String emailInstitucional) {
		this.emailInstitucional = emailInstitucional;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}