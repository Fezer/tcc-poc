package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

public class PeriodoRecesso implements Serializable{

	private long id;
	private Date dataInicio;
	private Date dataFim;
	private TermoDeRescisao termoRescisao;
	
	public PeriodoRecesso() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PeriodoRecesso(long id, Date dataInicio, Date dataFim, TermoDeRescisao termoRescisao) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.termoRescisao = termoRescisao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public TermoDeRescisao getTermoRescisao() {
		return termoRescisao;
	}

	public void setTermoRescisao(TermoDeRescisao termoRescisao) {
		this.termoRescisao = termoRescisao;
	}
	
}
