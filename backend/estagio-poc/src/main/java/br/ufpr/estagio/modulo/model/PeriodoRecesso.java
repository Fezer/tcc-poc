package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "periodo_recesso", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class PeriodoRecesso implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "dataInicio")
	private Date dataInicio;
	
	@Column(name = "dataFim")
	private Date dataFim;
	
	@Column(name = "termo_de_rescisao")
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
