package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class PlanoDeAtividades implements Serializable{
	
	private long id;
	private String local;
	private Supervisor supervisor;
	private String descricaoAtividades;
	private Estagio estagio;
	private TermoDeEstagio termoDeEstagio;

}
