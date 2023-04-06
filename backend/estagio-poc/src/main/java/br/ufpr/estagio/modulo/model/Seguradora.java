package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "seguradora", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Seguradora implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "apolice")
	//@OneToMany(mappedBy="seguradora")
	private ArrayList<Apolice> apolice;
	
	@Column(name = "termo_de_estagio")
	//@OneToMany(mappedBy="seguradora")
	private ArrayList<TermoDeEstagio> termoDeEstagio;
	
	@Column(name = "estagio")
	//@OneToMany(mappedBy="seguradora")
	private ArrayList<Estagio> estagio;
	
	public Seguradora() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seguradora(long id, String nome, ArrayList<Apolice> apolice, ArrayList<TermoDeEstagio> termoDeEstagio,
			ArrayList<Estagio> estagio) {
		super();
		this.id = id;
		this.nome = nome;
		this.apolice = apolice;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Apolice> getApolice() {
		return apolice;
	}

	public void setApolice(ArrayList<Apolice> apolice) {
		this.apolice = apolice;
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
