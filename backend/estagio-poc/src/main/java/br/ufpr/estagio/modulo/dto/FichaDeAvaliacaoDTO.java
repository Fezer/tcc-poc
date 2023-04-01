package br.ufpr.estagio.modulo.dto;

import br.ufpr.estagio.modulo.enums.EnumAvaliacaoAcomp;
import br.ufpr.estagio.modulo.model.Estagio;

public class FichaDeAvaliacaoDTO {
	
	private long id;
	private Estagio estagio;
	private int totalHorasEstagioEfetivamenteRealizadas;
	private boolean atividadesForamRealizadas;
	private String atividadesRealizadasConsideracoes;
	private EnumAvaliacaoAcomp acompanhamentoOrientador;
	private String acompanhamentoOrientadorComentario;
	private EnumAvaliacaoAcomp acompanhamentoCoordenador;
	private String acompanhamentoCoordenadorComentario;
	private String contribuicaoEstagio;
	private EnumAvaliacaoAcomp avalPontualidade;
	private EnumAvaliacaoAcomp avalCriatividade;
	private EnumAvaliacaoAcomp avalProtagonismo;
	private EnumAvaliacaoAcomp avalResponsabilidade;
	private EnumAvaliacaoAcomp avalConduta;
	private EnumAvaliacaoAcomp avalDominioTecnico;
	private EnumAvaliacaoAcomp avalHabilidades;
	private EnumAvaliacaoAcomp avalEfetivacao;
	
	public FichaDeAvaliacaoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FichaDeAvaliacaoDTO(long id, Estagio estagio, int totalHorasEstagioEfetivamenteRealizadas,
			boolean atividadesForamRealizadas, String atividadesRealizadasConsideracoes,
			EnumAvaliacaoAcomp acompanhamentoOrientador, String acompanhamentoOrientadorComentario,
			EnumAvaliacaoAcomp acompanhamentoCoordenador, String acompanhamentoCoordenadorComentario,
			String contribuicaoEstagio, EnumAvaliacaoAcomp avalPontualidade, EnumAvaliacaoAcomp avalCriatividade,
			EnumAvaliacaoAcomp avalProtagonismo, EnumAvaliacaoAcomp avalResponsabilidade,
			EnumAvaliacaoAcomp avalConduta, EnumAvaliacaoAcomp avalDominioTecnico, EnumAvaliacaoAcomp avalHabilidades,
			EnumAvaliacaoAcomp avalEfetivacao) {
		super();
		this.id = id;
		this.estagio = estagio;
		this.totalHorasEstagioEfetivamenteRealizadas = totalHorasEstagioEfetivamenteRealizadas;
		this.atividadesForamRealizadas = atividadesForamRealizadas;
		this.atividadesRealizadasConsideracoes = atividadesRealizadasConsideracoes;
		this.acompanhamentoOrientador = acompanhamentoOrientador;
		this.acompanhamentoOrientadorComentario = acompanhamentoOrientadorComentario;
		this.acompanhamentoCoordenador = acompanhamentoCoordenador;
		this.acompanhamentoCoordenadorComentario = acompanhamentoCoordenadorComentario;
		this.contribuicaoEstagio = contribuicaoEstagio;
		this.avalPontualidade = avalPontualidade;
		this.avalCriatividade = avalCriatividade;
		this.avalProtagonismo = avalProtagonismo;
		this.avalResponsabilidade = avalResponsabilidade;
		this.avalConduta = avalConduta;
		this.avalDominioTecnico = avalDominioTecnico;
		this.avalHabilidades = avalHabilidades;
		this.avalEfetivacao = avalEfetivacao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public int getTotalHorasEstagioEfetivamenteRealizadas() {
		return totalHorasEstagioEfetivamenteRealizadas;
	}

	public void setTotalHorasEstagioEfetivamenteRealizadas(int totalHorasEstagioEfetivamenteRealizadas) {
		this.totalHorasEstagioEfetivamenteRealizadas = totalHorasEstagioEfetivamenteRealizadas;
	}

	public boolean isAtividadesForamRealizadas() {
		return atividadesForamRealizadas;
	}

	public void setAtividadesForamRealizadas(boolean atividadesForamRealizadas) {
		this.atividadesForamRealizadas = atividadesForamRealizadas;
	}

	public String getAtividadesRealizadasConsideracoes() {
		return atividadesRealizadasConsideracoes;
	}

	public void setAtividadesRealizadasConsideracoes(String atividadesRealizadasConsideracoes) {
		this.atividadesRealizadasConsideracoes = atividadesRealizadasConsideracoes;
	}

	public EnumAvaliacaoAcomp getAcompanhamentoOrientador() {
		return acompanhamentoOrientador;
	}

	public void setAcompanhamentoOrientador(EnumAvaliacaoAcomp acompanhamentoOrientador) {
		this.acompanhamentoOrientador = acompanhamentoOrientador;
	}

	public String getAcompanhamentoOrientadorComentario() {
		return acompanhamentoOrientadorComentario;
	}

	public void setAcompanhamentoOrientadorComentario(String acompanhamentoOrientadorComentario) {
		this.acompanhamentoOrientadorComentario = acompanhamentoOrientadorComentario;
	}

	public EnumAvaliacaoAcomp getAcompanhamentoCoordenador() {
		return acompanhamentoCoordenador;
	}

	public void setAcompanhamentoCoordenador(EnumAvaliacaoAcomp acompanhamentoCoordenador) {
		this.acompanhamentoCoordenador = acompanhamentoCoordenador;
	}

	public String getAcompanhamentoCoordenadorComentario() {
		return acompanhamentoCoordenadorComentario;
	}

	public void setAcompanhamentoCoordenadorComentario(String acompanhamentoCoordenadorComentario) {
		this.acompanhamentoCoordenadorComentario = acompanhamentoCoordenadorComentario;
	}

	public String getContribuicaoEstagio() {
		return contribuicaoEstagio;
	}

	public void setContribuicaoEstagio(String contribuicaoEstagio) {
		this.contribuicaoEstagio = contribuicaoEstagio;
	}

	public EnumAvaliacaoAcomp getAvalPontualidade() {
		return avalPontualidade;
	}

	public void setAvalPontualidade(EnumAvaliacaoAcomp avalPontualidade) {
		this.avalPontualidade = avalPontualidade;
	}

	public EnumAvaliacaoAcomp getAvalCriatividade() {
		return avalCriatividade;
	}

	public void setAvalCriatividade(EnumAvaliacaoAcomp avalCriatividade) {
		this.avalCriatividade = avalCriatividade;
	}

	public EnumAvaliacaoAcomp getAvalProtagonismo() {
		return avalProtagonismo;
	}

	public void setAvalProtagonismo(EnumAvaliacaoAcomp avalProtagonismo) {
		this.avalProtagonismo = avalProtagonismo;
	}

	public EnumAvaliacaoAcomp getAvalResponsabilidade() {
		return avalResponsabilidade;
	}

	public void setAvalResponsabilidade(EnumAvaliacaoAcomp avalResponsabilidade) {
		this.avalResponsabilidade = avalResponsabilidade;
	}

	public EnumAvaliacaoAcomp getAvalConduta() {
		return avalConduta;
	}

	public void setAvalConduta(EnumAvaliacaoAcomp avalConduta) {
		this.avalConduta = avalConduta;
	}

	public EnumAvaliacaoAcomp getAvalDominioTecnico() {
		return avalDominioTecnico;
	}

	public void setAvalDominioTecnico(EnumAvaliacaoAcomp avalDominioTecnico) {
		this.avalDominioTecnico = avalDominioTecnico;
	}

	public EnumAvaliacaoAcomp getAvalHabilidades() {
		return avalHabilidades;
	}

	public void setAvalHabilidades(EnumAvaliacaoAcomp avalHabilidades) {
		this.avalHabilidades = avalHabilidades;
	}

	public EnumAvaliacaoAcomp getAvalEfetivacao() {
		return avalEfetivacao;
	}

	public void setAvalEfetivacao(EnumAvaliacaoAcomp avalEfetivacao) {
		this.avalEfetivacao = avalEfetivacao;
	}

}
