package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Aluno extends Pessoa implements Serializable {
	private long id;
	private String matricula;
	private String rg;
	private String cpf;
	private String email;
	private Date dataNascimento;
	private Curso curso;
	private DadosAuxiliares dadosAuxiliares;
	private DadosBancarios dadosBancarios;
	private ArrayList<Disciplina> disciplina;
}
