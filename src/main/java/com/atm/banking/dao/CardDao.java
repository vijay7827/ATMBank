package com.atm.banking.dao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "card")
public class CardDao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String cardNumber;
	
	private String pin;
	
	private String cvv;
	
	private long dateOfIssue;
	
	private long dateOfexpiry;
	
	@OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
	private AccountDao account;
}
