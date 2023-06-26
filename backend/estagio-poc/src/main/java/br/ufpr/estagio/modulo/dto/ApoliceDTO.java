package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.Date;

import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Seguradora;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public class ApoliceDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private int numero;
	private Date dataInicio;
	private Date dataFim;
	private Seguradora seguradora;
	private TermoDeEstagio termoDeEstagio;
	private Estagio estagio;
	private boolean ativo;
	
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

	public ApoliceDTO(long id, int numero, Date dataInicio, Date dataFim, Seguradora seguradora,
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

	public ApoliceDTO(long id, int numero, Date dataInicio, Date dataFim, Seguradora seguradora,
			TermoDeEstagio termoDeEstagio, Estagio estagio, boolean ativo) {
		super();
		this.id = id;
		this.numero = numero;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.seguradora = seguradora;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
		this.ativo = ativo;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
