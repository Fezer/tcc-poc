package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class AgenteIntegrador extends Pessoa implements Serializable {
	
	private long id;
	private String cnpj;
	private ArrayList<Convenio> convenio;
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	private ArrayList<Estagio> estagio;

}
