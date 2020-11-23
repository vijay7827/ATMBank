package com.atm.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.banking.dao.TransactionsDao;

public interface TransactionsRepository extends JpaRepository<TransactionsDao, Long>{

	List<TransactionsDao> findAllByAccountNumber(String accountNumber);
}
