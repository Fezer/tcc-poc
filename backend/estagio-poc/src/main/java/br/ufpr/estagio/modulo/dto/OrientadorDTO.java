package br.ufpr.estagio.modulo.dto;

import java.util.List;

import br.ufpr.estagio.modulo.model.Curso;

public class OrientadorDTO {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String cpf;
	private String lotacao;
	private String departamento;
	private List<Curso> curso;
	
	public OrientadorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrientadorDTO(long id, String cpf, String lotacao, String departamento, List<Curso> curso) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.lotacao = lotacao;
		this.departamento = departamento;
		this.curso = curso;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public List<Curso> getCurso() {
		return curso;
	}

	public void setCurso(List<Curso> curso) {
		this.curso = curso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
