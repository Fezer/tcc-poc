package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;

import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.PlanoDeAtividades;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public class SupervisorDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nome;
	private String telefone;
	private String cpf;
	private String formacao;
	private List<TermoDeEstagio> termoDeEstagio;
	private List<Estagio> estagio;
	private List<PlanoDeAtividades> planoDeAtividades;
	
	public SupervisorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SupervisorDTO(long id, String nome, String telefone, String cpf, String formacao,
			List<TermoDeEstagio> termoDeEstagio, List<Estagio> estagio, List<PlanoDeAtividades> planoDeAtividades) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.cpf = cpf;
		this.formacao = formacao;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
		this.planoDeAtividades = planoDeAtividades;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
	
}
