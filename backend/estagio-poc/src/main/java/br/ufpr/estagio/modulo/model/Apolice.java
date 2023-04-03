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
	
	public Apolice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Apolice(long id, int numero, Date dataInicio, Date dataFim, Seguradora seguradora,
			TermoDeEstagio termoDeEstagio, Estagio estagio) {
		super();
		this.id = id;
		this.numero = numero;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.seguradora = seguradora;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Seguradora getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(Seguradora seguradora) {
		this.seguradora = seguradora;
	}

	public TermoDeEstagio getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(TermoDeEstagio termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}	
	
}
