package br.ufpr.estagio.modulo.model;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "termopoc")

public class TermoPoc extends RepresentationModel<TermoPoc> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	
	@Column(name = "id")
	private long id;
	@Column(name = "tipoEstagio")
	private String tipoEstagio;
	@Column(name = "statusEstagio")
	private String statusEstagio;
	@Column(name = "estagioUfpr")
	private boolean estagioUfpr;
	@Column(name = "grrAluno")
	private String grrAluno;
	@Column(name = "tipoTermoDeEstagio")
	private String tipoTermoDeEstagio;
	@Column(name = "statusTermo")
	private String statusTermo;
	@Column(name = "etapaFluxo")
	private String etapaFluxo;
	@Column(name = "parecerCOE")
	private String parecerCOE;
	@Column(name = "parecerCOAFE")
	private String parecerCOAFE;
	@Column(name = "parecerCoordenacao")
	private String parecerCoordenacao;
	@Column(name = "motivoIndeferimento")
	private String motivoIndeferimento;
	@Column(name = "descricaoAjustes")
	private String descricaoAjustes;
	@Column(name = "jornadaDiaria")
	private int jornadaDiaria;
	@Column(name = "jornadaSemanal")
	private int jornadaSemanal;
	@Column(name = "dataInicio")
	private Date dataInicio;
	@Column(name = "dataTermino")
	private Date dataTermino;
	@Column(name = "valorBolsa")
	private float valorBolsa;
	@Column(name = "valorTransporte")
	private float valorTransporte;
	@Column(name = "nomeSupervisor")
	private String nomeSupervisor;
	@Column(name = "telefoneSupervisor")
	private String telefoneSupervisor;
	@Column(name = "formacaoSupervisor")
	private String formacaoSupervisor;
	@Column(name = "nomeOrientador")
	private String nomeOrientador;
	@Column(name = "departamentoOrientador")
	private String departamentoOrientador;
	@Column(name = "atividadesEstagio")
	private String atividadesEstagio;
	@Column(name = "nomeContratante")
	private String nomeContratante;
	@Column(name = "tipoContratante")
	private String tipoContratante;
	@Column(name = "cnpjContratente")
	private String cnpjContratente;
	@Column(name = "telefoneContratante")
	private String telefoneContratante;
	@Column(name = "numeroApolice")
	private String numeroApolice;
	@Column(name = "enderecoContratente")
	private String enderecoContratente;
	@Column(name = "cidadeContratante")
	private String cidadeContratante;
	@Column(name = "estadoContratante")
	private String estadoContratante;
	
	public TermoPoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoPoc(long id, String tipoEstagio, String statusEstagio, boolean estagioUfpr, String grrAluno,
			String tipoTermoDeEstagio, String statusTermo, String etapaFluxo, String parecerCOE, String parecerCOAFE,
			String parecerCoordenacao, String motivoIndeferimento, String descricaoAjustes, int jornadaDiaria,
			int jornadaSemanal, Date dataInicio, Date dataTermino, float valorBolsa, float valorTransporte,
			String nomeSupervisor, String telefoneSupervisor, String formacaoSupervisor, String nomeOrientador,
			String departamentoOrientador, String atividadesEstagio, String nomeContratante, String tipoContratante,
			String cnpjContratente, String telefoneContratante, String numeroApolice, String enderecoContratente,
			String cidadeContratante, String estadoContratante) {
		super();
		this.id = id;
		this.tipoEstagio = tipoEstagio;
		this.statusEstagio = statusEstagio;
		this.estagioUfpr = estagioUfpr;
		this.grrAluno = grrAluno;
		this.tipoTermoDeEstagio = tipoTermoDeEstagio;
		this.statusTermo = statusTermo;
		this.etapaFluxo = etapaFluxo;
		this.parecerCOE = parecerCOE;
		this.parecerCOAFE = parecerCOAFE;
		this.parecerCoordenacao = parecerCoordenacao;
		this.motivoIndeferimento = motivoIndeferimento;
		this.descricaoAjustes = descricaoAjustes;
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

	public String getTipoEstagio() {
		return tipoEstagio;
	}

	public void setTipoEstagio(String tipoEstagio) {
		this.tipoEstagio = tipoEstagio;
	}

	public String getStatusEstagio() {
		return statusEstagio;
	}

	public void setStatusEstagio(String statusEstagio) {
		this.statusEstagio = statusEstagio;
	}

	public boolean isEstagioUfpr() {
		return estagioUfpr;
	}

	public void setEstagioUfpr(boolean estagioUfpr) {
		this.estagioUfpr = estagioUfpr;
	}

	public String getGrrAluno() {
		return grrAluno;
	}

	public void setGrrAluno(String grrAluno) {
		this.grrAluno = grrAluno;
	}

	public String getTipoTermoDeEstagio() {
		return tipoTermoDeEstagio;
	}

	public void setTipoTermoDeEstagio(String tipoTermoDeEstagio) {
		this.tipoTermoDeEstagio = tipoTermoDeEstagio;
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

	public String getParecerCOE() {
		return parecerCOE;
	}

	public void setParecerCOE(String parecerCOE) {
		this.parecerCOE = parecerCOE;
	}

	public String getParecerCOAFE() {
		return parecerCOAFE;
	}

	public void setParecerCOAFE(String parecerCOAFE) {
		this.parecerCOAFE = parecerCOAFE;
	}

	public String getParecerCoordenacao() {
		return parecerCoordenacao;
	}

	public void setParecerCoordenacao(String parecerCoordenacao) {
		this.parecerCoordenacao = parecerCoordenacao;
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

	public static Long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
