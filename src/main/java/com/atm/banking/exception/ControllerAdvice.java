package com.atm.banking.exception;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(ATMException.class)
	public ResponseEntity<ExceptionResponse> handleBusinessException(final ATMException exc, HttpServletRequest request) {
		return ResponseEntity.status(Objects.nonNull(exc.getStatus()) ? exc.getStatus() : HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(exc.getStatus().value(), exc.getStatus().getReasonPhrase(), exc.getMessage(),
						request.getPathInfo()));
	}


}
