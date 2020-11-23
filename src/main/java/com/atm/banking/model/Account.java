package com.atm.banking.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.atm.banking.dao.AddressDao;
import com.atm.banking.dao.CardDao;
import com.atm.banking.enums.AccountType;
import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class Account {

	private long id;
	
	@NotNull
	private String firstName;
	
	private String middleName;
	
	@NotNull
	private String lastName;
	
	private String gender;
	
	private String dateOfBirth;
	
	@Column(unique = true)
	private String accountNumber;
	
	private double balance;
	
	private Card card;
	
	@NotNull
	private Address address;
	
	private String currency = "INR";
	
	@NotNull
	private AccountType accountType;
}
