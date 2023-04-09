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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "convenio", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Convenio implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "numero")
	private int numero;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "dataInicio")
	private Date dataInicio;
	
	@Column(name = "dataFim")
	private Date dataFim;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="agente_integrador_id", referencedColumnName="id", nullable=true)
	private AgenteIntegrador agenteIntegrador;

	public Convenio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Convenio(long id, int numero, String descricao, Date dataInicio, Date dataFim,
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
