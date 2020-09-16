package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.entity.Account;

public interface IAccountService {
	Account findAccount(String email);
	Account findAccount(long id);
	Account createAccount(Account account);
	boolean duplicateCheck(String nickname);
	Account setNickname(Long id, String nickname);
	boolean signout(Account account);
}
