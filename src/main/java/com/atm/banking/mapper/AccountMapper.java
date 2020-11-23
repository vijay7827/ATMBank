package com.atm.banking.mapper;

import java.util.Objects;


import com.atm.banking.dao.AccountDao;
import com.atm.banking.dao.AddressDao;
import com.atm.banking.dao.CardDao;
import com.atm.banking.model.Account;
import com.atm.banking.model.Address;
import com.atm.banking.model.Card;
import com.atm.banking.utils.DateUtils;

public class AccountMapper {
	
	private static final String DATE_FORMAT = "MM-dd-yyyy";

	public static AccountDao mapFromAccountToAccountDao(Account account) {
		if(Objects.isNull(account)) {
			return null;
		}
		AccountDao accountDao = new AccountDao();
		accountDao.setId(account.getId());
		accountDao.setAccountNumber(account.getAccountNumber());
		accountDao.setFirstName(account.getFirstName());
		accountDao.setLastName(account.getLastName());
		accountDao.setMiddleName(account.getMiddleName());
		accountDao.setGender(account.getGender());
		accountDao.setCurrency(account.getCurrency());
		accountDao.setAccountType(account.getAccountType());
		if(Objects.nonNull(account.getDateOfBirth()))
		accountDao.setDateOfBirth(DateUtils.getMillisFromTimestamp(account.getDateOfBirth(), DATE_FORMAT));
		accountDao.setBalance(account.getBalance());
		accountDao.setAddress(mapFromAddressToAddressDao(account.getAddress()));
		return accountDao;
		
	}
	
	public static CardDao mapFromCardToCardDao(Card card) {
		if(Objects.isNull(card)) {
			return null;
		}
		CardDao cardDao = new CardDao();
		cardDao.setCardNumber(card.getCardNumber());
		cardDao.setCvv(card.getCvv());
		cardDao.setPin(card.getPin());
		cardDao.setDateOfexpiry(DateUtils.getMillisFromTimestamp(card.getDateOfexpiry(), DATE_FORMAT));
		cardDao.setDateOfIssue(DateUtils.getMillisFromTimestamp(card.getDateOfIssue(), DATE_FORMAT));
		return cardDao;
		
	}
	
	public static AddressDao mapFromAddressToAddressDao(Address address) {
		if(Objects.isNull(address)) {
			return null;
		}
		AddressDao addressDao = new AddressDao();
		addressDao.setAddress(address.getAddress());
		addressDao.setCity(address.getCity());
		addressDao.setCountry(address.getCountry());
		addressDao.setPinCode(address.getPinCode());
		return addressDao;
	}
	
	public static Account mapFromAccountDaoToAccount(AccountDao accountDao) {
		if(Objects.isNull(accountDao)) {
			return null;
		}
		Account account = new Account();
		account.setId(accountDao.getId());
		account.setAccountNumber(accountDao.getAccountNumber());
		account.setFirstName(accountDao.getFirstName());
		account.setLastName(accountDao.getLastName());
		account.setMiddleName(accountDao.getMiddleName());
		account.setGender(accountDao.getGender());
		account.setCurrency(accountDao.getCurrency());
		account.setAccountType(accountDao.getAccountType());
		if(!Objects.isNull(accountDao.getDateOfBirth())) {
			account.setDateOfBirth(DateUtils.getTimestampfromMillis(accountDao.getDateOfBirth(), DATE_FORMAT));
		}
		account.setBalance(accountDao.getBalance());
		account.setCard(mapFromCardDaoToCard(accountDao.getCard()));
		account.setAddress(mapFromAddressDaoToAddress(accountDao.getAddress()));
		return account;
	}
	
	public static Card mapFromCardDaoToCard(CardDao cardDao) {
		if(Objects.isNull(cardDao)) {
			return null;
		}
		Card card = new Card();
		card.setCardNumber(cardDao.getCardNumber());
		card.setCvv(cardDao.getCvv());
		card.setPin(cardDao.getPin());
		card.setDateOfexpiry(DateUtils.getTimestampfromMillis(cardDao.getDateOfexpiry(), DATE_FORMAT));
		card.setDateOfIssue(DateUtils.getTimestampfromMillis(cardDao.getDateOfIssue(), DATE_FORMAT));
		return card;
	}
	
	public static Address mapFromAddressDaoToAddress(AddressDao addressDao) {
		if(Objects.isNull(addressDao)) {
			return null;
		}
		Address address = new Address();
		address.setAddress(addressDao.getAddress());
		address.setCity(addressDao.getCity());
		address.setCountry(addressDao.getCountry());
		address.setPinCode(addressDao.getPinCode());
		return address;
	}
}
