package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;

import br.ufpr.estagio.modulo.enums.EnumTipoContratante;

public class Contratante extends Pessoa implements Serializable {
	
	private long id;
	private EnumTipoContratante tipo;
	private String cnpj;
	private String cpf;
	private String representanteEmpresa;
	private ArrayList<Estagio> estagio;
	
	public Contratante() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contratante(long id, EnumTipoContratante tipo, String cnpj, String cpf, String representanteEmpresa,
			ArrayList<Estagio> estagio) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.representanteEmpresa = representanteEmpresa;
		this.estagio = estagio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EnumTipoContratante getTipo() {
		return tipo;
	}

	public void setTipo(EnumTipoContratante tipo) {
		this.tipo = tipo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRepresentanteEmpresa() {
		return representanteEmpresa;
	}

	public void setRepresentanteEmpresa(String representanteEmpresa) {
		this.representanteEmpresa = representanteEmpresa;
	}

	public ArrayList<Estagio> getEstagio() {
		return estagio;
	}

	public void setEstagio(ArrayList<Estagio> estagio) {
		this.estagio = estagio;
	}

}
