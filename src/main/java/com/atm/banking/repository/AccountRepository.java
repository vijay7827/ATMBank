package com.atm.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.banking.dao.AccountDao;

public interface AccountRepository extends JpaRepository<AccountDao, Long>{

	Optional<AccountDao> findByAccountNumber(String accountNumber);
}
