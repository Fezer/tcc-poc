package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;

public class CoordenadorDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nome;
	private String telefone;
	private String cpf;
	
	public CoordenadorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CoordenadorDTO(long id, String nome, String telefone, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.cpf = cpf;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
