package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Wallet;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

public interface IWalletService {
	Wallet findByAccount(Account account);
	
//	Wallet findByWallet(Wallet wallet);
	
	@Transactional
	Wallet create(Wallet wallet);

	@Transactional
	Wallet syncBalance(String walletAddress, BigDecimal balance, int cash);

	@Transactional
	Wallet requestEth(String walletAddress);

}
