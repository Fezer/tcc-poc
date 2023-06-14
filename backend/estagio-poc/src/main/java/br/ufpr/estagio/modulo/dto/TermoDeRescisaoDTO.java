package br.ufpr.estagio.modulo.dto;

import java.util.ArrayList;
import java.util.Date;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.PeriodoRecesso;

public class TermoDeRescisaoDTO {

	private long id;
	private String aluno;
	private String grrAluno;
	private Estagio estagio;
	private EnumEtapaFluxo etapaFluxo;
	private Date dataTermino;
	private int periodoTotalRecesso;
	private ArrayList<PeriodoRecesso> periodoRecesso;
	private boolean cienciaOrientador;
	private boolean cienciaCoordenacao;
	private boolean cienciaCOE;
	private boolean cienciaCOAFE;
	private boolean upload;
	
	public TermoDeRescisaoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoDeRescisaoDTO(long id, String aluno, String grrAluno, Estagio estagio, EnumEtapaFluxo etapaFluxo,
			Date dataTermino, int periodoTotalRecesso, ArrayList<PeriodoRecesso> periodoRecesso,
			boolean cienciaOrientador, boolean cienciaCoordenacao, boolean cienciaCOE, boolean cienciaCOAFE, boolean upload) {
		super();
		this.id = id;
		this.aluno = aluno;
		this.grrAluno = grrAluno;
		this.estagio = estagio;
		this.etapaFluxo = etapaFluxo;
		this.dataTermino = dataTermino;
		this.periodoTotalRecesso = periodoTotalRecesso;
		this.periodoRecesso = periodoRecesso;
		this.cienciaOrientador = cienciaOrientador;
		this.cienciaCoordenacao = cienciaCoordenacao;
		this.cienciaCOE = cienciaCOE;
		this.cienciaCOAFE = cienciaCOAFE;
		this.upload = upload;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	public String getGrrAluno() {
		return grrAluno;
	}

	public void setGrrAluno(String grrAluno) {
		this.grrAluno = grrAluno;
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
	
}
