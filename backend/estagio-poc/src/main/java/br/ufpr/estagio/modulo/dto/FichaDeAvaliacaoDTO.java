package br.ufpr.estagio.modulo.dto;

import java.util.List;

import br.ufpr.estagio.modulo.enums.EnumAvaliacao;
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
	private EnumAvaliacao avalPontualidade;
	private EnumAvaliacao avalCriatividade;
	private EnumAvaliacao avalProtagonismo;
	private EnumAvaliacao avalResponsabilidade;
	private EnumAvaliacao avalConduta;
	private EnumAvaliacao avalDominioTecnico;
	private EnumAvaliacao avalHabilidades;
	private EnumAvaliacao avalEfetivacao;
	private boolean upload;
	private List<String> arquivos;
	
	public FichaDeAvaliacaoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FichaDeAvaliacaoDTO(long id, Estagio estagio, int totalHorasEstagioEfetivamenteRealizadas,
			boolean atividadesForamRealizadas, String atividadesRealizadasConsideracoes,
			EnumAvaliacaoAcomp acompanhamentoOrientador, String acompanhamentoOrientadorComentario,
			EnumAvaliacaoAcomp acompanhamentoCoordenador, String acompanhamentoCoordenadorComentario,
			String contribuicaoEstagio, EnumAvaliacao avalPontualidade, EnumAvaliacao avalCriatividade,
			EnumAvaliacao avalProtagonismo, EnumAvaliacao avalResponsabilidade,
			EnumAvaliacao avalConduta, EnumAvaliacao avalDominioTecnico, EnumAvaliacao avalHabilidades,
			EnumAvaliacao avalEfetivacao, boolean upload) {
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
		this.upload = upload;
	}

	public FichaDeAvaliacaoDTO(long id, Estagio estagio, int totalHorasEstagioEfetivamenteRealizadas,
			boolean atividadesForamRealizadas, String atividadesRealizadasConsideracoes,
			EnumAvaliacaoAcomp acompanhamentoOrientador, String acompanhamentoOrientadorComentario,
			EnumAvaliacaoAcomp acompanhamentoCoordenador, String acompanhamentoCoordenadorComentario,
			String contribuicaoEstagio, EnumAvaliacao avalPontualidade, EnumAvaliacao avalCriatividade,
			EnumAvaliacao avalProtagonismo, EnumAvaliacao avalResponsabilidade, EnumAvaliacao avalConduta,
			EnumAvaliacao avalDominioTecnico, EnumAvaliacao avalHabilidades, EnumAvaliacao avalEfetivacao,
			boolean upload, List<String> arquivos) {
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
		this.upload = upload;
		this.arquivos = arquivos;
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

	public EnumAvaliacao getAvalPontualidade() {
		return avalPontualidade;
	}

	public void setAvalPontualidade(EnumAvaliacao avalPontualidade) {
		this.avalPontualidade = avalPontualidade;
	}

	public EnumAvaliacao getAvalCriatividade() {
		return avalCriatividade;
	}

	public void setAvalCriatividade(EnumAvaliacao avalCriatividade) {
		this.avalCriatividade = avalCriatividade;
	}

	public EnumAvaliacao getAvalProtagonismo() {
		return avalProtagonismo;
	}

	public void setAvalProtagonismo(EnumAvaliacao avalProtagonismo) {
		this.avalProtagonismo = avalProtagonismo;
	}

	public EnumAvaliacao getAvalResponsabilidade() {
		return avalResponsabilidade;
	}

	public void setAvalResponsabilidade(EnumAvaliacao avalResponsabilidade) {
		this.avalResponsabilidade = avalResponsabilidade;
	}

	public EnumAvaliacao getAvalConduta() {
		return avalConduta;
	}

	public void setAvalConduta(EnumAvaliacao avalConduta) {
		this.avalConduta = avalConduta;
	}

	public EnumAvaliacao getAvalDominioTecnico() {
		return avalDominioTecnico;
	}

	public void setAvalDominioTecnico(EnumAvaliacao avalDominioTecnico) {
		this.avalDominioTecnico = avalDominioTecnico;
	}

	public EnumAvaliacao getAvalHabilidades() {
		return avalHabilidades;
	}

	public void setAvalHabilidades(EnumAvaliacao avalHabilidades) {
		this.avalHabilidades = avalHabilidades;
	}

	public EnumAvaliacao getAvalEfetivacao() {
		return avalEfetivacao;
	}

	public void setAvalEfetivacao(EnumAvaliacao avalEfetivacao) {
		this.avalEfetivacao = avalEfetivacao;
	}

	public boolean isUpload() {
		return upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}

	public List<String> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<String> arquivos) {
		this.arquivos = arquivos;
	}

}
