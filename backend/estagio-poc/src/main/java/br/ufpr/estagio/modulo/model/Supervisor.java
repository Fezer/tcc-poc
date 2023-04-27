/*package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
//@Table(name = "supervisor", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
@Table(name = "supervisor")
//@Inheritance(strategy = InheritanceType.JOINED)
public class Supervisor extends Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name = "id")
//	private long id;

	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "formacao")
	private String formacao;
	
	@JsonIgnore
	@OneToMany(mappedBy="supervisor", cascade=CascadeType.REMOVE)
	private List<TermoDeEstagio> termoDeEstagio;
	
	@JsonIgnore
	@OneToMany(mappedBy="supervisor", cascade=CascadeType.REMOVE)
	private List<Estagio> estagio;

	@JsonIgnore
	@OneToMany(mappedBy="supervisor", cascade=CascadeType.REMOVE)
	private List<PlanoDeAtividades> planoDeAtividades;
	
	
	public Supervisor() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Supervisor(long id, String nome, String telefone, String cpf, String formacao,
			List<TermoDeEstagio> termoDeEstagio, List<Estagio> estagio, List<PlanoDeAtividades> planoDeAtividades) {
		super(id, nome, telefone);
		this.cpf = cpf;
		this.formacao = formacao;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
		this.planoDeAtividades = planoDeAtividades;
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

	public long getId() {
		return super.getId();
	}

	public void setId(long id) {
		super.setId(id);
	}

	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getFormacao() {
		return formacao;
	}


	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}


	public List<TermoDeEstagio> getTermoDeEstagio() {
		return termoDeEstagio;
	}


	public void setTermoDeEstagio(List<TermoDeEstagio> termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}


	public List<Estagio> getEstagio() {
		return estagio;
	}


	public void setEstagio(List<Estagio> estagio) {
		this.estagio = estagio;
	}


	public List<PlanoDeAtividades> getPlanoDeAtividades() {
		return planoDeAtividades;
	}


	public void setPlanoDeAtividades(List<PlanoDeAtividades> planoDeAtividades) {
		this.planoDeAtividades = planoDeAtividades;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}*/
