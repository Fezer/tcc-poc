package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.Date;

import br.ufpr.estagio.modulo.model.Aluno;
import jakarta.persistence.Column;

public class DadosAuxiliaresDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;

	private String nomePai;

	private String estadoCivil;

	private int dependentes;

	private String grupoSanguineo;
	
	private String corDaPele;
	
	private String sexo;
	
	private String nomeMae;
	
	private String nacionalidade;
	
	private Date dataDeChegadaNoPais;
	
	private String orgaoEmissor;
	
	private String uf;
	
	private Date dataExpedicao;
	
	private String tituloEleitoral;
	
	private int zona;
	
	private int secao;
	
	private String certificadoMilitar;
	
	private String orgaoDeExpedicao;
	
	private String serie;
	
	private Date dataDeEmissao;
	
	private String estadoNascimento;
	
	private String cidadeNascimento;
	
	private String corRaca;
	
	private String expressaoGenero;
	
	private String autoIdentificacaoGenero;
	
	private String orientacaoSexual;
	
	private String emailInstitucional;
	
	private String tipoVaga;
	
	private String dataEmissaoTitulo;
	
	//private Aluno aluno;

	public DadosAuxiliaresDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DadosAuxiliaresDTO(long id, String nomePai, String estadoCivil, int dependentes, String grupoSanguineo,
			String corDaPele, String sexo, String nomeMae, String nacionalidade, Date dataDeChegadaNoPais,
			String orgaoEmissor, String uf, Date dataExpedicao, String tituloEleitoral, int zona, int secao,
			String certificadoMilitar, String orgaoDeExpedicao, String serie, Date dataDeEmissao,
			String estadoNascimento, String cidadeNascimento, String corRaca, String expressaoGenero,
			String autoIdentificacaoGenero, String orientacaoSexual, String emailInstitucional/*, Aluno aluno*/) {
		super();
		this.id = id;
		this.nomePai = nomePai;
		this.estadoCivil = estadoCivil;
		this.dependentes = dependentes;
		this.grupoSanguineo = grupoSanguineo;
		this.corDaPele = corDaPele;
		this.sexo = sexo;
		this.nomeMae = nomeMae;
		this.nacionalidade = nacionalidade;
		this.dataDeChegadaNoPais = dataDeChegadaNoPais;
		this.orgaoEmissor = orgaoEmissor;
		this.uf = uf;
		this.dataExpedicao = dataExpedicao;
		this.tituloEleitoral = tituloEleitoral;
		this.zona = zona;
		this.secao = secao;
		this.certificadoMilitar = certificadoMilitar;
		this.orgaoDeExpedicao = orgaoDeExpedicao;
		this.serie = serie;
		this.dataDeEmissao = dataDeEmissao;
		this.estadoNascimento = estadoNascimento;
		this.cidadeNascimento = cidadeNascimento;
		this.corRaca = corRaca;
		this.expressaoGenero = expressaoGenero;
		this.autoIdentificacaoGenero = autoIdentificacaoGenero;
		this.orientacaoSexual = orientacaoSexual;
		this.emailInstitucional = emailInstitucional;
		//this.aluno = aluno;
	}

	public DadosAuxiliaresDTO(long id, String nomePai, String estadoCivil, int dependentes, String grupoSanguineo,
			String corDaPele, String sexo, String nomeMae, String nacionalidade, Date dataDeChegadaNoPais,
			String orgaoEmissor, String uf, Date dataExpedicao, String tituloEleitoral, int zona, int secao,
			String certificadoMilitar, String orgaoDeExpedicao, String serie, Date dataDeEmissao,
			String estadoNascimento, String cidadeNascimento, String corRaca, String expressaoGenero,
			String autoIdentificacaoGenero, String orientacaoSexual, String emailInstitucional, String tipoVaga,
			String dataEmissaoTitulo) {
		super();
		this.id = id;
		this.nomePai = nomePai;
		this.estadoCivil = estadoCivil;
		this.dependentes = dependentes;
		this.grupoSanguineo = grupoSanguineo;
		this.corDaPele = corDaPele;
		this.sexo = sexo;
		this.nomeMae = nomeMae;
		this.nacionalidade = nacionalidade;
		this.dataDeChegadaNoPais = dataDeChegadaNoPais;
		this.orgaoEmissor = orgaoEmissor;
		this.uf = uf;
		this.dataExpedicao = dataExpedicao;
		this.tituloEleitoral = tituloEleitoral;
		this.zona = zona;
		this.secao = secao;
		this.certificadoMilitar = certificadoMilitar;
		this.orgaoDeExpedicao = orgaoDeExpedicao;
		this.serie = serie;
		this.dataDeEmissao = dataDeEmissao;
		this.estadoNascimento = estadoNascimento;
		this.cidadeNascimento = cidadeNascimento;
		this.corRaca = corRaca;
		this.expressaoGenero = expressaoGenero;
		this.autoIdentificacaoGenero = autoIdentificacaoGenero;
		this.orientacaoSexual = orientacaoSexual;
		this.emailInstitucional = emailInstitucional;
		this.tipoVaga = tipoVaga;
		this.dataEmissaoTitulo = dataEmissaoTitulo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public int getDependentes() {
		return dependentes;
	}

	public void setDependentes(int dependentes) {
		this.dependentes = dependentes;
	}

	public String getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public void setGrupoSanguineo(String grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public String getCorDaPele() {
		return corDaPele;
	}

	public void setCorDaPele(String corDaPele) {
		this.corDaPele = corDaPele;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Date getDataDeChegadaNoPais() {
		return dataDeChegadaNoPais;
	}

	public void setDataDeChegadaNoPais(Date dataDeChegadaNoPais) {
		this.dataDeChegadaNoPais = dataDeChegadaNoPais;
	}

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Date getDataExpedicao() {
		return dataExpedicao;
	}

	public void setDataExpedicao(Date dataExpedicao) {
		this.dataExpedicao = dataExpedicao;
	}

	public String getTituloEleitoral() {
		return tituloEleitoral;
	}

	public void setTituloEleitoral(String tituloEleitoral) {
		this.tituloEleitoral = tituloEleitoral;
	}

	public int getZona() {
		return zona;
	}

	public void setZona(int zona) {
		this.zona = zona;
	}

	public int getSecao() {
		return secao;
	}

	public void setSecao(int secao) {
		this.secao = secao;
	}

	public String getCertificadoMilitar() {
		return certificadoMilitar;
	}

	public void setCertificadoMilitar(String certificadoMilitar) {
		this.certificadoMilitar = certificadoMilitar;
	}

	public String getOrgaoDeExpedicao() {
		return orgaoDeExpedicao;
	}

	public void setOrgaoDeExpedicao(String orgaoDeExpedicao) {
		this.orgaoDeExpedicao = orgaoDeExpedicao;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Date getDataDeEmissao() {
		return dataDeEmissao;
	}

	public void setDataDeEmissao(Date dataDeEmissao) {
		this.dataDeEmissao = dataDeEmissao;
	}

	public String getEstadoNascimento() {
		return estadoNascimento;
	}

	public void setEstadoNascimento(String estadoNascimento) {
		this.estadoNascimento = estadoNascimento;
	}

	public String getCidadeNascimento() {
		return cidadeNascimento;
	}

	public void setCidadeNascimento(String cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}

	public String getCorRaca() {
		return corRaca;
	}

	public void setCorRaca(String corRaca) {
		this.corRaca = corRaca;
	}

	public String getExpressaoGenero() {
		return expressaoGenero;
	}

	public void setExpressaoGenero(String expressaoGenero) {
		this.expressaoGenero = expressaoGenero;
	}

	public String getAutoIdentificacaoGenero() {
		return autoIdentificacaoGenero;
	}

	public void setAutoIdentificacaoGenero(String autoIdentificacaoGenero) {
		this.autoIdentificacaoGenero = autoIdentificacaoGenero;
	}

	public String getOrientacaoSexual() {
		return orientacaoSexual;
	}

	public void setOrientacaoSexual(String orientacaoSexual) {
		this.orientacaoSexual = orientacaoSexual;
	}

	public String getEmailInstitucional() {
		return emailInstitucional;
	}

	public void setEmailInstitucional(String emailInstitucional) {
		this.emailInstitucional = emailInstitucional;
	}

	/*public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}*/

	public String getTipoVaga() {
		return tipoVaga;
	}

	public void setTipoVaga(String tipoVaga) {
		this.tipoVaga = tipoVaga;
	}

	public String getDataEmissaoTitulo() {
		return dataEmissaoTitulo;
	}

	public void setDataEmissaoTitulo(String dataEmissaoTitulo) {
		this.dataEmissaoTitulo = dataEmissaoTitulo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
