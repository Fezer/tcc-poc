package br.ufpr.estagio.modulo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PocException extends ResponseStatusException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PocException(HttpStatus status) {
		super(status);
	}
	
	public PocException(HttpStatus status, String reason) {
    	 super(status, reason);
    }
    
    public PocException(HttpStatus status, String reason, Throwable cause) {
    	super(status, reason, cause);
    }
}

