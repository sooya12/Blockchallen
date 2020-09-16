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
	
	public boolean duplicateCheck(String nickname) {
		Optional<Account> account = accountRepository.findByNickname(nickname);
		if(!account.isPresent()) // 사용 가능 닉네임
			return true;
		else // 사용 불가능 닉네임 (이미 존재)
			return false;
	}

	public Account setNickname(Long id, String nickname) {
		Optional<Account> setAccount = accountRepository.findById(id);
		if(setAccount.isPresent()) {
			setAccount.get().setNickname(nickname);
			accountRepository.save(setAccount.get());
			return setAccount.get();
		} else {
			return null;
		}
	}

	public boolean signout(Account account) {
		Optional<Account> delAccount = accountRepository.findById(account.getId());
		if(delAccount.isPresent()) {
			accountRepository.deleteById(account.getId());
			return true;
		} else {
			return false;
		}
	}
	
}
