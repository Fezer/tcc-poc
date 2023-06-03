package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
@Table(name = "plano_de_atividades", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class PlanoDeAtividades implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "local")
	private String local;
	
	@Column(name = "nomeSupervisor")
	private String nomeSupervisor;
	
	@Column(name = "telefoneSupervisor")
	private String telefoneSupervisor;
	
	@Column(name = "cpfSupervisor")
	private String cpfSupervisor;
	
	@Column(name = "formacaoSupervisor")
	private String formacaoSupervisor;
	
	@Column(name = "descricaoAtividades", length = 3000)
	private String descricaoAtividades;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="estagio_id", referencedColumnName="id", nullable=true)
	private Estagio estagio;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="termo_de_estagio_id", referencedColumnName="id", nullable=true)
	private TermoDeEstagio termoDeEstagio;
	
	public PlanoDeAtividades() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PlanoDeAtividades(long id, String local, String nomeSupervisor, String telefoneSupervisor,
			String cpfSupervisor, String formacaoSupervisor, String descricaoAtividades, Estagio estagio,
			TermoDeEstagio termoDeEstagio) {
		super();
		this.id = id;
		this.local = local;
		this.nomeSupervisor = nomeSupervisor;
		this.telefoneSupervisor = telefoneSupervisor;
		this.cpfSupervisor = cpfSupervisor;
		this.formacaoSupervisor = formacaoSupervisor;
		this.descricaoAtividades = descricaoAtividades;
		this.estagio = estagio;
		this.termoDeEstagio = termoDeEstagio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getNomeSupervisor() {
		return nomeSupervisor;
	}

	public void setNomeSupervisor(String nomeSupervisor) {
		this.nomeSupervisor = nomeSupervisor;
	}

	public String getTelefoneSupervisor() {
		return telefoneSupervisor;
	}

	public void setTelefoneSupervisor(String telefoneSupervisor) {
		this.telefoneSupervisor = telefoneSupervisor;
	}

	public String getCpfSupervisor() {
		return cpfSupervisor;
	}

	public void setCpfSupervisor(String cpfSupervisor) {
		this.cpfSupervisor = cpfSupervisor;
	}

	public String getFormacaoSupervisor() {
		return formacaoSupervisor;
	}

	public void setFormacaoSupervisor(String formacaoSupervisor) {
		this.formacaoSupervisor = formacaoSupervisor;
	}

	public String getDescricaoAtividades() {
		return descricaoAtividades;
	}

	public void setDescricaoAtividades(String descricaoAtividades) {
		this.descricaoAtividades = descricaoAtividades;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public TermoDeEstagio getTermoDeEstagio() {
		return termoDeEstagio;
	}

	public void setTermoDeEstagio(TermoDeEstagio termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}

}
