package com.atm.banking.enums;

public enum AccountType {

	SAVING("saving"),
	CURRENT("current");
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private AccountType(String value) {
		this.value = value;
	}
	
	
}
