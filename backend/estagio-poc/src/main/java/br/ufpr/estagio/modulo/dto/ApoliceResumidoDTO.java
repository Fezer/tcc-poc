package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.Date;

import br.ufpr.estagio.modulo.model.Seguradora;

public class ApoliceResumidoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private int numero;
	private Date dataInicio;
	private Date dataFim;
	private Seguradora seguradora;
	
	public ApoliceResumidoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApoliceResumidoDTO(long id, int numero, Date dataInicio, Date dataFim) {
		super();
		this.id = id;
		this.numero = numero;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public ApoliceResumidoDTO(long id, int numero, Date dataInicio, Date dataFim, Seguradora seguradora) {
		super();
		this.id = id;
		this.numero = numero;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.seguradora = seguradora;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
