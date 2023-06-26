package br.ufpr.estagio.modulo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DocentesData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private List<String> docentes = new ArrayList<>();

    public List<String> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<String> docentes) {
        this.docentes = docentes;
    }
}
