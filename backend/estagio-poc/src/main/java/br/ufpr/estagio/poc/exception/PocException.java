package br.ufpr.estagio.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PocException extends ResponseStatusException {
    public PocException(String message, HttpStatus httpStatus) {
        super(httpStatus, message);
    }
}
