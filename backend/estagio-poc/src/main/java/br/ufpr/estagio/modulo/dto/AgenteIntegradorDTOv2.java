package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;


import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public class AgenteIntegradorDTOv2 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String nome;
	
	private String telefone;
	
	private String cnpj;
	
	private List<Convenio> convenio;
	
	private List<TermoDeEstagio> termoDeEstagio;
	
	private List<Estagio> estagio;
	
	public AgenteIntegradorDTOv2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgenteIntegradorDTOv2(long id, String nome, String telefone, String cnpj, List<Convenio> convenio,
			List<TermoDeEstagio> termoDeEstagio, List<Estagio> estagio) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.cnpj = cnpj;
		this.convenio = convenio;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Convenio> getConvenio() {
		return convenio;
	}

	public void setConvenio(List<Convenio> convenio) {
		this.convenio = convenio;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
