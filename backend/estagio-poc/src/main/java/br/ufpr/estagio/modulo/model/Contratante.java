package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufpr.estagio.modulo.enums.EnumTipoContratante;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
//@Table(name = "contratante", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
@Table(name = "contratante")
//@Inheritance(strategy = InheritanceType.JOINED)
public class Contratante extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name = "id")
//	private long id;
	
	@Column(name = "tipo")
	private EnumTipoContratante tipo;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "representanteEmpresa")
	private String representanteEmpresa;
	
	@JsonIgnore
	@OneToMany(mappedBy="contratante", cascade=CascadeType.REMOVE)
	private List<Estagio> estagio;
	
	// adicionado para concluir a task de associar contratante ao termo
	@JsonIgnore
	@OneToMany(mappedBy="contratante", cascade=CascadeType.REMOVE)
	private List<TermoDeEstagio> termoDeEstagio;
	
	public Contratante() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contratante(long id, EnumTipoContratante tipo, String cnpj, String cpf, String representanteEmpresa,
			List<Estagio> estagio) {
		super();
		this.tipo = tipo;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.representanteEmpresa = representanteEmpresa;
		this.estagio = estagio;
	}

	// adicionado para concluir a task de associar contratante ao termo
	public Contratante(long id, String nome, String telefone, EnumTipoContratante tipo, String cnpj, String cpf,
			String representanteEmpresa, List<Estagio> estagio, List<TermoDeEstagio> termoDeEstagio) {
		super(id, nome, telefone);
		this.tipo = tipo;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.representanteEmpresa = representanteEmpresa;
		this.estagio = estagio;
		this.termoDeEstagio = termoDeEstagio;
	}

	public long getId() {
		return super.getId();
	}

	public void setId(long id) {
		super.setId(id);
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

	// adicionado para concluir a task de associar contratante ao termo
	public List<TermoDeEstagio> getTermoDeEstagio() {
		return termoDeEstagio;
	}

	// adicionado para concluir a task de associar contratante ao termo
	public void setTermoDeEstagio(List<TermoDeEstagio> termoDeEstagio) {
		this.termoDeEstagio = termoDeEstagio;
	}

}
