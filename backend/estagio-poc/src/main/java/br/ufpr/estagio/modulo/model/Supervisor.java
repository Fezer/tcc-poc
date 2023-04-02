package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Supervisor extends Pessoa implements Serializable{
	
	private long id;
	private String cpf;
	private String formacao;
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	private ArrayList<Estagio> estagio;
	
	
}
