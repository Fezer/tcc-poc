package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aluno")
public class Aluno extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Column(name = "idDiscente")
	private long idDiscente;
	
	@Column(name = "isPcd")
	private boolean isPcd;
	
	@Column(name = "matricula")
	private String matricula;
	
	@Column(name = "periodoAtual")
	private int periodoAtual;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "dataNascimento")
	private Date dataNascimento;
	
	@Column(name = "turno")
	private String turno;
	
	@Column(name = "coordenador")
	private String coordenador;
	
	@Column(name = "idPrograma")
	private String idPrograma;
	
	@Column(name = "ira")
	private String ira;
	
	@Column(name = "idCurso")
	private Long idCurso;
	
	@Column(name = "isMatriculado")
	private boolean isMatriculado;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="curso_id", referencedColumnName="id", nullable=true)
	private Curso curso;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="dados_auxiliares_id", referencedColumnName="id", nullable=true)
	private DadosAuxiliares dadosAuxiliares;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="dados_bancarios_id", referencedColumnName="id", nullable=true)
	private DadosBancarios dadosBancarios;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="endereco_id", referencedColumnName="id", nullable=true)
	private Endereco endereco;
	
//	@JsonIgnore
//	@ManyToMany(mappedBy = "aluno", cascade=CascadeType.REMOVE)
//	private List<Disciplina> disciplina;
	
	@JsonIgnore
	@OneToMany(mappedBy="aluno", cascade=CascadeType.REMOVE)
	private List<Estagio> estagio;
	
	public Aluno() {
		super();
//		this.disciplina = new ArrayList<Disciplina>();
		this.estagio = new ArrayList<Estagio>();
	}


	public Aluno(long id, String nome, String telefone, long idDiscente, boolean isPcd, String matricula,
			int periodoAtual, String rg, String cpf, String email, Date dataNascimento, String turno,
			String coordenador, String idPrograma, String ira, Long idCurso, boolean isMatriculado, Curso curso,
			DadosAuxiliares dadosAuxiliares, DadosBancarios dadosBancarios, Endereco endereco,
			/**List<Disciplina> disciplina,**/ List<Estagio> estagio) {
		super(id, nome, telefone);
		this.idDiscente = idDiscente;
		this.isPcd = isPcd;
		this.matricula = matricula;
		this.periodoAtual = periodoAtual;
		this.rg = rg;
		this.cpf = cpf;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.turno = turno;
		this.coordenador = coordenador;
		this.idPrograma = idPrograma;
		this.ira = ira;
		this.idCurso = idCurso;
		this.isMatriculado = isMatriculado;
		this.curso = curso;
		this.dadosAuxiliares = dadosAuxiliares;
		this.dadosBancarios = dadosBancarios;
		this.endereco = endereco;
//		this.disciplina = disciplina;
		this.estagio = estagio;
	}


	public long getId() {
		return super.getId();
	}

	public void setId(long id) {
		super.setId(id);
	}
	
	public String getNome() {
		return super.getNome();
	}
	
	public void setNome(String nome) {
		super.setNome(nome);
	}
	
	public String getTelefone() {
		return super.getTelefone();
	}
	
	public void setTelefone(String telefone) {
		super.setTelefone(telefone);
	}

	public long getIdDiscente() {
		return idDiscente;
	}

	public void setIdDiscente(long idDiscente) {
		this.idDiscente = idDiscente;
	}
	
	public boolean isPcd() {
		return isPcd;
	}

	public void setPcd(boolean isPcd) {
		this.isPcd = isPcd;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public int getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(int periodoAtual) {
		this.periodoAtual = periodoAtual;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTurno() {
		return turno;
	}


	public void setTurno(String turno) {
		this.turno = turno;
	}


	public String getCoordenador() {
		return coordenador;
	}


	public void setCoordenador(String coordenador) {
		this.coordenador = coordenador;
	}


	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	public String getIdPrograma() {
		return idPrograma;
	}


	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}


	public String getIra() {
		return ira;
	}


	public void setIra(String ira) {
		this.ira = ira;
	}


	public Long getIdCurso() {
		return idCurso;
	}


	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}


	public boolean isMatriculado() {
		return isMatriculado;
	}


	public void setMatriculado(boolean isMatriculado) {
		this.isMatriculado = isMatriculado;
	}


	public DadosAuxiliares getDadosAuxiliares() {
		return dadosAuxiliares;
	}

	public void setDadosAuxiliares(DadosAuxiliares dadosAuxiliares) {
		this.dadosAuxiliares = dadosAuxiliares;
	}

	public DadosBancarios getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(DadosBancarios dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

//	public List<Disciplina> getDisciplina() {
//		return disciplina;
//	}
//
//	public void setDisciplina(List<Disciplina> disciplina) {
//		this.disciplina = disciplina;
//	}

	public List<Estagio> getEstagio() {
		return estagio;
	}

	public void setEstagio(List<Estagio> estagio) {
		this.estagio = estagio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}


	public Estagio novoEstagio() {
		Estagio estagio = new Estagio();
		estagio.setAluno(this);
		estagio.novoTermoCompromisso();
		this.setEstagio(getEstagio());
		return estagio;
	}

}
