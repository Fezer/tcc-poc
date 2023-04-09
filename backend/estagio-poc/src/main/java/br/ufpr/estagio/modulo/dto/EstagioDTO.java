package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;

public class EstagioDTO extends RepresentationModel<EstagioDTO> implements Serializable{
	
	private long id;
	private EnumTipoEstagio tipoEstagio;
	private EnumStatusEstagio statusEstagio;
	private boolean estagioUfpr;
	private long aluno;
	private long contratante;
	private long seguradora;
	private long apolice;
	private long agenteIntegrador;
	private long orientador;
	private long supervisor;
	private long planoDeAtividades;
	private Date dataInicio;
	private Date dataTermino;
	private int jornadaDiaria;
	private int jornadaSemanal;
	private float valorBolsa;
	private float valorTransporte;
	private long termoDeCompromisso;
	private List<Long> termoAdivito;
	private long termoDeRescisao;
	private List<Long> relatorioDeEstagio;
	private long fichaDeAvaliacao;
	private long certificadoDeEstagio;
	private Date dataCriacao;
	
	public EstagioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EstagioDTO(long id, EnumTipoEstagio tipoEstagio, EnumStatusEstagio statusEstagio, boolean estagioUfpr,
			long aluno, long contratante, long seguradora, long apolice, long agenteIntegrador, long orientador,
			long supervisor, long planoDeAtividades, Date dataInicio, Date dataTermino, int jornadaDiaria,
			int jornadaSemanal, float valorBolsa, float valorTransporte, long termoDeCompromisso,
			List<Long> termoAdivito, long termoDeRescisao, List<Long> relatorioDeEstagio,
			long fichaDeAvaliacao, long certificadoDeEstagio, Date dataCriacao) {
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

	public long getAluno() {
		return aluno;
	}

	public void setAluno(long aluno) {
		this.aluno = aluno;
	}

	public long getContratante() {
		return contratante;
	}

	public void setContratante(long contratante) {
		this.contratante = contratante;
	}

	public long getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(long seguradora) {
		this.seguradora = seguradora;
	}

	public long getApolice() {
		return apolice;
	}

	public void setApolice(long apolice) {
		this.apolice = apolice;
	}

	public long getAgenteIntegrador() {
		return agenteIntegrador;
	}

	public void setAgenteIntegrador(long agenteIntegrador) {
		this.agenteIntegrador = agenteIntegrador;
	}

	public long getOrientador() {
		return orientador;
	}

	public void setOrientador(long orientador) {
		this.orientador = orientador;
	}

	public long getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(long supervisor) {
		this.supervisor = supervisor;
	}

	public long getPlanoDeAtividades() {
		return planoDeAtividades;
	}

	public void setPlanoDeAtividades(long planoDeAtividades) {
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

	public long getTermoDeCompromisso() {
		return termoDeCompromisso;
	}

	public void setTermoDeCompromisso(long termoDeCompromisso) {
		this.termoDeCompromisso = termoDeCompromisso;
	}

	public List<Long> getTermoAdivito() {
		return termoAdivito;
	}

	public void setTermoAdivito(List<Long> termoAdivito) {
		this.termoAdivito = termoAdivito;
	}

	public long getTermoDeRescisao() {
		return termoDeRescisao;
	}

	public void setTermoDeRescisao(long termoDeRescisao) {
		this.termoDeRescisao = termoDeRescisao;
	}

	public List<Long> getRelatorioDeEstagio() {
		return relatorioDeEstagio;
	}

	public void setRelatorioDeEstagio(List<Long> relatorioDeEstagio) {
		this.relatorioDeEstagio = relatorioDeEstagio;
	}

	public long getFichaDeAvaliacao() {
		return fichaDeAvaliacao;
	}

	public void setFichaDeAvaliacao(long fichaDeAvaliacao) {
		this.fichaDeAvaliacao = fichaDeAvaliacao;
	}

	public long getCertificadoDeEstagio() {
		return certificadoDeEstagio;
	}

	public void setCertificadoDeEstagio(long certificadoDeEstagio) {
		this.certificadoDeEstagio = certificadoDeEstagio;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
}