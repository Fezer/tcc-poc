package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumParecerAprovadores;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.service.CurrentTimestampService;

@Entity
@Table(name = "termo_de_estagio", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class TermoDeEstagio extends RepresentationModel<TermoDeEstagio> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "tipo_termo_de_estagio")
	private EnumTipoTermoDeEstagio tipoTermoDeEstagio;
	
	@JsonIgnore
	@ManyToOne(cascade= {CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinColumn(name="estagio_id", referencedColumnName="id",nullable=true)
	private Estagio estagio;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="seguradora_id", referencedColumnName="id", nullable=true)
	private Seguradora seguradora;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="apolice_id", referencedColumnName="id",nullable=true)
	private Apolice apolice;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="agente_integrador_id", referencedColumnName="id", nullable=true)
	private AgenteIntegrador agenteIntegrador;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="orientador_id", referencedColumnName="id", nullable=true)
	private Orientador orientador;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="supervisor_id", referencedColumnName="id", nullable=true)
	private Supervisor supervisor;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="coordenador_id", referencedColumnName="id", nullable=true)
	private Coordenador coordenador;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="plano_atividades_id", referencedColumnName="id",nullable=true)
	private PlanoDeAtividades planoAtividades;
	
	@Column(name = "data_inicio")
	private Date dataInicio;
	
	@Column(name = "data_termino")
	private Date dataTermino;
	
	@Column(name = "jornada_diaria")
	private int jornadaDiaria;
	
	@Column(name = "jornada_semanal")
	private int jornadaSemanal;
	
	@Column(name = "valor_bolsa")
	private float valorBolsa;
	
	@Column(name = "valor_transporte")
	private float valorTransporte;
	
	@Column(name = "data_fim_suspensao")
	private Date dataFimSuspensao;
	
	@Column(name = "data_inicio_retomada")
	private Date dataInicioRetomada;

	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	@Column(name = "status_termo")
	private EnumStatusTermo statusTermo;
	
	@Column(name = "etapa_fluxo")
	private EnumEtapaFluxo etapaFluxo;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="ciencia_coordenacao_id", referencedColumnName="id",nullable=true)
	private CienciaCoordenacao cienciaCoordenacao;
	
	@Column(name = "parecer_coe")
	private EnumParecerAprovadores parecerCOE;
	
	@Column(name = "parecer_coafe")
	private EnumParecerAprovadores parecerCOAFE;
	
	@Column(name = "parecer_coordenacao")
	private EnumParecerAprovadores parecerCoordenacao;

	@Column(name = "motivo_indeferimento")
	private String motivoIndeferimento;
	
	@Column(name = "descricao_ajustes")
	private String descricaoAjustes;
	
	public TermoDeEstagio() {
		super();
		this.dataCriacao = this.gerarDataCriacao();
	}
	
	private Date gerarDataCriacao() {
		CurrentTimestampService time = new CurrentTimestampService();
		return time.getTimestamp();
	}
	

	public TermoDeEstagio(long id, EnumTipoTermoDeEstagio tipoTermoDeEstagio, Estagio estagio, Seguradora seguradora,
			Apolice apolice, AgenteIntegrador agenteIntegrador, Orientador orientador, Supervisor supervisor,
			Coordenador coordenador, PlanoDeAtividades planoAtividades, Date dataInicio, Date dataTermino,
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
		this.coordenador = coordenador;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
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