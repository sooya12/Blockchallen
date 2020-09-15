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
	
}
