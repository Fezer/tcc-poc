package br.ufpr.estagio.modulo.dto;

import java.sql.Date;

import br.ufpr.estagio.modulo.enums.EnumEtapaFluxo;
import br.ufpr.estagio.modulo.enums.EnumParecerAprovadores;
import br.ufpr.estagio.modulo.enums.EnumStatusTermo;
import br.ufpr.estagio.modulo.enums.EnumTipoTermoDeEstagio;
import br.ufpr.estagio.modulo.model.AgenteIntegrador;
import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.CienciaCoordenacao;
import br.ufpr.estagio.modulo.model.Contratante;
import br.ufpr.estagio.modulo.model.Coordenador;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.Orientador;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.Seguradora;

public class TermoDeEstagioDTO {
	private long id;
	private EnumTipoTermoDeEstagio tipoTermoDeEstagio;
	private Estagio estagio;
	private Seguradora seguradora;
	private Apolice apolice;
	
	// adicionado para concluir a task de associar contratante ao termo
	private Contratante contratante;
	
	private AgenteIntegrador agenteIntegrador;
	private Orientador orientador;
	private Coordenador coordenador;
	private PlanoDeAtividades planoAtividades;
	private Date dataInicio;
	private Date dataTermino;
	private int jornadaDiaria;
	private int jornadaSemanal;
	private float valorBolsa;
	private float valorTransporte;
	private Date dataFimSuspensao;
	private Date dataInicioRetomada;
	private Date dataCriacao;
	private EnumStatusTermo statusTermo;
	private EnumEtapaFluxo etapaFluxo;
	private CienciaCoordenacao cienciaCoordenacao;
	private EnumParecerAprovadores parecerCOE;
	private EnumParecerAprovadores parecerCOAFE;
	private EnumParecerAprovadores parecerCoordenacao;
	private String motivoIndeferimento;
	private String descricaoAjustes;
	
	public TermoDeEstagioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermoDeEstagioDTO(long id, EnumTipoTermoDeEstagio tipoTermoDeEstagio, Estagio estagio, Seguradora seguradora,
			Apolice apolice, AgenteIntegrador agenteIntegrador, Orientador orientador,
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

	// adicionado para concluir a task de associar contratante ao termo
	public TermoDeEstagioDTO(long id, EnumTipoTermoDeEstagio tipoTermoDeEstagio, Estagio estagio, Seguradora seguradora,
			Apolice apolice, Contratante contratante, AgenteIntegrador agenteIntegrador, Orientador orientador,
			Coordenador coordenador, PlanoDeAtividades planoAtividades, Date dataInicio,
			Date dataTermino, int jornadaDiaria, int jornadaSemanal, float valorBolsa, float valorTransporte,
			Date dataFimSuspensao, Date dataInicioRetomada, Date dataCriacao, EnumStatusTermo statusTermo,
			EnumEtapaFluxo etapaFluxo, CienciaCoordenacao cienciaCoordenacao, EnumParecerAprovadores parecerCOE,
			EnumParecerAprovadores parecerCOAFE, EnumParecerAprovadores parecerCoordenacao, String motivoIndeferimento,
			String descricaoAjustes) {
		super();
		this.id = id;
		this.tipoTermoDeEstagio = tipoTermoDeEstagio;
		this.estagio = estagio;
		this.seguradora = seguradora;
		this.apolice = apolice;
		this.contratante = contratante;
		this.agenteIntegrador = agenteIntegrador;
		this.orientador = orientador;
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

	// adicionado para concluir a task de associar contratante ao termo
	public Contratante getContratante() {
		return contratante;
	}

	// adicionado para concluir a task de associar contratante ao termo
	public void setContratante(Contratante contratante) {
		this.contratante = contratante;
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