package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Seguradora implements Serializable{
	
	private long id;
	private String nome;
	private ArrayList<Apolice> apolice;
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	private ArrayList<Estagio> estagio;
	
}
