package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "dados_auxiliares", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class DadosAuxiliares implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "nomeDoPai")
	private String nomeDoPai;
	
	@Column(name = "estadoCivil")
	private String estadoCivil;
	
	@Column(name = "dependentes")
	private int dependentes;
	
	@Column(name = "grupoSanguineo")
	private String grupoSanguineo;
	
	@Column(name = "corDaPele")
	private String corDaPele;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "nomeDaMae")
	private String nomeDaMae;
	
	@Column(name = "nacionalidade")
	private String nacionalidade;
	
	@Column(name = "dataDeChegadaNoPais")
	private Date dataDeChegadaNoPais;
	
	@Column(name = "orgaoEmissor")
	private String orgaoEmissor;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "dataExpedicao")
	private Date dataExpedicao;
	
	@Column(name = "tituloEleitoral")
	private String tituloEleitoral;
	
	@Column(name = "zona")
	private int zona;
	
	@Column(name = "secao")
	private int secao;
	
	@Column(name = "certificadoMilitar")
	private String certificadoMilitar;
	
	@Column(name = "orgaoDeExpedicao")
	private String orgaoDeExpedicao;
	
	@Column(name = "serie")
	private String serie;
	
	@Column(name = "dataDeEmissao")
	private Date dataDeEmissao;
	
	@Column(name = "cidadeDeNascimento")
	private String cidadeDeNascimento;
	
	@OneToOne
	@JoinColumn(name="aluno_id", referencedColumnName="id", nullable=false)
	private Aluno aluno;

	public DadosAuxiliares() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DadosAuxiliares(long id, String nomeDoPai, String estadoCivil, int dependentes, String grupoSanguineo,
			String corDaPele, String sexo, String nomeDaMae, String nacionalidade, Date dataDeChegadaNoPais,
			String orgaoEmissor, String uf, Date dataExpedicao, String tituloEleitoral, int zona, int secao,
			String certificadoMilitar, String orgaoDeExpedicao, String serie, Date dataDeEmissao,
			String cidadeDeNascimento, Aluno aluno) {
		super();
		this.id = id;
		this.nomeDoPai = nomeDoPai;
		this.estadoCivil = estadoCivil;
		this.dependentes = dependentes;
		this.grupoSanguineo = grupoSanguineo;
		this.corDaPele = corDaPele;
		this.sexo = sexo;
		this.nomeDaMae = nomeDaMae;
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
		this.cidadeDeNascimento = cidadeDeNascimento;
		this.aluno = aluno;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeDoPai() {
		return nomeDoPai;
	}

	public void setNomeDoPai(String nomeDoPai) {
		this.nomeDoPai = nomeDoPai;
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

	public String getNomeDaMae() {
		return nomeDaMae;
	}

	public void setNomeDaMae(String nomeDaMae) {
		this.nomeDaMae = nomeDaMae;
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

	public String getCidadeDeNascimento() {
		return cidadeDeNascimento;
	}

	public void setCidadeDeNascimento(String cidadeDeNascimento) {
		this.cidadeDeNascimento = cidadeDeNascimento;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
