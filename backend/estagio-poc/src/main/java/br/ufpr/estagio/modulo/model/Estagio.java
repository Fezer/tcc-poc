package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import jakarta.persistence.*;

import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;

@Entity
@Table(name = "estagio", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Estagio implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "tipoEstagio")
	private EnumTipoEstagio tipoEstagio;
	
	@Column(name = "statusEstagio")
	private EnumStatusEstagio statusEstagio;
	
	@Column(name = "estagioUfpr")
	private boolean estagioUfpr;
	
	@Column(name = "aluno")
	private Aluno aluno;
	
	@Column(name = "contratante")
	private Contratante contratante;
	
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
	
	@Column(name = "planoDeAtividades")
	private PlanoDeAtividades planoDeAtividades;
	
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
	
	@Column(name = "termoDeCompromisso")
	private TermoDeEstagio termoDeCompromisso;
	
	@Column(name = "termoAdivito")
	@ElementCollection
	private ArrayList<TermoDeEstagio> termoAdivito;
	
	@Column(name = "termoDeRescisao")
	private TermoDeRescisao termoDeRescisao;
	
	@Column(name = "relatorioDeEstagio")
	@ElementCollection
	private ArrayList<RelatorioDeEstagio> relatorioDeEstagio;
	
	@Column(name = "fichaDeAvaliacao")
	private FichaDeAvaliacao fichaDeAvaliacao;
	
	@Column(name = "certificadoDeEstagio")
	private CertificadoDeEstagio certificadoDeEstagio;
	
	@Column(name = "dataCriacao")
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
			ArrayList<TermoDeEstagio> termoAdivito, TermoDeRescisao termoDeRescisao,
			ArrayList<RelatorioDeEstagio> relatorioDeEstagio, FichaDeAvaliacao fichaDeAvaliacao,
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

	public ArrayList<TermoDeEstagio> getTermoAdivito() {
		return termoAdivito;
	}

	public void setTermoAdivito(ArrayList<TermoDeEstagio> termoAdivito) {
		this.termoAdivito = termoAdivito;
	}

	public TermoDeRescisao getTermoDeRescisao() {
		return termoDeRescisao;
	}

	public void setTermoDeRescisao(TermoDeRescisao termoDeRescisao) {
		this.termoDeRescisao = termoDeRescisao;
	}

	public ArrayList<RelatorioDeEstagio> getRelatorioDeEstagio() {
		return relatorioDeEstagio;
	}

	public void setRelatorioDeEstagio(ArrayList<RelatorioDeEstagio> relatorioDeEstagio) {
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