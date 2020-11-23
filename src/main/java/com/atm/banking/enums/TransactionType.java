package com.atm.banking.enums;

import org.springframework.http.HttpStatus;

import com.atm.banking.exception.ATMException;

public enum TransactionType {

	WITHDRAWAL("withdrawal"),
	DEPOSIT("deposit"),
	ENQUIRY("enquiry");
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private TransactionType(String value) {
		this.value = value;
	}
	
	public static TransactionType getTransactionType(String value) {
		switch(value.toUpperCase()) {
		case "WITHDRAWAL" :
			return TransactionType.WITHDRAWAL;
		case "DEPOSIT" :
			return TransactionType.DEPOSIT;
		case "ENQUIRY" :
			return TransactionType.ENQUIRY;
		default :
			throw new ATMException(ErrorCodes.INVALID_REQUEST.getValue(), HttpStatus.BAD_REQUEST);
		
		}
	}
	
}
