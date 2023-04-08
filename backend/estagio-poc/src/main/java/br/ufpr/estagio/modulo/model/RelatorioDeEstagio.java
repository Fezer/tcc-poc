package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

import br.ufpr.estagio.modulo.enums.EnumAvaliacao;
import br.ufpr.estagio.modulo.enums.EnumAvaliacaoAtividades;
import br.ufpr.estagio.modulo.enums.EnumTipoRelatorio;
import jakarta.persistence.*;

@Entity
@Table(name = "relatorio_de_estagio", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class RelatorioDeEstagio implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name="estagio_id", referencedColumnName="id",nullable=false)
	private Estagio estagio;
	
	@Column(name = "ciencia_orientador")
	private boolean cienciaOrientador;
	
	@Column(name = "tipo_relatorio")
	private EnumTipoRelatorio tipoRelatorio;
	
	@Column(name = "aval_atividades")
	private EnumAvaliacaoAtividades avalAtividades;
	
	@Column(name = "aval_formacao_profissional")
	private EnumAvaliacao avalFormacaoProfissional;
	
	@Column(name = "aval_relacoes_interpessoais")
	private EnumAvaliacao avalRelacoesInterpessoais;
	
	@Column(name = "aval_desenvolvimento_atividades")
	private EnumAvaliacao avalDesenvolvimentoAtividades;
	
	@Column(name = "aval_contribuicao_estagio")
	private EnumAvaliacao avalContribuicaoEstagio;
	
	@Column(name = "aval_efetivacao")
	private EnumAvaliacao avalEfetivacao;
	
	@Column(name = "consideracoes")
	private String consideracoes;
	
	public RelatorioDeEstagio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RelatorioDeEstagio(long id, Estagio estagio, boolean cienciaOrientador, EnumTipoRelatorio tipoRelatorio,
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
