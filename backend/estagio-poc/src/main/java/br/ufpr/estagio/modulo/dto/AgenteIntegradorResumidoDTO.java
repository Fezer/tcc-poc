package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufpr.estagio.modulo.model.Convenio;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class AgenteIntegradorResumidoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String nome;
	
	private String telefone;
	
	private String cnpj;
	
	private List<Convenio> convenio;
		
	public AgenteIntegradorResumidoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgenteIntegradorResumidoDTO(long id, String nome, String telefone, String cnpj, List<Convenio> convenio) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.cnpj = cnpj;
		this.convenio = convenio;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
