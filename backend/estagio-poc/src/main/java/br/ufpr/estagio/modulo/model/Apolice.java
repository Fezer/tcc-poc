	package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "apolice", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Apolice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "numero")
	private int numero;
	
	@Column(name = "dataInicio")
	private Date dataInicio;
	
	@Column(name = "dataFim")
	private Date dataFim;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="seguradora_id", referencedColumnName="id", nullable=true)
	private Seguradora seguradora;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="termo_de_estagio_id", referencedColumnName="id", nullable=true)
	private TermoDeEstagio termoDeEstagio;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="estagio_id", referencedColumnName="id", nullable=true)
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
