package com.atm.banking.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.atm.banking.dao.AccountDao;
import com.atm.banking.dao.CardDao;
import com.atm.banking.dao.TransactionsDao;
import com.atm.banking.enums.ErrorCodes;
import com.atm.banking.enums.TransactionType;
import com.atm.banking.exception.ATMException;
import com.atm.banking.mapper.AccountMapper;
import com.atm.banking.model.Account;
import com.atm.banking.model.TransactionRequest;
import com.atm.banking.repository.AccountRepository;
import com.atm.banking.repository.CardRepository;
import com.atm.banking.repository.TransactionsRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionsRepository transactionRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Value("${withdrawal-limit}")
	private double withdrawalLimit;
	
	@Value("${minimum-balance}")
	private double minimumBalance;
	
	@Value("${account-length}")
	private int accountLength;
	
	@Value("${cardNumber-length}")
	private int cardLength;
	
	@Value("${pin-length}")
	private int pinLength;
	
	@Value("${cvv-length}")
	private int cvvLength;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
	
	/**
	 * adding account details
	 * @param account
	 */
//	@Transactional
	public Account addAcount(Account account) {
		String accountNumber = String.valueOf(this.generateUniqueAccountNumber(accountLength));
		LOGGER.info(accountNumber);
		Optional<AccountDao> accountDb = accountRepository.findByAccountNumber(accountNumber);
		if(accountDb.isPresent()) {
			throw new ATMException(ErrorCodes.ACCOUNT_EXISTS.getValue(), HttpStatus.CONFLICT);
		}
		account.setAccountNumber(accountNumber);
		AccountDao accountDao = AccountMapper.mapFromAccountToAccountDao(account);
		CardDao card = this.generateCardDetails();
		accountDao.setCard(card);
		//accountDao = accountRepository.saveAndFlush(accountDao);
		accountDao = this.saveAccountDetails(accountDao);
		LOGGER.info("account saved account number - {}", accountNumber);
		return AccountMapper.mapFromAccountDaoToAccount(accountDao);		
	}
	
	private CardDao generateCardDetails() {
		CardDao card = new CardDao();
		card.setCardNumber(String.valueOf(this.generateUniqueAccountNumber(13)));
		card.setPin("1234");
		card.setCvv("123");
		card.setDateOfexpiry(1763809263000L);
		card.setDateOfIssue(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
		return card;
	}
	
	/**
	 * generate 13 digit account number
	 * @return
	 */
	private long generateUniqueAccountNumber(int len) {
	    while (true) {
	        long numb = (long)(Math.random() * 100000000 * 1000000); 
	        if (String.valueOf(numb).length() == len)
	            return numb;
	    }
	}
	
	/**
	 * update accountDetails
	 * @param account
	 */
	@Transactional
	public Account updateAccountDetails(Account account) {
		Optional<AccountDao> accountDb = accountRepository.findByAccountNumber(account.getAccountNumber());
		if(accountDb.isPresent()) {
			throw new ATMException(ErrorCodes.ACCOUNT_NOT_EXISTS.getValue(), HttpStatus.BAD_REQUEST);
		}
		AccountDao accountDao = AccountMapper.mapFromAccountToAccountDao(account);
		this.saveAccountDetails(accountDao);
		LOGGER.info("account saved account number - {}", account.getAccountNumber());
		return AccountMapper.mapFromAccountDaoToAccount(accountDao);	
	}
	
	/**
	 * function for saving account details
	 * @param accountDao
	 * @return
	 */
	private AccountDao saveAccountDetails(AccountDao accountDao) {
		synchronized (this) {
			return accountRepository.save(accountDao);	
		}		
	}
	
	/**
	 * get AccountDetails
	 * @param accountDao
	 */
	public AccountDao getAccountDetails(String accountNumber) {
		synchronized (this) {
			Optional<AccountDao> accountDb = accountRepository.findByAccountNumber(accountNumber);
			if(!accountDb.isPresent()) {
				throw new ATMException(ErrorCodes.ACCOUNT_NOT_EXISTS.getValue(), HttpStatus.BAD_REQUEST);
			}
			return accountDb.get();
		}
		
	}
	
	public AccountDao getAccountDetails(TransactionRequest request) {
		if(Objects.nonNull(request.getCardNumber())) {
			synchronized (this) {
				Optional<CardDao> card = cardRepository.findByCardNumber(request.getCardNumber());
				if(!card.isPresent()) {
					throw new ATMException(ErrorCodes.ACCOUNT_NOT_EXISTS.getValue(), HttpStatus.BAD_REQUEST);
				}
				return card.get().getAccount();
			}
		}
		else if(Objects.nonNull(request.getAccountNumber())) {
			synchronized (this) {
				Optional<AccountDao> accountDb = accountRepository.findByAccountNumber(request.getAccountNumber());
				if(!accountDb.isPresent()) {
					throw new ATMException(ErrorCodes.ACCOUNT_NOT_EXISTS.getValue(), HttpStatus.BAD_REQUEST);
				}
				return accountDb.get();
			}
		}
		return null;
	}
	
	/**
	 * withdraw amount
	 * @param account
	 */
	@Transactional
	private AccountDao withdrawAmountFromAccount(TransactionRequest request) {
		AccountDao accountDb = this.getAccountDetails(request);
		if(accountDb.getBalance() < minimumBalance || accountDb.getBalance() - request.getAmount() < minimumBalance) {
			throw new ATMException(ErrorCodes.INSUFFICIENT_BALANCE.getValue(), HttpStatus.BAD_REQUEST);
		}
		double todaysWithdrawal = transactionRepository.findAllByAccountNumber(accountDb.getAccountNumber()).stream()
				.filter(accounts -> 
					(accounts.getCreatedAt() > LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli() &&
					accounts.getCreatedAt() <  LocalDate.now().plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
				)
		.map(accountMap -> accountMap.getAmount())
		.mapToDouble(Double::doubleValue).sum();
		if(todaysWithdrawal > withdrawalLimit) {
			throw new ATMException(ErrorCodes.LIMIT_EXCEDED.getValue(), HttpStatus.BAD_REQUEST);
		}
		accountDb.setBalance(accountDb.getBalance() - request.getAmount());
		accountDb = accountRepository.save(accountDb);
		transactionRepository.save(this.createTransaction(request.getAmount(), TransactionType.getTransactionType(request.getType()), request.getAccountNumber()));
		return accountDb;
	}
	
	/**
	 * create transaction
	 * @param amount
	 * @param type
	 * @param accountNumber
	 * @return
	 */
	private TransactionsDao createTransaction(double amount, TransactionType type, String accountNumber) {
		TransactionsDao transaction = new TransactionsDao();
		transaction.setAccountNumber(accountNumber);
		transaction.setType(type);
		transaction.setAmount(amount);
		return transaction;
		
	}
	
	
	@Transactional
	public AccountDao depositAmountFromAccount(TransactionRequest request) {
		AccountDao accountDb = this.getAccountDetails(request);
		accountDb.setBalance(accountDb.getBalance() + request.getAmount());
		accountDb = accountRepository.save(accountDb);
		transactionRepository.save(this.createTransaction(request.getAmount(), TransactionType.getTransactionType(request.getType()), request.getAccountNumber()));
		return accountDb;
	}
	
	public AccountDao handleTransactionRequest(TransactionRequest request) {
		if(this.isInvalidRequest(request)) {
			throw new ATMException(ErrorCodes.UNAUTHORIZED.getValue(), HttpStatus.BAD_REQUEST);
		}
		
		switch(TransactionType.getTransactionType(request.getType())) {
		case WITHDRAWAL :
			return this.withdrawAmountFromAccount(request);
		case DEPOSIT :
			return this.depositAmountFromAccount(request);
		case ENQUIRY :
			return this.enquireAccount(request);
		}
		return null;
	}
	
	@Transactional
	private AccountDao enquireAccount(TransactionRequest request) {
		if(!Objects.isNull(request.getAccountNumber())) {
			return this.getAccountDetails(request.getAccountNumber());
		}
		else if(!Objects.isNull(request.getCardNumber())) {
			Optional<CardDao> card = cardRepository.findByCardNumber(request.getCardNumber());
			if(card.isPresent()) {
				return card.get().getAccount();
			}
		}
		return null;
	}
	
	private boolean isInvalidRequest(TransactionRequest request) {
		if(!Objects.isNull(request.getAccountNumber())) {
			AccountDao accountDb = this.getAccountDetails(request.getAccountNumber());
			CardDao card= accountDb.getCard();
			return (Objects.isNull(card) || !card.getPin().equals(request.getPin()));

		}
		else if(!Objects.isNull(request.getCardNumber())) {
			Optional<CardDao> card = cardRepository.findByCardNumber(request.getCardNumber());
			return (!card.isPresent() || !request.getPin().equals(card.get().getPin()));
		}
		return true;
	}
	
	
	
	
}
