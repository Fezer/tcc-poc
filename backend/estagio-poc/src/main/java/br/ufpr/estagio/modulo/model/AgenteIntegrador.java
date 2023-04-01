package br.ufpr.estagio.modulo.model;

import java.util.ArrayList;

public class AgenteIntegrador extends Pessoa{
	
	private long id;
	private String cnpj;
	private ArrayList<Convenio> convenio;
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	private ArrayList<Estagio> estagio;

}
