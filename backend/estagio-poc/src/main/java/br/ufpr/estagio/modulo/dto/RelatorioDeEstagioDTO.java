package br.ufpr.estagio.modulo.dto;

import br.ufpr.estagio.modulo.enums.EnumAvaliacao;
import br.ufpr.estagio.modulo.enums.EnumAvaliacaoAtividades;
import br.ufpr.estagio.modulo.enums.EnumTipoRelatorio;
import br.ufpr.estagio.modulo.model.Estagio;

public class RelatorioDeEstagioDTO {
	
	private long id;
	private Estagio estagio;
	private boolean cienciaOrientador;
	private EnumTipoRelatorio tipoRelatorio;
	private EnumAvaliacaoAtividades avalAtividades;
	private EnumAvaliacao avalFormacaoProfissional;
	private EnumAvaliacao avalRelacoesInterpessoais;
	private EnumAvaliacao avalDesenvolvimentoAtividades;
	private EnumAvaliacao avalContribuicaoEstagio;
	private EnumAvaliacao avalEfetivacao;
	private String consideracoes;
	
	public RelatorioDeEstagioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RelatorioDeEstagioDTO(long id, Estagio estagio, boolean cienciaOrientador, EnumTipoRelatorio tipoRelatorio,
			EnumAvaliacaoAtividades avalAtividades, EnumAvaliacao avalFormacaoProfissional, EnumAvaliacao avalRelacoesInterpessoais,
			EnumAvaliacao avalDesenvolvimentoAtividades, EnumAvaliacao avalContribuicaoEstagio, EnumAvaliacao avalEfetivacao,
			String consideracoes) {
		super();
		this.id = id;
		this.estagio = estagio;
		this.cienciaOrientador = cienciaOrientador;
		this.tipoRelatorio = tipoRelatorio;
		this.avalAtividades = avalAtividades;
		this.avalFormacaoProfissional = avalFormacaoProfissional;
		this.avalRelacoesInterpessoais = avalRelacoesInterpessoais;
		this.avalDesenvolvimentoAtividades = avalDesenvolvimentoAtividades;
		this.avalContribuicaoEstagio = avalContribuicaoEstagio;
		this.avalEfetivacao = avalEfetivacao;
		this.consideracoes = consideracoes;
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

	public boolean isCienciaOrientador() {
		return cienciaOrientador;
	}

	public void setCienciaOrientador(boolean cienciaOrientador) {
		this.cienciaOrientador = cienciaOrientador;
	}

	public EnumTipoRelatorio getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(EnumTipoRelatorio tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public EnumAvaliacaoAtividades getAvalAtividades() {
		return avalAtividades;
	}

	public void setAvalAtividades(EnumAvaliacaoAtividades avalAtividades) {
		this.avalAtividades = avalAtividades;
	}

	public EnumAvaliacao getAvalFormacaoProfissional() {
		return avalFormacaoProfissional;
	}

	public void setAvalFormacaoProfissional(EnumAvaliacao avalFormacaoProfissional) {
		this.avalFormacaoProfissional = avalFormacaoProfissional;
	}

	public EnumAvaliacao getAvalRelacoesInterpessoais() {
		return avalRelacoesInterpessoais;
	}

	public void setAvalRelacoesInterpessoais(EnumAvaliacao avalRelacoesInterpessoais) {
		this.avalRelacoesInterpessoais = avalRelacoesInterpessoais;
	}

	public EnumAvaliacao getAvalDesenvolvimentoAtividades() {
		return avalDesenvolvimentoAtividades;
	}

	public void setAvalDesenvolvimentoAtividades(EnumAvaliacao avalDesenvolvimentoAtividades) {
		this.avalDesenvolvimentoAtividades = avalDesenvolvimentoAtividades;
	}

	public EnumAvaliacao getAvalContribuicaoEstagio() {
		return avalContribuicaoEstagio;
	}

	public void setAvalContribuicaoEstagio(EnumAvaliacao avalContribuicaoEstagio) {
		this.avalContribuicaoEstagio = avalContribuicaoEstagio;
	}

	public EnumAvaliacao getAvalEfetivacao() {
		return avalEfetivacao;
	}

	public void setAvalEfetivacao(EnumAvaliacao avalEfetivacao) {
		this.avalEfetivacao = avalEfetivacao;
	}

	public String getConsideracoes() {
		return consideracoes;
	}

	public void setConsideracoes(String consideracoes) {
		this.consideracoes = consideracoes;
	}
	
}
