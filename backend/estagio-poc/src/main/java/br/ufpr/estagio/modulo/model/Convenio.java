package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

public class Convenio implements Serializable{
	
	private long id;
	private int numero;
	private String descricao;
	private Date dataInicio;
	private Date dataFim;
	private AgenteIntegrador agenteIntegrador;
}
