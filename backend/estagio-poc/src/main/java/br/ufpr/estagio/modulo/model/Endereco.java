package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class Endereco implements Serializable{
	private long id;
	private String tipoLogradouro;
	private String nomeLogradouro;
	private int numero;
	private String complemento;
	private String cidade;
	private String estado;
	private String cep;
	private Pessoa pessoa;
}
