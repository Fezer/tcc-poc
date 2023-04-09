package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "contratante", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Contratante extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "tipo")
	private EnumTipoContratante tipo;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "representanteEmpresa")
	private String representanteEmpresa;
	
	@OneToMany(mappedBy="contratante", cascade=CascadeType.REMOVE)
	private List<Estagio> estagio;
	
	public Contratante() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contratante(long id, EnumTipoContratante tipo, String cnpj, String cpf, String representanteEmpresa,
			List<Estagio> estagio) {
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

	public List<Estagio> getEstagio() {
		return estagio;
	}

	public void setEstagio(List<Estagio> estagio) {
		this.estagio = estagio;
	}

}
