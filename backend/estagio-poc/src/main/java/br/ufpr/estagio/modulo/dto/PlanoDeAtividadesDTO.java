package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;


public class PlanoDeAtividadesDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String local;
	private String nomeSupervisor;
	private String telefoneSupervisor;
	private String cpfSupervisor;
	private String formacaoSupervisor;
	private String descricaoAtividades;
	
	public PlanoDeAtividadesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanoDeAtividadesDTO(long id, String local, String nomeSupervisor, String telefoneSupervisor,
			String cpfSupervisor, String formacaoSupervisor, String descricaoAtividades) {
		super();
		this.id = id;
		this.local = local;
		this.nomeSupervisor = nomeSupervisor;
		this.telefoneSupervisor = telefoneSupervisor;
		this.cpfSupervisor = cpfSupervisor;
		this.formacaoSupervisor = formacaoSupervisor;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
}
