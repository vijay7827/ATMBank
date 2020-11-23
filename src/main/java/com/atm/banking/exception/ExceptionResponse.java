package com.atm.banking.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

	private int status;
	private String error;
	private String message;
	private String path;

	
}
