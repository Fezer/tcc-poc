package br.ufpr.estagio.modulo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ResponseStatusException {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public NotFoundException(HttpStatusCode status) {
		super(status);
	}

	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
