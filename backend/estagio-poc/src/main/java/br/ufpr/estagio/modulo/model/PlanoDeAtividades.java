package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name="supervisor_id", referencedColumnName="id", nullable=true)
	private Supervisor supervisor;
	
	@Column(name = "descricaoAtividades")
	private String descricaoAtividades;
	
	//@OneToOne
	//@JoinColumn(name="estagio")
	@OneToOne
	@JoinColumn(name="estagio_id", referencedColumnName="id", nullable=false)
	private Estagio estagio;
	
	//@OneToOne
	//@JoinColumn(name="termo_de_estagio")
	@OneToOne
	@JoinColumn(name="termo_de_estagio_id", referencedColumnName="id", nullable=false)
	private TermoDeEstagio termoDeEstagio;
	
	public PlanoDeAtividades() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanoDeAtividades(long id, String local, Supervisor supervisor, String descricaoAtividades, Estagio estagio,
			TermoDeEstagio termoDeEstagio) {
		super();
		this.id = id;
		this.local = local;
		this.supervisor = supervisor;
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

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
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
