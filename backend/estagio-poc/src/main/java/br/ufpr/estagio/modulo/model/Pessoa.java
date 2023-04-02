package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class Pessoa implements Serializable{
	private long id;
	private String nome;
	private String telefone;
	private Endereco endereco;
}
