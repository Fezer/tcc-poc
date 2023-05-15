package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufpr.estagio.modulo.model.Aluno;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class DadosBancariosDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;

	private String banco;
	
	private int numeroDaAgencia;
	
	private int numeroDaConta;
	
	private String nomeDaAgencia;
	
	private String cidadeDaAgencia;
	
	private String enderecoDaAgencia;
	
	private String bairroDaAgencia;

	public DadosBancariosDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DadosBancariosDTO(long id, String banco, int numeroDaAgencia, int numeroDaConta, String nomeDaAgencia,
			String cidadeDaAgencia, String enderecoDaAgencia, String bairroDaAgencia) {
		super();
		this.id = id;
		this.banco = banco;
		this.numeroDaAgencia = numeroDaAgencia;
		this.numeroDaConta = numeroDaConta;
		this.nomeDaAgencia = nomeDaAgencia;
		this.cidadeDaAgencia = cidadeDaAgencia;
		this.enderecoDaAgencia = enderecoDaAgencia;
		this.bairroDaAgencia = bairroDaAgencia;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
