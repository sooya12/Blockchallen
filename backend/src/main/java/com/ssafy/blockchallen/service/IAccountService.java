package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.entity.Account;

public interface IAccountService {
	Account findAccount(String email);
	
}
