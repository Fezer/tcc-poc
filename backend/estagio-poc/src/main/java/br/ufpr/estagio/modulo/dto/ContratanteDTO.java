package br.ufpr.estagio.modulo.dto;

import java.util.ArrayList;

import br.ufpr.estagio.modulo.enums.EnumTipoContratante;

public class ContratanteDTO {
	private long id;
	private EnumTipoContratante tipo;
	private String cnpj;
	private String cpf;
	private String representanteEmpresa;
	private ArrayList<EstagioDTO> Estagio;
	
	public ContratanteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContratanteDTO(long id, EnumTipoContratante tipo, String cnpj, String cpf, String representanteEmpresa,
			ArrayList<EstagioDTO> estagio) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.representanteEmpresa = representanteEmpresa;
		Estagio = estagio;
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

	public ArrayList<EstagioDTO> getEstagio() {
		return Estagio;
	}

	public void setEstagio(ArrayList<EstagioDTO> estagio) {
		Estagio = estagio;
	}
	
}