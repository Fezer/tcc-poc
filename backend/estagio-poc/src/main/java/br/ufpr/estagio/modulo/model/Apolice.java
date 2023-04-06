package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "apolice", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Apolice implements Serializable {

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
	
	//@OneToOne
	//@JoinColumn(name="id_seguradora")
	@OneToOne(mappedBy="apolice")
	private Seguradora seguradora;
	
	// REVER DIAGRAMA
	//@OneToOne
	//@JoinColumn(name="id_termo_de_estagio")
	@OneToOne(mappedBy="apolice")
	private TermoDeEstagio termoDeEstagio;
	
	// REVER DIAGRAMA + o que eh apolicecol (ou algo assim)?
	//@OneToOne
	//@JoinColumn(name="id_estagio")
	@OneToOne(mappedBy="apolice")
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
