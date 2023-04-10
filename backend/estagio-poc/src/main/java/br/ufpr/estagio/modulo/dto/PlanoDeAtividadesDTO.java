package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;

import br.ufpr.estagio.modulo.model.Supervisor;


public class PlanoDeAtividadesDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String local;
	private Supervisor supervisor;
	private String descricaoAtividades;
	
	public PlanoDeAtividadesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanoDeAtividadesDTO(long id, String local, Supervisor supervisor, String descricaoAtividades) {
		super();
		this.id = id;
		this.local = local;
		this.supervisor = supervisor;
		this.descricaoAtividades = descricaoAtividades;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
}
