package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "supervisor", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Supervisor extends Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "formacao")
	private String formacao;
	
	//@Column(name = "termo_de_estagio")
	@OneToMany(mappedBy="supervisor", cascade=CascadeType.ALL)
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	
	//@Column(name = "estagio")
	@OneToMany(mappedBy="supervisor", cascade=CascadeType.ALL)
	private ArrayList<Estagio> estagio;

	//@Column(name = "plano_de_atividades")
	@OneToMany(mappedBy="supervisor", cascade=CascadeType.ALL)
	private ArrayList<PlanoDeAtividades> planoDeAtividades;
	
	
	public Supervisor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Supervisor(long id, String cpf, String formacao, ArrayList<TermoDeEstagio> termoDeEstagio,
			ArrayList<Estagio> estagio) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.formacao = formacao;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public ArrayList<TermoDeEstagio> getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(ArrayList<TermoDeEstagio> termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}

	public ArrayList<Estagio> getEstagio() {
		return estagio;
	}

	public void setEstagio(ArrayList<Estagio> estagio) {
		this.estagio = estagio;
	}
	
}
