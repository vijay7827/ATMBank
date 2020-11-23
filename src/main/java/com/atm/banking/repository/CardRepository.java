package com.atm.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.banking.dao.CardDao;

public interface CardRepository extends JpaRepository<CardDao, Long>{

	Optional<CardDao> findByCardNumber(String cardNumber);
}
