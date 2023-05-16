package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;

public class PessoaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String nome;
	
	private String telefone;

	public PessoaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PessoaDTO(long id, String nome, String telefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
