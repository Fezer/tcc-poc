package br.ufpr.estagio.poc.model;

import java.util.Date;

public class TermoPocDTO {
	private long id;
	private String statusTermo;
	private String etapaFluxo;
	private String grrAluno;
	private String tipoEstagio;
	private boolean estagioUfpr;
	private int jornadaDiaria;
	private int jornadaSemanal;
	private Date dataInicio;
	private Date dataTermino;
	private float valorBolsa;
	private float valorTransporte;
	private String nomeSupervisor;
	private String telefoneSupervisor;
	private String formacaoSupervisor;
	private String nomeOrientador;
	private String departamentoOrientador;
	private String atividadesEstagio;
	private String nomeContratante;
	private String tipoContratante;
	private String cnpjContratente;
	private String telefoneContratante;
	private String numeroApolice;
	private String enderecoContratente;
	private String cidadeContratante;
	private String estadoContratante;
	
	public TermoPocDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoPocDTO(long id, String statusTermo, String etapaFluxo, String grrAluno, String tipoEstagio,
			boolean estagioUfpr, int jornadaDiaria, int jornadaSemanal, Date dataInicio, Date dataTermino,
			float valorBolsa, float valorTransporte, String nomeSupervisor, String telefoneSupervisor,
			String formacaoSupervisor, String nomeOrientador, String departamentoOrientador, String atividadesEstagio,
			String nomeContratante, String tipoContratante, String cnpjContratente, String telefoneContratante,
			String numeroApolice, String enderecoContratente, String cidadeContratante, String estadoContratante) {
		super();
		this.id = id;
		this.statusTermo = statusTermo;
		this.etapaFluxo = etapaFluxo;
		this.grrAluno = grrAluno;
		this.tipoEstagio = tipoEstagio;
		this.estagioUfpr = estagioUfpr;
		this.jornadaDiaria = jornadaDiaria;
		this.jornadaSemanal = jornadaSemanal;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.valorBolsa = valorBolsa;
		this.valorTransporte = valorTransporte;
		this.nomeSupervisor = nomeSupervisor;
		this.telefoneSupervisor = telefoneSupervisor;
		this.formacaoSupervisor = formacaoSupervisor;
		this.nomeOrientador = nomeOrientador;
		this.departamentoOrientador = departamentoOrientador;
		this.atividadesEstagio = atividadesEstagio;
		this.nomeContratante = nomeContratante;
		this.tipoContratante = tipoContratante;
		this.cnpjContratente = cnpjContratente;
		this.telefoneContratante = telefoneContratante;
		this.numeroApolice = numeroApolice;
		this.enderecoContratente = enderecoContratente;
		this.cidadeContratante = cidadeContratante;
		this.estadoContratante = estadoContratante;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatusTermo() {
		return statusTermo;
	}

	public void setStatusTermo(String statusTermo) {
		this.statusTermo = statusTermo;
	}

	public String getEtapaFluxo() {
		return etapaFluxo;
	}

	public void setEtapaFluxo(String etapaFluxo) {
		this.etapaFluxo = etapaFluxo;
	}

	public String getGrrAluno() {
		return grrAluno;
	}

	public void setGrrAluno(String grrAluno) {
		this.grrAluno = grrAluno;
	}

	public String getTipoEstagio() {
		return tipoEstagio;
	}

	public void setTipoEstagio(String tipoEstagio) {
		this.tipoEstagio = tipoEstagio;
	}

	public boolean isEstagioUfpr() {
		return estagioUfpr;
	}

	public void setEstagioUfpr(boolean estagioUfpr) {
		this.estagioUfpr = estagioUfpr;
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

	public String getNomeSupervisor() {
		return nomeSupervisor;
	}

	public void setNomeSupervisor(String nomeSupervisor) {
		this.nomeSupervisor = nomeSupervisor;
	}

	public String getTelefoneSupervisor() {
		return telefoneSupervisor;
	}

	public void setTelefoneSupervisor(String telefoneSupervisor) {
		this.telefoneSupervisor = telefoneSupervisor;
	}

	public String getFormacaoSupervisor() {
		return formacaoSupervisor;
	}

	public void setFormacaoSupervisor(String formacaoSupervisor) {
		this.formacaoSupervisor = formacaoSupervisor;
	}

	public String getNomeOrientador() {
		return nomeOrientador;
	}

	public void setNomeOrientador(String nomeOrientador) {
		this.nomeOrientador = nomeOrientador;
	}

	public String getDepartamentoOrientador() {
		return departamentoOrientador;
	}

	public void setDepartamentoOrientador(String departamentoOrientador) {
		this.departamentoOrientador = departamentoOrientador;
	}

	public String getAtividadesEstagio() {
		return atividadesEstagio;
	}

	public void setAtividadesEstagio(String atividadesEstagio) {
		this.atividadesEstagio = atividadesEstagio;
	}

	public String getNomeContratante() {
		return nomeContratante;
	}

	public void setNomeContratante(String nomeContratante) {
		this.nomeContratante = nomeContratante;
	}

	public String getTipoContratante() {
		return tipoContratante;
	}

	public void setTipoContratante(String tipoContratante) {
		this.tipoContratante = tipoContratante;
	}

	public String getCnpjContratente() {
		return cnpjContratente;
	}

	public void setCnpjContratente(String cnpjContratente) {
		this.cnpjContratente = cnpjContratente;
	}

	public String getTelefoneContratante() {
		return telefoneContratante;
	}

	public void setTelefoneContratante(String telefoneContratante) {
		this.telefoneContratante = telefoneContratante;
	}

	public String getNumeroApolice() {
		return numeroApolice;
	}

	public void setNumeroApolice(String numeroApolice) {
		this.numeroApolice = numeroApolice;
	}

	public String getEnderecoContratente() {
		return enderecoContratente;
	}

	public void setEnderecoContratente(String enderecoContratente) {
		this.enderecoContratente = enderecoContratente;
	}

	public String getCidadeContratante() {
		return cidadeContratante;
	}

	public void setCidadeContratante(String cidadeContratante) {
		this.cidadeContratante = cidadeContratante;
	}

	public String getEstadoContratante() {
		return estadoContratante;
	}

	public void setEstadoContratante(String estadoContratante) {
		this.estadoContratante = estadoContratante;
	}
	
}
