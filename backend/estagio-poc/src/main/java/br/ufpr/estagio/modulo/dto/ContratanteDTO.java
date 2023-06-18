package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;

import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import br.ufpr.estagio.modulo.model.Endereco;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public class ContratanteDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private EnumTipoContratante tipo;
	private String nome;
	private String telefone;
	private String cnpj;
	private String cpf;
	private String representanteEmpresa;
	private Endereco endereco;

	public ContratanteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContratanteDTO(long id, EnumTipoContratante tipo, String nome, String telefone, String cnpj, String cpf,
			String representanteEmpresa) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.telefone = telefone;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.representanteEmpresa = representanteEmpresa;

	}

	// adicionado para concluir a task de associar contratante ao termo
	public ContratanteDTO(long id, EnumTipoContratante tipo, String cnpj, String cpf, String representanteEmpresa) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.representanteEmpresa = representanteEmpresa;

	}

	public ContratanteDTO(long id, EnumTipoContratante tipo, String nome, String telefone, String cnpj, String cpf,
			String representanteEmpresa, Endereco endereco) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.telefone = telefone;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.representanteEmpresa = representanteEmpresa;
		this.endereco = endereco;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
