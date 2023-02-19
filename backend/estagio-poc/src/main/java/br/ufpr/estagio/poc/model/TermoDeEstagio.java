package br.ufpr.estagio.poc.model;

import java.sql.Date;

public class TermoDeEstagio {
	private int id;
	private static final String tipoTermoDeEstagio = "TermoDeCompromisso";
	
	private String seguradora;
	private String agenteIntegrador;
	private Estagio estagio;
	private String coordenadorCurso;
	private Date dataInicio;
	private Date dataTermino;
	private int jornadaDiaria;
	private int jornadaSemanal;
	private float valorBolsa;
	private float valorTransporte;
	private Date dataFimSuspensao;
	private Date dataInicioRetomada;
	private Date dataCriacao;
	private String motivoIndeferimento;
	private String descricaoAjustes;
	private String orientador;
	private String supervisor;
	private String coordenador;
//	private String planoDeAtividade;
	private boolean cienciaCoordenacao;

	public TermoDeEstagio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoDeEstagio(int id, String seguradora, String agenteIntegrador, Estagio estagio, String coordenadorCurso,
			Date dataInicio, Date dataTermino, int jornadaDiaria, int jornadaSemanal, float valorBolsa,
			float valorTransporte, Date dataFimSuspensao, Date dataInicioRetomada, Date dataCriacao,
			String motivoIndeferimento, String descricaoAjustes, String orientador, String supervisor,
			String coordenador, boolean cienciaCoordenacao) {
		super();
		this.id = id;
		this.seguradora = seguradora;
		this.agenteIntegrador = agenteIntegrador;
		this.estagio = estagio;
		this.coordenadorCurso = coordenadorCurso;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.jornadaDiaria = jornadaDiaria;
		this.jornadaSemanal = jornadaSemanal;
		this.valorBolsa = valorBolsa;
		this.valorTransporte = valorTransporte;
		this.dataFimSuspensao = dataFimSuspensao;
		this.dataInicioRetomada = dataInicioRetomada;
		this.dataCriacao = dataCriacao;
		this.motivoIndeferimento = motivoIndeferimento;
		this.descricaoAjustes = descricaoAjustes;
		this.orientador = orientador;
		this.supervisor = supervisor;
		this.coordenador = coordenador;
		this.cienciaCoordenacao = cienciaCoordenacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(String seguradora) {
		this.seguradora = seguradora;
	}

	public String getAgenteIntegrador() {
		return agenteIntegrador;
	}

	public void setAgenteIntegrador(String agenteIntegrador) {
		this.agenteIntegrador = agenteIntegrador;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public String getCoordenadorCurso() {
		return coordenadorCurso;
	}

	public void setCoordenadorCurso(String coordenadorCurso) {
		this.coordenadorCurso = coordenadorCurso;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public int getJornadaDiaria() {
		return jornadaDiaria;
	}

	public void setJornadaDiaria(int jornadaDiaria) {
		this.jornadaDiaria = jornadaDiaria;
	}

	public int getJornadaSemanal() {
		return jornadaSemanal;
	}

	public void setJornadaSemanal(int jornadaSemanal) {
		this.jornadaSemanal = jornadaSemanal;
	}

	public float getValorBolsa() {
		return valorBolsa;
	}

	public void setValorBolsa(float valorBolsa) {
		this.valorBolsa = valorBolsa;
	}

	public float getValorTransporte() {
		return valorTransporte;
	}

	public void setValorTransporte(float valorTransporte) {
		this.valorTransporte = valorTransporte;
	}

	public Date getDataFimSuspensao() {
		return dataFimSuspensao;
	}

	public void setDataFimSuspensao(Date dataFimSuspensao) {
		this.dataFimSuspensao = dataFimSuspensao;
	}

	public Date getDataInicioRetomada() {
		return dataInicioRetomada;
	}

	public void setDataInicioRetomada(Date dataInicioRetomada) {
		this.dataInicioRetomada = dataInicioRetomada;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getMotivoIndeferimento() {
		return motivoIndeferimento;
	}

	public void setMotivoIndeferimento(String motivoIndeferimento) {
		this.motivoIndeferimento = motivoIndeferimento;
	}

	public String getDescricaoAjustes() {
		return descricaoAjustes;
	}

	public void setDescricaoAjustes(String descricaoAjustes) {
		this.descricaoAjustes = descricaoAjustes;
	}

	public String getOrientador() {
		return orientador;
	}

	public void setOrientador(String orientador) {
		this.orientador = orientador;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(String coordenador) {
		this.coordenador = coordenador;
	}

	public boolean isCienciaCoordenacao() {
		return cienciaCoordenacao;
	}

	public void setCienciaCoordenacao(boolean cienciaCoordenacao) {
		this.cienciaCoordenacao = cienciaCoordenacao;
	}

	public static String getTipotermodeestagio() {
		return tipoTermoDeEstagio;
	}

	

}
