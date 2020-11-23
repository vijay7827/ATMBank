package com.atm.banking.controller;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.banking.dao.AccountDao;
import com.atm.banking.mapper.AccountMapper;
import com.atm.banking.model.Account;
import com.atm.banking.model.TransactionRequest;
import com.atm.banking.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountsController {
	
	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<Account> addAccount(@Validated @RequestBody Account account) {
		Account result = accountService.addAcount(account);
		if(Objects.isNull(result)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PutMapping
	public ResponseEntity<Account> updateAccount(@Validated @RequestBody Account account) {
		Account result = accountService.updateAccountDetails(account);
		if(Objects.isNull(result)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(result);
	}
	
	@PutMapping
	@RequestMapping("/transaction-request")
	public ResponseEntity<Account> operateAccount(@Validated @RequestBody TransactionRequest request) {
		AccountDao result = accountService.handleTransactionRequest(request);
		if(Objects.isNull(result)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(AccountMapper.mapFromAccountDaoToAccount(result));
	}
	
	
	
	
}
