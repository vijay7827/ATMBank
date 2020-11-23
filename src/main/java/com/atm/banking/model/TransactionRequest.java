package com.atm.banking.model;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class TransactionRequest extends Card{

	private String accountNumber;
	
	private String cardNumber;
	
	@NotNull
	private String type;
	
	private double amount;
	
	@NotNull
	private String pin;
}
