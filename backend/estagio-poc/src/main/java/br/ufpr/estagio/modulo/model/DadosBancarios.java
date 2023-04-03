package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class DadosBancarios implements Serializable{
	private long id;
	private String banco;
	private int numeroDaAgencia;
	private int numeroDaConta;
	private String nomeDaAgencia;
	private String cidadeDaAgencia;
	private String enderecoDaAgencia;
	private String bairroDaAgencia;
	private Aluno aluno;
}