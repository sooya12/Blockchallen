package com.ssafy.blockchallen.service.impl;

import com.ssafy.blockchallen.service.IWalletService;
import com.ssafy.blockchallen.entity.Wallet;
import com.ssafy.blockchallen.repository.WalletRepository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService implements IWalletService {

	private WalletRepository walletRepository;

	@Autowired
	public WalletService(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}
	
	@Override
	public Wallet findUserId(long userId) {
		return walletRepository.findById(userId).orElse(null);
	}
	
	@Override
	public Wallet findAddress(String address) {
		return walletRepository.findByAddress(address);
	}
	
	@Override
	public Wallet create(Wallet wallet) {
		return walletRepository.save(wallet);
	}
	@Override
	public Wallet syncBalance(String walletAddress, BigDecimal balance, int cash) {
		return null;
	}
	
	@Override
	public Wallet requestEth(String walletAddress) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
