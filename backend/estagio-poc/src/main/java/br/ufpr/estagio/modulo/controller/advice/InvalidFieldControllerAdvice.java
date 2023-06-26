package br.ufpr.estagio.modulo.controller.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import br.ufpr.estagio.modulo.dto.ErrorResponse;
import br.ufpr.estagio.modulo.exception.InvalidFieldException;

@RestControllerAdvice
public class InvalidFieldControllerAdvice {
	
	@ExceptionHandler(InvalidFieldException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidFieldException(InvalidFieldException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        response.setError(ex.getMessage());
        return response;
    }
	
}
