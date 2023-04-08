package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "certificado_de_estagio", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class CertificadoDeEstagio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	//@Column(name = "id_estagio")
	@OneToOne
	@JoinColumn(name="estagio_id", referencedColumnName="id", nullable=false)
	private Estagio estagio;
	
	// nao tem a coluna no diagrama
	@Column(name = "aprovado_coe")
	private boolean aprovadoCOE;
	
	public CertificadoDeEstagio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificadoDeEstagio(long id, Estagio estagio, boolean aprovadoCOE) {
		super();
		this.id = id;
		this.estagio = estagio;
		this.aprovadoCOE = aprovadoCOE;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public boolean isAprovadoCOE() {
		return aprovadoCOE;
	}

	public void setAprovadoCOE(boolean aprovadoCOE) {
		this.aprovadoCOE = aprovadoCOE;
	}

}
