package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "CursoSiga", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class CursoSiga implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ano_base")
    private int anoBase;

    @Column(name = "sequencial")
    private int sequencial;

    @Column(name = "nome")
    private String nome;

    @Column(name = "id_pessoal_coordenador")
    private int idPessoalCoordenador;

    @Column(name = "ano_inicio")
    private int anoInicio;

    @Column(name = "creditos_disciplinas")
    private int creditosDisciplinas;

    @Column(name = "creditos_obrigatorios")
    private int creditosObrigatorios;

    @Column(name = "creditos_eletivos")
    private int creditosEletivos;

    @Column(name = "creditos_teses")
    private int creditosTeses;

    @Column(name = "creditos_outros")
    private int creditosOutros;

    @Column(name = "equivalencia_cred_carga_horaria")
    private int equivalenciaCredCargaHoraria;

    @Column(name = "id_pessoal_secretaria")
    private int idPessoalSecretaria;

    @Column(name = "id_pessoal_vice_coordenador")
    private int idPessoalViceCoordenador;

    @Column(name = "num_total_vagas")
    private int numTotalVagas;

    @Column(name = "duracao")
    private int duracao;

    @Column(name = "duracao_maxima")
    private int duracaoMaxima;

    @Column(name = "status")
    private int status;

    @Column(name = "carga_horaria_total")
    private int cargaHorariaTotal;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoSiga tipo;

    @Column(name = "carga_horaria_obrigatoria")
    private int cargaHorariaObrigatoria;

    @Column(name = "carga_horaria_optativa")
    private int cargaHorariaOptativa;

    @Column(name = "carga_horaria_atividade_formativa")
    private int cargaHorariaAtividadeFormativa;

    @Column(name = "estruturas")
    private List<String> estruturas;
    
    @Column(name = "historicoCorrecaoIdPessoal")
    private int historicoCorrecaoIdPessoal;

    @Column(name = "periodos")
    private List<String> periodos;

    @Column(name = "totalAlunos")
    private int totalAlunos;

    @Column(name = "totalComprovantesVacinacao")
    private int totalComprovantesVacinacao;

    @Column(name = "porcentagemAlunosComprovante")
    private Double porcentagemAlunosComprovante;

    @Column(name = "versao")
    private int versao;

    @Column(name = "nomeVersao")
    private String nomeVersao;

    @Column(name = "anoVersao")
    private int anoVersao;

    @Column(name = "vigencia")
    private String vigencia;

    @Column(name = "disciplinas")
    private List<String> disciplinas;

	public CursoSiga() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CursoSiga(Long id, int anoBase, int sequencial, String nome, int idPessoalCoordenador, int anoInicio,
			int creditosDisciplinas, int creditosObrigatorios, int creditosEletivos, int creditosTeses,
			int creditosOutros, int equivalenciaCredCargaHoraria, int idPessoalSecretaria, int idPessoalViceCoordenador,
			int numTotalVagas, int duracao, int duracaoMaxima, int status, int cargaHorariaTotal, TipoSiga tipo,
			int cargaHorariaObrigatoria, int cargaHorariaOptativa, int cargaHorariaAtividadeFormativa,
			List<String> estruturas, int historicoCorrecaoIdPessoal, List<String> periodos, int totalAlunos,
			int totalComprovantesVacinacao, Double porcentagemAlunosComprovante, int versao, String nomeVersao,
			int anoVersao, String vigencia, List<String> disciplinas) {
		super();
		this.id = id;
		this.anoBase = anoBase;
		this.sequencial = sequencial;
		this.nome = nome;
		this.idPessoalCoordenador = idPessoalCoordenador;
		this.anoInicio = anoInicio;
		this.creditosDisciplinas = creditosDisciplinas;
		this.creditosObrigatorios = creditosObrigatorios;
		this.creditosEletivos = creditosEletivos;
		this.creditosTeses = creditosTeses;
		this.creditosOutros = creditosOutros;
		this.equivalenciaCredCargaHoraria = equivalenciaCredCargaHoraria;
		this.idPessoalSecretaria = idPessoalSecretaria;
		this.idPessoalViceCoordenador = idPessoalViceCoordenador;
		this.numTotalVagas = numTotalVagas;
		this.duracao = duracao;
		this.duracaoMaxima = duracaoMaxima;
		this.status = status;
		this.cargaHorariaTotal = cargaHorariaTotal;
		this.tipo = tipo;
		this.cargaHorariaObrigatoria = cargaHorariaObrigatoria;
		this.cargaHorariaOptativa = cargaHorariaOptativa;
		this.cargaHorariaAtividadeFormativa = cargaHorariaAtividadeFormativa;
		this.estruturas = estruturas;
		this.historicoCorrecaoIdPessoal = historicoCorrecaoIdPessoal;
		this.periodos = periodos;
		this.totalAlunos = totalAlunos;
		this.totalComprovantesVacinacao = totalComprovantesVacinacao;
		this.porcentagemAlunosComprovante = porcentagemAlunosComprovante;
		this.versao = versao;
		this.nomeVersao = nomeVersao;
		this.anoVersao = anoVersao;
		this.vigencia = vigencia;
		this.disciplinas = disciplinas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAnoBase() {
		return anoBase;
	}

	public void setAnoBase(int anoBase) {
		this.anoBase = anoBase;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdPessoalCoordenador() {
		return idPessoalCoordenador;
	}

	public void setIdPessoalCoordenador(int idPessoalCoordenador) {
		this.idPessoalCoordenador = idPessoalCoordenador;
	}

	public int getAnoInicio() {
		return anoInicio;
	}

	public void setAnoInicio(int anoInicio) {
		this.anoInicio = anoInicio;
	}

	public int getCreditosDisciplinas() {
		return creditosDisciplinas;
	}

	public void setCreditosDisciplinas(int creditosDisciplinas) {
		this.creditosDisciplinas = creditosDisciplinas;
	}

	public int getCreditosObrigatorios() {
		return creditosObrigatorios;
	}

	public void setCreditosObrigatorios(int creditosObrigatorios) {
		this.creditosObrigatorios = creditosObrigatorios;
	}

	public int getCreditosEletivos() {
		return creditosEletivos;
	}

	public void setCreditosEletivos(int creditosEletivos) {
		this.creditosEletivos = creditosEletivos;
	}

	public int getCreditosTeses() {
		return creditosTeses;
	}

	public void setCreditosTeses(int creditosTeses) {
		this.creditosTeses = creditosTeses;
	}

	public int getCreditosOutros() {
		return creditosOutros;
	}

	public void setCreditosOutros(int creditosOutros) {
		this.creditosOutros = creditosOutros;
	}

	public int getEquivalenciaCredCargaHoraria() {
		return equivalenciaCredCargaHoraria;
	}

	public void setEquivalenciaCredCargaHoraria(int equivalenciaCredCargaHoraria) {
		this.equivalenciaCredCargaHoraria = equivalenciaCredCargaHoraria;
	}

	public int getIdPessoalSecretaria() {
		return idPessoalSecretaria;
	}

	public void setIdPessoalSecretaria(int idPessoalSecretaria) {
		this.idPessoalSecretaria = idPessoalSecretaria;
	}

	public int getIdPessoalViceCoordenador() {
		return idPessoalViceCoordenador;
	}

	public void setIdPessoalViceCoordenador(int idPessoalViceCoordenador) {
		this.idPessoalViceCoordenador = idPessoalViceCoordenador;
	}

	public int getNumTotalVagas() {
		return numTotalVagas;
	}

	public void setNumTotalVagas(int numTotalVagas) {
		this.numTotalVagas = numTotalVagas;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public int getDuracaoMaxima() {
		return duracaoMaxima;
	}

	public void setDuracaoMaxima(int duracaoMaxima) {
		this.duracaoMaxima = duracaoMaxima;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCargaHorariaTotal() {
		return cargaHorariaTotal;
	}

	public void setCargaHorariaTotal(int cargaHorariaTotal) {
		this.cargaHorariaTotal = cargaHorariaTotal;
	}

	public TipoSiga getTipo() {
		return tipo;
	}

	public void setTipo(TipoSiga tipo) {
		this.tipo = tipo;
	}

	public int getCargaHorariaObrigatoria() {
		return cargaHorariaObrigatoria;
	}

	public void setCargaHorariaObrigatoria(int cargaHorariaObrigatoria) {
		this.cargaHorariaObrigatoria = cargaHorariaObrigatoria;
	}

	public int getCargaHorariaOptativa() {
		return cargaHorariaOptativa;
	}

	public void setCargaHorariaOptativa(int cargaHorariaOptativa) {
		this.cargaHorariaOptativa = cargaHorariaOptativa;
	}

	public int getCargaHorariaAtividadeFormativa() {
		return cargaHorariaAtividadeFormativa;
	}

	public void setCargaHorariaAtividadeFormativa(int cargaHorariaAtividadeFormativa) {
		this.cargaHorariaAtividadeFormativa = cargaHorariaAtividadeFormativa;
	}

	public List<String> getEstruturas() {
		return estruturas;
	}

	public void setEstruturas(List<String> estruturas) {
		this.estruturas = estruturas;
	}

	public int getHistoricoCorrecaoIdPessoal() {
		return historicoCorrecaoIdPessoal;
	}

	public void setHistoricoCorrecaoIdPessoal(int historicoCorrecaoIdPessoal) {
		this.historicoCorrecaoIdPessoal = historicoCorrecaoIdPessoal;
	}

	public List<String> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<String> periodos) {
		this.periodos = periodos;
	}

	public int getTotalAlunos() {
		return totalAlunos;
	}

	public void setTotalAlunos(int totalAlunos) {
		this.totalAlunos = totalAlunos;
	}

	public int getTotalComprovantesVacinacao() {
		return totalComprovantesVacinacao;
	}

	public void setTotalComprovantesVacinacao(int totalComprovantesVacinacao) {
		this.totalComprovantesVacinacao = totalComprovantesVacinacao;
	}

	public Double getPorcentagemAlunosComprovante() {
		return porcentagemAlunosComprovante;
	}

	public void setPorcentagemAlunosComprovante(Double porcentagemAlunosComprovante) {
		this.porcentagemAlunosComprovante = porcentagemAlunosComprovante;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}

	public String getNomeVersao() {
		return nomeVersao;
	}

	public void setNomeVersao(String nomeVersao) {
		this.nomeVersao = nomeVersao;
	}

	public int getAnoVersao() {
		return anoVersao;
	}

	public void setAnoVersao(int anoVersao) {
		this.anoVersao = anoVersao;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public List<String> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<String> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
