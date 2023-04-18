package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "seguradora", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Seguradora extends RepresentationModel<Seguradora> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "nome")
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy="seguradora", cascade=CascadeType.REMOVE)
	private List<Apolice> apolice;
	
	@JsonIgnore
	@OneToMany(mappedBy="seguradora", cascade=CascadeType.REMOVE)
	private List<TermoDeEstagio> termoDeEstagio;
	
	@JsonIgnore
	@OneToMany(mappedBy="seguradora", cascade=CascadeType.REMOVE)
	private List<Estagio> estagio;
	
	public Seguradora() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seguradora(long id, String nome, List<Apolice> apolice, List<TermoDeEstagio> termoDeEstagio,
			List<Estagio> estagio) {
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

	public List<Apolice> getApolice() {
		return apolice;
	}

	public void setApolice(List<Apolice> apolice) {
		this.apolice = apolice;
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
	
	
}
