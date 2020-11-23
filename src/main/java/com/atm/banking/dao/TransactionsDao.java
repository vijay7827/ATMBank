package com.atm.banking.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.atm.banking.enums.TransactionType;

import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
public class TransactionsDao extends BaseDao{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private double amount;
	
	private TransactionType type;
	
	private String accountNumber;
	
	private boolean status;
	
}
