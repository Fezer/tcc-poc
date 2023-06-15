package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumParecerAprovadores;
import jakarta.persistence.CascadeType;
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

	@JsonIgnore
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="estagio_id", referencedColumnName="id", nullable=true)
	private Estagio estagio;
	
	@Column(name = "etapa_fluxo")
	private EnumEtapaFluxo etapaFluxo;

	@Column(name = "parecer_coe")
	private EnumParecerAprovadores parecerCOE;
	
	@Column(name = "upload")
	private boolean upload;
	
	@Column(name = "movitvo_reprovacao", length = 1000)
	private String motivoReprovacao;
	
	private List<String> arquivos;
	
	public CertificadoDeEstagio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificadoDeEstagio(long id, Estagio estagio, EnumEtapaFluxo etapaFluxo, EnumParecerAprovadores parecerCOE,
			String motivoReprovacao) {
		super();
		this.id = id;
		this.estagio = estagio;
		this.etapaFluxo = etapaFluxo;
		this.parecerCOE = parecerCOE;
		this.motivoReprovacao = motivoReprovacao;
	}

	public CertificadoDeEstagio(long id, Estagio estagio, EnumEtapaFluxo etapaFluxo, EnumParecerAprovadores parecerCOE,
			String motivoReprovacao, boolean upload) {
		super();
		this.id = id;
		this.estagio = estagio;
		this.etapaFluxo = etapaFluxo;
		this.parecerCOE = parecerCOE;
		this.motivoReprovacao = motivoReprovacao;
		this.upload = false;
	}

	public CertificadoDeEstagio(long id, Estagio estagio, EnumEtapaFluxo etapaFluxo, EnumParecerAprovadores parecerCOE,
			boolean upload, String motivoReprovacao, List<String> arquivos) {
		super();
		this.id = id;
		this.estagio = estagio;
		this.etapaFluxo = etapaFluxo;
		this.parecerCOE = parecerCOE;
		this.upload = upload;
		this.motivoReprovacao = motivoReprovacao;
		this.arquivos = arquivos;
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

	public List<String> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<String> arquivos) {
		this.arquivos = arquivos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
