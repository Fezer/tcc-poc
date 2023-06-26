package br.ufpr.estagio.modulo.dto;

public class ErrorResponse {
	private String error;

    public ErrorResponse() {
    	super();
    }

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
