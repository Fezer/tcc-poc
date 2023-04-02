package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Disciplina implements Serializable{
	private long id;
	private ArrayList<Aluno> aluno;
	private Curso curso;
}
