package com.atm.banking.dao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.atm.banking.enums.AccountType;
import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class AccountDao extends BaseDao{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	private String firstName;
	
	private String middleName;
	
	@NotNull
	private String lastName;
	
	private String gender;
	
	private long dateOfBirth;
	
	@Column(unique = true)
	private String accountNumber;
	
	private double balance;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "card_id")
	private CardDao card;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private AddressDao address;
	
	private String currency = "INR";
	
	private AccountType accountType;
	

}
