package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumParecerAprovadores;
import br.ufpr.estagio.modulo.model.Estagio;


public class CertificadoDeEstagioDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private Estagio estagio;
	private EnumEtapaFluxo etapaFluxo;
	private EnumParecerAprovadores parecerCOE;
	private String motivoReprovacao;
	private boolean upload;
	
	public CertificadoDeEstagioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificadoDeEstagioDTO(long id, Estagio estagio, EnumEtapaFluxo etapaFluxo,
			EnumParecerAprovadores parecerCOE, String motivoReprovacao, boolean upload) {
		super();
		this.id = id;
		this.estagio = estagio;
		this.etapaFluxo = etapaFluxo;
		this.parecerCOE = parecerCOE;
		this.motivoReprovacao = motivoReprovacao;
		this.upload = upload;
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

	public EnumEtapaFluxo getEtapaFluxo() {
		return etapaFluxo;
	}

	public void setEtapaFluxo(EnumEtapaFluxo etapaFluxo) {
		this.etapaFluxo = etapaFluxo;
	}

	public EnumParecerAprovadores getParecerCOE() {
		return parecerCOE;
	}

	public void setParecerCOE(EnumParecerAprovadores parecerCOE) {
		this.parecerCOE = parecerCOE;
	}

	public String getMotivoReprovacao() {
		return motivoReprovacao;
	}

	public void setMotivoReprovacao(String motivoReprovacao) {
		this.motivoReprovacao = motivoReprovacao;
	}

	public boolean isUpload() {
		return upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
