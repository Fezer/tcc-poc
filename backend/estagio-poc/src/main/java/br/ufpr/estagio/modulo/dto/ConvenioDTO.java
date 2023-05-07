package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.Date;

import br.ufpr.estagio.modulo.model.AgenteIntegrador;

public class ConvenioDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;

	private int numero;
	
	private String descricao;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private AgenteIntegrador agenteIntegrador;

	public ConvenioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConvenioDTO(long id, int numero, String descricao, Date dataInicio, Date dataFim,
			AgenteIntegrador agenteIntegrador) {
		super();
		this.id = id;
		this.numero = numero;
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.agenteIntegrador = agenteIntegrador;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public AgenteIntegrador getAgenteIntegrador() {
		return agenteIntegrador;
	}

	public void setAgenteIntegrador(AgenteIntegrador agenteIntegrador) {
		this.agenteIntegrador = agenteIntegrador;
	}
}
