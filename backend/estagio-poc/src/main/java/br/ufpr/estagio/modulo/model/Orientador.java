package br.ufpr.estagio.modulo.model;

import java.util.ArrayList;

public class Orientador extends Pessoa {
	
	private long id;
	private String cpf;
	private String lotacao;
	private String departamento;
	private ArrayList<Curso> curso;
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	private ArrayList<Estagio> estagio;

}
