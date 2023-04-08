package br.ufpr.estagio.modulo.dto;

import java.util.ArrayList;
import java.util.Date;

import br.ufpr.estagio.modulo.model.Estagio;

public class TermoDeRescisaoDTO {

	private long id;
	private Estagio estagio;
	private Date dataTermino;
	private int periodoTotalRecesso;
	private ArrayList<PeriodoRecessoDTO> periodoRecesso;
	private boolean cienciaOrientador;
	private boolean cienciaCoordenador;
	private boolean cienciaCOE;
	private boolean cienciaCOAFE;
	
	public TermoDeRescisaoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoDeRescisaoDTO(long id, Estagio estagio, Date dataTermino, int periodoTotalRecesso,
			ArrayList<PeriodoRecessoDTO> periodoRecesso, boolean cienciaOrientador, boolean cienciaCoordenador,
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

	public ArrayList<PeriodoRecessoDTO> getPeriodoRecesso() {
		return periodoRecesso;
	}

	public void setPeriodoRecesso(ArrayList<PeriodoRecessoDTO> periodoRecesso) {
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
