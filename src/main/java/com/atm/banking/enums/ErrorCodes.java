package com.atm.banking.enums;

public enum ErrorCodes {

	ACCOUNT_EXISTS("account already exisits"),
	ACCOUNT_NOT_EXISTS("account not found"),
	INSUFFICIENT_BALANCE("insuffucient balance"),
	LIMIT_EXCEDED("withdrawal limit reached"),
	INVALID_REQUEST("Invalid request"),
	UNAUTHORIZED("request is not authorized");
	
	private String value;

	public String getValue() {
		return value;
	}

	private ErrorCodes(String value) {
		this.value = value;
	}

	
	
}
