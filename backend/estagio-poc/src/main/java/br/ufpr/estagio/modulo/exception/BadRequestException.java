package br.ufpr.estagio.modulo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ResponseStatusException {
private static final long serialVersionUID = 1L;
	
	private String message;
	
	public BadRequestException(HttpStatusCode status) {
		super(status);
	}

	public BadRequestException(String message) {
		super(HttpStatus.BAD_REQUEST);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
