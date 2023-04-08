package br.ufpr.estagio.modulo.model;

import java.io.Serializable;

public class DiscenteData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Discente discente;

    public Discente getDiscente() {
        return discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }
}
