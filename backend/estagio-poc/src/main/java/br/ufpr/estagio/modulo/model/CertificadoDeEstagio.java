package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class CertificadoDeEstagio implements Serializable {
	
	private long id;
	private Estagio estagio;
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
