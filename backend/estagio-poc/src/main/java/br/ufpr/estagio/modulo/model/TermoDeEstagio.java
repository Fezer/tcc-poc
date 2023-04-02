package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumParecerAprovadores;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;

@Entity
@Table(name = "TermoDeEstagio", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class TermoDeEstagio extends RepresentationModel<TermoDeEstagio> implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "tipoTermoDeEstagio")
	private EnumTipoTermoDeEstagio tipoTermoDeEstagio;
	
	@OneToOne
	private Estagio estagio;
	
	@Column(name = "seguradora")
	private Seguradora seguradora;
	
	@Column(name = "apolice")
	private Apolice apolice;
	
	@Column(name = "agenteIntegrador")
	private AgenteIntegrador agenteIntegrador;
	
	@Column(name = "orientador")
	private Orientador orientador;
	
	@Column(name = "supervisor")
	private Supervisor supervisor;
	
	@Column(name = "coordenadorCurso")
	private Coordenador coordenadorCurso;
	
	@Column(name = "planoAtividades")
	private PlanoDeAtividades planoAtividades;
	
	@Column(name = "dataInicio")
	private Date dataInicio;
	
	@Column(name = "dataTermino")
	private Date dataTermino;
	
	@Column(name = "jornadaDiaria")
	private int jornadaDiaria;
	
	@Column(name = "jornadaSemanal")
	private int jornadaSemanal;
	
	@Column(name = "valorBolsa")
	private float valorBolsa;
	
	@Column(name = "valorTransporte")
	private float valorTransporte;
	
	@Column(name = "dataFimSuspensao")
	private Date dataFimSuspensao;
	
	@Column(name = "dataInicioRetomada")
	private Date dataInicioRetomada;

	@Column(name = "dataCriacao")
	private Date dataCriacao;
	
	@Column(name = "statusTermo")
	private EnumStatusTermo statusTermo;
	
	@Column(name = "etapaFluxo")
	private EnumEtapaFluxo etapaFluxo;
	
	@Column(name = "cienciaCoordenacao")
	private CienciaCoordenacao cienciaCoordenacao;
	
	@Column(name = "parecerCOE")
	private EnumParecerAprovadores parecerCOE;
	
	@Column(name = "parecerCOAFE")
	private EnumParecerAprovadores parecerCOAFE;
	
	@Column(name = "parecerCoordenacao")
	private EnumParecerAprovadores parecerCoordenacao;

	@Column(name = "motivoIndeferimento")
	private String motivoIndeferimento;
	
	@Column(name = "descricaoAjustes")
	private String descricaoAjustes;
	
	public TermoDeEstagio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoDeEstagio(int id, EnumTipoTermoDeEstagio tipoTermoDeEstagio, Estagio estagio, Seguradora seguradora,
			Apolice apolice, AgenteIntegrador agenteIntegrador, Orientador orientador, Supervisor supervisor,
			Coordenador coordenadorCurso, PlanoDeAtividades planoAtividades, Date dataInicio, Date dataTermino,
			int jornadaDiaria, int jornadaSemanal, float valorBolsa, float valorTransporte, Date dataFimSuspensao,
			Date dataInicioRetomada, Date dataCriacao, EnumStatusTermo statusTermo, EnumEtapaFluxo etapaFluxo,
			CienciaCoordenacao cienciaCoordenacao, EnumParecerAprovadores parecerCOE,
			EnumParecerAprovadores parecerCOAFE, EnumParecerAprovadores parecerCoordenacao, String motivoIndeferimento,
			String descricaoAjustes) {
		super();
		this.id = id;
		this.tipoTermoDeEstagio = tipoTermoDeEstagio;
		this.estagio = estagio;
		this.seguradora = seguradora;
		this.apolice = apolice;
		this.agenteIntegrador = agenteIntegrador;
		this.orientador = orientador;
		this.supervisor = supervisor;
		this.coordenadorCurso = coordenadorCurso;
		this.planoAtividades = planoAtividades;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.jornadaDiaria = jornadaDiaria;
		this.jornadaSemanal = jornadaSemanal;
		this.valorBolsa = valorBolsa;
		this.valorTransporte = valorTransporte;
		this.dataFimSuspensao = dataFimSuspensao;
		this.dataInicioRetomada = dataInicioRetomada;
		this.dataCriacao = dataCriacao;
		this.statusTermo = statusTermo;
		this.etapaFluxo = etapaFluxo;
		this.cienciaCoordenacao = cienciaCoordenacao;
		this.parecerCOE = parecerCOE;
		this.parecerCOAFE = parecerCOAFE;
		this.parecerCoordenacao = parecerCoordenacao;
		this.motivoIndeferimento = motivoIndeferimento;
		this.descricaoAjustes = descricaoAjustes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EnumTipoTermoDeEstagio getTipoTermoDeEstagio() {
		return tipoTermoDeEstagio;
	}

	public void setTipoTermoDeEstagio(EnumTipoTermoDeEstagio tipoTermoDeEstagio) {
		this.tipoTermoDeEstagio = tipoTermoDeEstagio;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public Seguradora getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(Seguradora seguradora) {
		this.seguradora = seguradora;
	}

	public Apolice getApolice() {
		return apolice;
	}

	public void setApolice(Apolice apolice) {
		this.apolice = apolice;
	}

	public AgenteIntegrador getAgenteIntegrador() {
		return agenteIntegrador;
	}

	public void setAgenteIntegrador(AgenteIntegrador agenteIntegrador) {
		this.agenteIntegrador = agenteIntegrador;
	}

	public Orientador getOrientador() {
		return orientador;
	}

	public void setOrientador(Orientador orientador) {
		this.orientador = orientador;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public Coordenador getCoordenadorCurso() {
		return coordenadorCurso;
	}

	public void setCoordenadorCurso(Coordenador coordenadorCurso) {
		this.coordenadorCurso = coordenadorCurso;
	}

	public PlanoDeAtividades getPlanoAtividades() {
		return planoAtividades;
	}

	public void setPlanoAtividades(PlanoDeAtividades planoAtividades) {
		this.planoAtividades = planoAtividades;
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

	public EnumStatusTermo getStatusTermo() {
		return statusTermo;
	}

	public void setStatusTermo(EnumStatusTermo statusTermo) {
		this.statusTermo = statusTermo;
	}

	public EnumEtapaFluxo getEtapaFluxo() {
		return etapaFluxo;
	}

	public void setEtapaFluxo(EnumEtapaFluxo etapaFluxo) {
		this.etapaFluxo = etapaFluxo;
	}

	public CienciaCoordenacao getCienciaCoordenacao() {
		return cienciaCoordenacao;
	}

	public void setCienciaCoordenacao(CienciaCoordenacao cienciaCoordenacao) {
		this.cienciaCoordenacao = cienciaCoordenacao;
	}

	public EnumParecerAprovadores getParecerCOE() {
		return parecerCOE;
	}

	public void setParecerCOE(EnumParecerAprovadores parecerCOE) {
		this.parecerCOE = parecerCOE;
	}

	public EnumParecerAprovadores getParecerCOAFE() {
		return parecerCOAFE;
	}

	public void setParecerCOAFE(EnumParecerAprovadores parecerCOAFE) {
		this.parecerCOAFE = parecerCOAFE;
	}

	public EnumParecerAprovadores getParecerCoordenacao() {
		return parecerCoordenacao;
	}

	public void setParecerCoordenacao(EnumParecerAprovadores parecerCoordenacao) {
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

}