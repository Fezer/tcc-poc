package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;

@Entity
@Table(name = "estagio", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Estagio extends RepresentationModel<Estagio> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "tipo_estagio")
	private EnumTipoEstagio tipoEstagio;
	
	@Column(name = "status_estagio")
	private EnumStatusEstagio statusEstagio;
	
	@Column(name = "estagio_ufpr")
	private boolean estagioUfpr;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="aluno_id", referencedColumnName="id", nullable=true)
	private Aluno aluno;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="contratante_id", referencedColumnName="id", nullable=true)
	private Contratante contratante;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="seguradora_id", referencedColumnName="id", nullable=true)
	private Seguradora seguradora;
	
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

	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="plano_de_atividades_id", referencedColumnName="id",nullable=true)
	private PlanoDeAtividades planoDeAtividades;
	
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
	
	@OneToOne(cascade= {CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinColumn(name="termo_de_compromisso_id", referencedColumnName="id",nullable=true)
	private TermoDeEstagio termoDeCompromisso;
	
	@OneToMany(mappedBy="estagio", cascade= {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<TermoDeEstagio> termoAdivito;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="termo_de_rescisao_id", referencedColumnName="id",nullable=true)
	private TermoDeRescisao termoDeRescisao;

	@OneToMany(mappedBy="estagio", cascade= CascadeType.REMOVE)
	private List<RelatorioDeEstagio> relatorioDeEstagio;

	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="ficha_de_avaliacao_id", referencedColumnName="id",nullable=true)
	private FichaDeAvaliacao fichaDeAvaliacao;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="certificado_de_estagi_id", referencedColumnName="id",nullable=true)
	private CertificadoDeEstagio certificadoDeEstagio;
	
	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	public Estagio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Estagio(long id, EnumTipoEstagio tipoEstagio, EnumStatusEstagio statusEstagio, boolean estagioUfpr,
			Aluno aluno, Contratante contratante, Seguradora seguradora, Apolice apolice,
			AgenteIntegrador agenteIntegrador, Orientador orientador, Supervisor supervisor,
			PlanoDeAtividades planoDeAtividades, Date dataInicio, Date dataTermino, int jornadaDiaria,
			int jornadaSemanal, float valorBolsa, float valorTransporte, TermoDeEstagio termoDeCompromisso,
			List<TermoDeEstagio> termoAdivito, TermoDeRescisao termoDeRescisao,
			List<RelatorioDeEstagio> relatorioDeEstagio, FichaDeAvaliacao fichaDeAvaliacao,
			CertificadoDeEstagio certificadoDeEstagio, Date dataCriacao) {
		super();
		this.id = id;
		this.tipoEstagio = tipoEstagio;
		this.statusEstagio = statusEstagio;
		this.estagioUfpr = estagioUfpr;
		this.aluno = aluno;
		this.contratante = contratante;
		this.seguradora = seguradora;
		this.apolice = apolice;
		this.agenteIntegrador = agenteIntegrador;
		this.orientador = orientador;
		this.supervisor = supervisor;
		this.planoDeAtividades = planoDeAtividades;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.jornadaDiaria = jornadaDiaria;
		this.jornadaSemanal = jornadaSemanal;
		this.valorBolsa = valorBolsa;
		this.valorTransporte = valorTransporte;
		this.termoDeCompromisso = termoDeCompromisso;
		this.termoAdivito = termoAdivito;
		this.termoDeRescisao = termoDeRescisao;
		this.relatorioDeEstagio = relatorioDeEstagio;
		this.fichaDeAvaliacao = fichaDeAvaliacao;
		this.certificadoDeEstagio = certificadoDeEstagio;
		this.dataCriacao = dataCriacao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EnumTipoEstagio getTipoEstagio() {
		return tipoEstagio;
	}

	public void setTipoEstagio(EnumTipoEstagio tipoEstagio) {
		this.tipoEstagio = tipoEstagio;
	}

	public EnumStatusEstagio getStatusEstagio() {
		return statusEstagio;
	}

	public void setStatusEstagio(EnumStatusEstagio statusEstagio) {
		this.statusEstagio = statusEstagio;
	}

	public boolean isEstagioUfpr() {
		return estagioUfpr;
	}

	public void setEstagioUfpr(boolean estagioUfpr) {
		this.estagioUfpr = estagioUfpr;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Contratante getContratante() {
		return contratante;
	}

	public void setContratante(Contratante contratante) {
		this.contratante = contratante;
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

	public PlanoDeAtividades getPlanoDeAtividades() {
		return planoDeAtividades;
	}

	public void setPlanoDeAtividades(PlanoDeAtividades planoDeAtividades) {
		this.planoDeAtividades = planoDeAtividades;
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

	public TermoDeEstagio getTermoDeCompromisso() {
		return termoDeCompromisso;
	}

	public void setTermoDeCompromisso(TermoDeEstagio termoDeCompromisso) {
		this.termoDeCompromisso = termoDeCompromisso;
	}

	public List<TermoDeEstagio> getTermoAdivito() {
		return termoAdivito;
	}

	public void setTermoAdivito(List<TermoDeEstagio> termoAdivito) {
		this.termoAdivito = termoAdivito;
	}

	public TermoDeRescisao getTermoDeRescisao() {
		return termoDeRescisao;
	}

	public void setTermoDeRescisao(TermoDeRescisao termoDeRescisao) {
		this.termoDeRescisao = termoDeRescisao;
	}

	public List<RelatorioDeEstagio> getRelatorioDeEstagio() {
		return relatorioDeEstagio;
	}

	public void setRelatorioDeEstagio(List<RelatorioDeEstagio> relatorioDeEstagio) {
		this.relatorioDeEstagio = relatorioDeEstagio;
	}

	public FichaDeAvaliacao getFichaDeAvaliacao() {
		return fichaDeAvaliacao;
	}

	public void setFichaDeAvaliacao(FichaDeAvaliacao fichaDeAvaliacao) {
		this.fichaDeAvaliacao = fichaDeAvaliacao;
	}

	public CertificadoDeEstagio getCertificadoDeEstagio() {
		return certificadoDeEstagio;
	}

	public void setCertificadoDeEstagio(CertificadoDeEstagio certificadoDeEstagio) {
		this.certificadoDeEstagio = certificadoDeEstagio;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}