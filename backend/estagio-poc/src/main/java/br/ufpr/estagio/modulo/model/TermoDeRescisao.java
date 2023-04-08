package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
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

	//@OneToOne
	//@JoinColumn(name="estagio")
	@OneToOne
	@JoinColumn(name="estagio_id", referencedColumnName="id", nullable=false)
	private Estagio estagio;
	
	@Column(name = "data_termino")
	private Date dataTermino;
	
	@Column(name = "periodo_total_recesso")
	private int periodoTotalRecesso;
	
	//@Column(name = "periodo_recesso")
	//@OneToMany(mappedBy="termo_de_rescisao")
	@OneToMany(mappedBy="termoRescisao", cascade=CascadeType.ALL)
	private ArrayList<PeriodoRecesso> periodoRecesso;
	
	@Column(name = "ciencia_orientador")
	private boolean cienciaOrientador;
	
	@Column(name = "ciencia_coordenador")
	private boolean cienciaCoordenador;
	
	@Column(name = "ciencia_coe")
	private boolean cienciaCOE;
	
	@Column(name = "ciencia_coafe")
	private boolean cienciaCOAFE;
	
	public TermoDeRescisao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoDeRescisao(long id, Estagio estagio, Date dataTermino, int periodoTotalRecesso,
			ArrayList<PeriodoRecesso> periodoRecesso, boolean cienciaOrientador, boolean cienciaCoordenador,
			boolean cienciaCOE, boolean cienciaCOAFE) {
		super();
		this.id = id;
		this.estagio = estagio;
		this.dataTermino = dataTermino;
		this.periodoTotalRecesso = periodoTotalRecesso;
		this.periodoRecesso = periodoRecesso;
		this.cienciaOrientador = cienciaOrientador;
		this.cienciaCoordenador = cienciaCoordenador;
		this.cienciaCOE = cienciaCOE;
		this.cienciaCOAFE = cienciaCOAFE;
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

	public ArrayList<PeriodoRecesso> getPeriodoRecesso() {
		return periodoRecesso;
	}

	public void setPeriodoRecesso(ArrayList<PeriodoRecesso> periodoRecesso) {
		this.periodoRecesso = periodoRecesso;
	}

	public boolean isCienciaOrientador() {
		return cienciaOrientador;
	}

	public void setCienciaOrientador(boolean cienciaOrientador) {
		this.cienciaOrientador = cienciaOrientador;
	}

	public boolean isCienciaCoordenador() {
		return cienciaCoordenador;
	}

	public void setCienciaCoordenador(boolean cienciaCoordenador) {
		this.cienciaCoordenador = cienciaCoordenador;
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
	
}
