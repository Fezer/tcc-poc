package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ciencia_coordenacao", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class CienciaCoordenacao implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	// adicionei parecer pq consta no diagrama. vamos manter aqui?
	@Column(name = "parecer")
	private String parecer;
	
	@Column(name = "cienciaIRA")
	private boolean cienciaIRA;
	
	@Column(name = "cienciaPlanoAtividades")
	private boolean cienciaPlanoAtividades;
	
	@Column(name = "cienciaFormacaoSupervisor")
	private boolean cienciaFormacaoSupervisor;

	public CienciaCoordenacao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CienciaCoordenacao(long id, String parecer, boolean cienciaIRA, boolean cienciaPlanoAtividades,
			boolean cienciaFormacaoSupervisor) {
		super();
		this.id = id;
		this.parecer = parecer;
		this.cienciaIRA = cienciaIRA;
		this.cienciaPlanoAtividades = cienciaPlanoAtividades;
		this.cienciaFormacaoSupervisor = cienciaFormacaoSupervisor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public boolean isCienciaIRA() {
		return cienciaIRA;
	}

	public void setCienciaIRA(boolean cienciaIRA) {
		this.cienciaIRA = cienciaIRA;
	}

	public boolean isCienciaPlanoAtividades() {
		return cienciaPlanoAtividades;
	}

	public void setCienciaPlanoAtividades(boolean cienciaPlanoAtividades) {
		this.cienciaPlanoAtividades = cienciaPlanoAtividades;
	}

	public boolean isCienciaFormacaoSupervisor() {
		return cienciaFormacaoSupervisor;
	}

	public void setCienciaFormacaoSupervisor(boolean cienciaFormacaoSupervisor) {
		this.cienciaFormacaoSupervisor = cienciaFormacaoSupervisor;
	}
	
	
}
