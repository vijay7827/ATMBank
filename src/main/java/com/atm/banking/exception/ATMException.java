package com.atm.banking.exception;

import org.springframework.http.HttpStatus;

public class ATMException extends RuntimeException {
	
	private static final long serialVersionUID = 2457189159617736175L;
	
	private final HttpStatus status;
    private String message;
    private String code;
    
    
    
	public ATMException(HttpStatus status, String message, String code) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
	
	public ATMException(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}
    
	
    


}
