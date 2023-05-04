package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class CursoSigaData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private CursoSiga cursoSiga;

	public CursoSiga getCursoSiga() {
		return cursoSiga;
	}

	public void setCursoSiga(CursoSiga cursoSiga) {
		this.cursoSiga = cursoSiga;
	}

}
