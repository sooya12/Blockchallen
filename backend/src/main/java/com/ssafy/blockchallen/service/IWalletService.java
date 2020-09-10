package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.entity.Wallet;
import org.springframework.transaction.annotation.Transactional;

public interface IWalletService {
	
	Wallet get(long userId);
	
	@Transactional
	Wallet register(Wallet wallet);

}
