package br.ufpr.estagio.modulo.dto;

import java.io.Serializable;
import java.util.List;

import br.ufpr.estagio.modulo.model.Apolice;
import br.ufpr.estagio.modulo.model.Estagio;
import br.ufpr.estagio.modulo.model.TermoDeEstagio;

public class SeguradoraDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nome;
	private boolean seguradoraUfpr;
	private boolean isAtiva;
	private String error;
	private List<Apolice> apolice;
	private List<TermoDeEstagio> termoDeEstagio;
	private List<Estagio> estagio;

	public SeguradoraDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SeguradoraDTO(long id, String nome, boolean seguradoraUfpr, boolean isAtiva, List<Apolice> apolice,
			List<TermoDeEstagio> termoDeEstagio, List<Estagio> estagio) {
		super();
		this.id = id;
		this.nome = nome;
		this.seguradoraUfpr = seguradoraUfpr;
		this.isAtiva = isAtiva;
		this.apolice = apolice;
		this.termoDeEstagio = termoDeEstagio;
		this.estagio = estagio;
	}

	public SeguradoraDTO(String error) {
		super();
		this.error = error;
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

	public boolean isAtiva() {
		return isAtiva;
	}

	public void setAtiva(boolean isAtiva) {
		this.isAtiva = isAtiva;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<Apolice> getApolice() {
		return apolice;
	}

	public void setApolice(List<Apolice> apolice) {
		this.apolice = apolice;
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
