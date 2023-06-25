package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class CursoSigaData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private CursoSiga curso;

	public CursoSiga getCurso() {
		return curso;
	}

	public void setCurso(CursoSiga curso) {
		this.curso = curso;
	}

}
