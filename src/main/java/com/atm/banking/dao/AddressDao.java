package com.atm.banking.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class AddressDao extends BaseDao{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String address;
	
	private String city;
	
	private String country;
	
	private String pinCode;
}
