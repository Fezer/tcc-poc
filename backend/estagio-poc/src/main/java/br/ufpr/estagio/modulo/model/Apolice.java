package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

public class Apolice implements Serializable {

	private long id;
	private int numero;
	private Date dataInicio;
	private Date dataFim;
	private Seguradora seguradora;
	private TermoDeEstagio termoDeEstagio;
	private Estagio estagio;	
	
}
