package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.entity.Wallet;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

public interface IWalletService {
	
	Wallet get(long userId);
	
	Wallet get(String address);
	
	@Transactional
	Wallet register(Wallet wallet);

	@Transactional
	Wallet syncBalance(String walletAddress, BigDecimal balance, int cash);

	@Transactional
	Wallet requestEth(String walletAddress);

}
