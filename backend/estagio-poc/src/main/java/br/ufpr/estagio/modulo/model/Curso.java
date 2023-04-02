package br.ufpr.estagio.modulo.model;

import java.util.ArrayList;

public class Curso {
	
	private long id;
	private String nome;
	private String matricula;
	private String nivel;
	private int peridiocidade;
	private ArrayList<Coordenador> coordenador;
	private ArrayList<Orientador> orientador;
	private ArrayList<Disciplina> disciplina;
	private ArrayList<Aluno> aluno;
}
