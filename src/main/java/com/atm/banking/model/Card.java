package com.atm.banking.model;

import lombok.Data;

@Data
public class Card {

	private String cardNumber;
	
	private String pin;
	
	private String cvv;
	
	private String dateOfIssue;
	
	private String dateOfexpiry;
}
