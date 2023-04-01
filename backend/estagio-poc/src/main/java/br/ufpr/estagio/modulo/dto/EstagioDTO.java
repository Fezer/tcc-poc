package br.ufpr.estagio.modulo.dto;

import java.util.ArrayList;
import java.util.Date;

import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;

public class EstagioDTO {
	
	private long id;
	private EnumTipoEstagio tipoEstagio;
	private EnumStatusEstagio statusEstagio;
	private boolean estagioUfpr;
	private AlunoDTO aluno;
	private ContratanteDTO contratante;
	private SeguradoraDTO seguradora;
	private AgenteIntegradorDTO agenteIntegrador;
	private OrientadorDTO orientador;
	private SupervisorDTO supervisor;
	private PlanoDeAtividadesDTO planoDeAtividades;
	private Date dataInicio;
	private Date dataTermino;
	private int jornadaDiaria;
	private int jornadaSemanal;
	private float valorBolsa;
	private float valorTransporte;
	private TermoDeEstagioDTO termoDeCompromisso;
	private ArrayList<TermoDeEstagioDTO> termoAdivito;
	private TermoDeRescisaoDTO termoDeRescisao;
	private ArrayList<RelatorioDeEstagioDTO> relatorioDeEstagio;
	private FichaDeAvaliacaoDTO fichaDeAvaliacao;
	private CertificadoDeEstagioDTO certificadoDeEstagio;
	
	public EstagioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EstagioDTO(long id, EnumTipoEstagio tipoEstagio, EnumStatusEstagio statusEstagio, boolean estagioUfpr,
			AlunoDTO aluno, ContratanteDTO contratante, SeguradoraDTO seguradora, AgenteIntegradorDTO agenteIntegrador,
			OrientadorDTO orientador, SupervisorDTO supervisor, PlanoDeAtividadesDTO planoDeAtividades, Date dataInicio,
			Date dataTermino, int jornadaDiaria, int jornadaSemanal, float valorBolsa, float valorTransporte,
			TermoDeEstagioDTO termoDeCompromisso, ArrayList<TermoDeEstagioDTO> termoAdivito,
			TermoDeRescisaoDTO termoDeRescisao, ArrayList<RelatorioDeEstagioDTO> relatorioDeEstagio,
			FichaDeAvaliacaoDTO fichaDeAvaliacao, CertificadoDeEstagioDTO certificadoDeEstagio) {
		super();
		this.id = id;
		this.tipoEstagio = tipoEstagio;
		this.statusEstagio = statusEstagio;
		this.estagioUfpr = estagioUfpr;
		this.aluno = aluno;
		this.contratante = contratante;
		this.seguradora = seguradora;
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

	public AlunoDTO getAluno() {
		return aluno;
	}

	public void setAluno(AlunoDTO aluno) {
		this.aluno = aluno;
	}

	public ContratanteDTO getContratante() {
		return contratante;
	}

	public void setContratante(ContratanteDTO contratante) {
		this.contratante = contratante;
	}

	public SeguradoraDTO getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(SeguradoraDTO seguradora) {
		this.seguradora = seguradora;
	}

	public AgenteIntegradorDTO getAgenteIntegrador() {
		return agenteIntegrador;
	}

	public void setAgenteIntegrador(AgenteIntegradorDTO agenteIntegrador) {
		this.agenteIntegrador = agenteIntegrador;
	}

	public OrientadorDTO getOrientador() {
		return orientador;
	}

	public void setOrientador(OrientadorDTO orientador) {
		this.orientador = orientador;
	}

	public SupervisorDTO getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(SupervisorDTO supervisor) {
		this.supervisor = supervisor;
	}

	public PlanoDeAtividadesDTO getPlanoDeAtividades() {
		return planoDeAtividades;
	}

	public void setPlanoDeAtividades(PlanoDeAtividadesDTO planoDeAtividades) {
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

	public TermoDeEstagioDTO getTermoDeCompromisso() {
		return termoDeCompromisso;
	}

	public void setTermoDeCompromisso(TermoDeEstagioDTO termoDeCompromisso) {
		this.termoDeCompromisso = termoDeCompromisso;
	}

	public ArrayList<TermoDeEstagioDTO> getTermoAdivito() {
		return termoAdivito;
	}

	public void setTermoAdivito(ArrayList<TermoDeEstagioDTO> termoAdivito) {
		this.termoAdivito = termoAdivito;
	}

	public TermoDeRescisaoDTO getTermoDeRescisao() {
		return termoDeRescisao;
	}

	public void setTermoDeRescisao(TermoDeRescisaoDTO termoDeRescisao) {
		this.termoDeRescisao = termoDeRescisao;
	}

	public ArrayList<RelatorioDeEstagioDTO> getRelatorioDeEstagio() {
		return relatorioDeEstagio;
	}

	public void setRelatorioDeEstagio(ArrayList<RelatorioDeEstagioDTO> relatorioDeEstagio) {
		this.relatorioDeEstagio = relatorioDeEstagio;
	}

	public FichaDeAvaliacaoDTO getFichaDeAvaliacao() {
		return fichaDeAvaliacao;
	}

	public void setFichaDeAvaliacao(FichaDeAvaliacaoDTO fichaDeAvaliacao) {
		this.fichaDeAvaliacao = fichaDeAvaliacao;
	}

	public CertificadoDeEstagioDTO getCertificadoDeEstagio() {
		return certificadoDeEstagio;
	}

	public void setCertificadoDeEstagio(CertificadoDeEstagioDTO certificadoDeEstagio) {
		this.certificadoDeEstagio = certificadoDeEstagio;
	}
	
}
