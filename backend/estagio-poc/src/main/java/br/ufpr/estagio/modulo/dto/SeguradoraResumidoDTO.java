package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;

import br.ufpr.estagio.modulo.model.Apolice;

public class SeguradoraResumidoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nome;
	private boolean seguradoraUfpr;
	private List<Apolice> apolice;

	public SeguradoraResumidoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SeguradoraResumidoDTO(long id, String nome, boolean seguradoraUfpr, List<Apolice> apolice) {
		super();
		this.id = id;
		this.nome = nome;
		this.seguradoraUfpr = seguradoraUfpr;
		this.apolice = apolice;
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

	public boolean isSeguradoraUfpr() {
		return seguradoraUfpr;
	}

	public void setSeguradoraUfpr(boolean seguradoraUfpr) {
		this.seguradoraUfpr = seguradoraUfpr;
	}

	public List<Apolice> getApolice() {
		return apolice;
	}

	public void setApolice(List<Apolice> apolice) {
		this.apolice = apolice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
