package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import br.ufpr.estagio.modulo.enums.EnumStatusEstagio;
import br.ufpr.estagio.modulo.enums.EnumTipoEstagio;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Aluno;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.Seguradora;

public class EstagioDTO extends RepresentationModel<EstagioDTO> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private EnumTipoEstagio tipoEstagio;
	private EnumStatusEstagio statusEstagio;
	private boolean estagioUfpr;
	private boolean estagioSeed;
	private Aluno aluno;
	private Contratante contratante;
	private Seguradora seguradora;
	private Apolice apolice;
	private AgenteIntegrador agenteIntegrador;
	private Orientador orientador;
	private PlanoDeAtividades planoDeAtividades;
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
			boolean estagioSeed, Aluno aluno, Contratante contratante, Seguradora seguradora, Apolice apolice,
			AgenteIntegrador agenteIntegrador, Orientador orientador, PlanoDeAtividades planoDeAtividades,
			Date dataInicio, Date dataTermino, int jornadaDiaria, int jornadaSemanal, float valorBolsa,
			float valorTransporte, long termoDeCompromisso, List<Long> termoAdivito, long termoDeRescisao,
			List<Long> relatorioDeEstagio, long fichaDeAvaliacao, long certificadoDeEstagio, Date dataCriacao) {
		super();
		this.id = id;
		this.tipoEstagio = tipoEstagio;
		this.statusEstagio = statusEstagio;
		this.estagioUfpr = estagioUfpr;
		this.estagioSeed = estagioSeed;
		this.aluno = aluno;
		this.contratante = contratante;
		this.seguradora = seguradora;
		this.apolice = apolice;
		this.agenteIntegrador = agenteIntegrador;
		this.orientador = orientador;
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
	
	public boolean isEstagioSeed() {
		return estagioSeed;
	}

	public void setEstagioSeed(boolean estagioSeed) {
		this.estagioSeed = estagioSeed;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}