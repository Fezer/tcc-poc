package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "termo_de_rescisao", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class TermoDeRescisao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "etapa_fluxo")
	private EnumEtapaFluxo etapaFluxo;

	@JsonIgnore
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="estagio_id", referencedColumnName="id", nullable=true)
	private Estagio estagio;
	
	@Column(name = "data_termino")
	private Date dataTermino;
	
	@Column(name = "periodo_total_recesso")
	private int periodoTotalRecesso;
	
	@JsonIgnore
	@OneToMany(mappedBy="termoRescisao", cascade=CascadeType.REMOVE)
	private List<PeriodoRecesso> periodoRecesso;
	
	@Column(name = "ciencia_orientador")
	private boolean cienciaOrientador;
	
	@Column(name = "ciencia_coordenacao")
	private boolean cienciaCoordenacao;
	
	@Column(name = "ciencia_coe")
	private boolean cienciaCOE;
	
	@Column(name = "ciencia_coafe")
	private boolean cienciaCOAFE;
	
	@Column(name = "upload")
	private boolean upload;
	
	private List<String> arquivos;
	
	public TermoDeRescisao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoDeRescisao(long id, EnumEtapaFluxo etapaFluxo, Estagio estagio, Date dataTermino,
			int periodoTotalRecesso, List<PeriodoRecesso> periodoRecesso, boolean cienciaOrientador,
			boolean cienciaCoordenacao, boolean cienciaCOE, boolean cienciaCOAFE) {
		super();
		this.id = id;
		this.etapaFluxo = etapaFluxo;
		this.estagio = estagio;
		this.dataTermino = dataTermino;
		this.periodoTotalRecesso = periodoTotalRecesso;
		this.periodoRecesso = periodoRecesso;
		this.cienciaOrientador = cienciaOrientador;
		this.cienciaCoordenacao = cienciaCoordenacao;
		this.cienciaCOE = cienciaCOE;
		this.cienciaCOAFE = cienciaCOAFE;
		this.upload = false;
	}

	public TermoDeRescisao(long id, EnumEtapaFluxo etapaFluxo, Estagio estagio, Date dataTermino,
			int periodoTotalRecesso, List<PeriodoRecesso> periodoRecesso, boolean cienciaOrientador,
			boolean cienciaCoordenacao, boolean cienciaCOE, boolean cienciaCOAFE, boolean upload) {
		super();
		this.id = id;
		this.etapaFluxo = etapaFluxo;
		this.estagio = estagio;
		this.dataTermino = dataTermino;
		this.periodoTotalRecesso = periodoTotalRecesso;
		this.periodoRecesso = periodoRecesso;
		this.cienciaOrientador = cienciaOrientador;
		this.cienciaCoordenacao = cienciaCoordenacao;
		this.cienciaCOE = cienciaCOE;
		this.cienciaCOAFE = cienciaCOAFE;
		this.upload = false;
	}

	public TermoDeRescisao(long id, EnumEtapaFluxo etapaFluxo, Estagio estagio, Date dataTermino,
			int periodoTotalRecesso, List<PeriodoRecesso> periodoRecesso, boolean cienciaOrientador,
			boolean cienciaCoordenacao, boolean cienciaCOE, boolean cienciaCOAFE, boolean upload,
			List<String> arquivos) {
		super();
		this.id = id;
		this.etapaFluxo = etapaFluxo;
		this.estagio = estagio;
		this.dataTermino = dataTermino;
		this.periodoTotalRecesso = periodoTotalRecesso;
		this.periodoRecesso = periodoRecesso;
		this.cienciaOrientador = cienciaOrientador;
		this.cienciaCoordenacao = cienciaCoordenacao;
		this.cienciaCOE = cienciaCOE;
		this.cienciaCOAFE = cienciaCOAFE;
		this.upload = upload;
		this.arquivos = arquivos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EnumEtapaFluxo getEtapaFluxo() {
		return etapaFluxo;
	}

	public void setEtapaFluxo(EnumEtapaFluxo etapaFluxo) {
		this.etapaFluxo = etapaFluxo;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public int getPeriodoTotalRecesso() {
		return periodoTotalRecesso;
	}

	public void setPeriodoTotalRecesso(int periodoTotalRecesso) {
		this.periodoTotalRecesso = periodoTotalRecesso;
	}

	public List<PeriodoRecesso> getPeriodoRecesso() {
		return periodoRecesso;
	}

	public void setPeriodoRecesso(List<PeriodoRecesso> periodoRecesso) {
		this.periodoRecesso = periodoRecesso;
	}

	public boolean isCienciaOrientador() {
		return cienciaOrientador;
	}

	public void setCienciaOrientador(boolean cienciaOrientador) {
		this.cienciaOrientador = cienciaOrientador;
	}

	public boolean isCienciaCoordenacao() {
		return cienciaCoordenacao;
	}

	public void setCienciaCoordenacao(boolean cienciaCoordenacao) {
		this.cienciaCoordenacao = cienciaCoordenacao;
	}

	public boolean isCienciaCOE() {
		return cienciaCOE;
	}

	public void setCienciaCOE(boolean cienciaCOE) {
		this.cienciaCOE = cienciaCOE;
	}

	public boolean isCienciaCOAFE() {
		return cienciaCOAFE;
	}

	public void setCienciaCOAFE(boolean cienciaCOAFE) {
		this.cienciaCOAFE = cienciaCOAFE;
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
