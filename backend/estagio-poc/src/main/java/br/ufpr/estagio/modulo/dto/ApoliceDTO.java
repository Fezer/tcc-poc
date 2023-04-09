package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.Date;

public class ApoliceDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private int numero;
	private Date dataInicio;
	private Date dataFim;
	
	public ApoliceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApoliceDTO(long id, int numero, Date dataInicio, Date dataFim) {
		super();
		this.id = id;
		this.numero = numero;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
