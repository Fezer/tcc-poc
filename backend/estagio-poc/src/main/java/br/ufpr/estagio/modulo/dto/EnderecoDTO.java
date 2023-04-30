package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;

import br.ufpr.estagio.modulo.model.Pessoa;

public class EnderecoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String logradouro;
	private int numero;
	private String complemento;
	private String cidade;
	private String estado;
	private String cep;
	private Pessoa pessoa;
	
	
	public EnderecoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EnderecoDTO(long id, String logradouro, int numero, String complemento, String cidade, String estado,
			String cep, Pessoa pessoa) {
		super();
		this.id = id;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.pessoa = pessoa;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getLogradouro() {
		return logradouro;
	}


	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
