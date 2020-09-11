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
	public Wallet get(long userId) {
		Wallet newWallet = new Wallet();
		newWallet = walletRepository.findById(userId).orElse(null);
		return newWallet;
	}

	@Override
	public Wallet register(Wallet wallet) {
		Wallet newWallet = new Wallet();
		newWallet.setId(wallet.getId());
		newWallet.setAddress(wallet.getAddress());
		newWallet.setOwnerId(wallet.getOwnerId());
		return newWallet;
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
	@Override
	public Wallet get(String address) {
		Wallet newWallet = new Wallet();
		newWallet = walletRepository.findByAddress(address);
		return newWallet;
	}
}
