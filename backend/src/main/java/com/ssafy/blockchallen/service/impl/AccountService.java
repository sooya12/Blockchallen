package com.ssafy.blockchallen.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.service.IAccountService;

@Service
public class AccountService implements IAccountService {

	@Autowired
	AccountRepository accountRepository;
	
	public Account findAccount(String email) {
		Optional<Account> account = accountRepository.findByEmail(email);
		if(account.isPresent())
			return account.get();  
		else
			return null;
	}

	public Account findAccount(long id) {
		Optional<Account> account = accountRepository.findById(id);
		if(account.isPresent())
			return account.get();
		else
			return null;
	}

	public Account createAccount(Account account) {
		accountRepository.save(account);
		Optional<Account> newAccount = accountRepository.findByEmail(account.getEmail());
		if(newAccount.isPresent())
			return newAccount.get();
		else
			return null;
	}
	
	
}
