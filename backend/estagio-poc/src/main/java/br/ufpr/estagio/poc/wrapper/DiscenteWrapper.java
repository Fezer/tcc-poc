package br.ufpr.estagio.poc.wrapper;

import br.ufpr.estagio.poc.model.Discente;
import br.ufpr.estagio.poc.model.DiscenteData;

public class DiscenteWrapper {
    private DiscenteData data;

    public DiscenteData getData() {
        return data;
    }

    public void setData(DiscenteData data) {
        this.data = data;
    }
}
