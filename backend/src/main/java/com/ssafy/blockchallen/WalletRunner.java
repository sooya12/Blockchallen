package com.ssafy.blockchallen;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Wallet;
import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.repository.WalletRepository;

@Component
public class WalletRunner implements ApplicationRunner{

	@Autowired
	WalletRepository walletRepository;
	AccountRepository accountRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
//		Account account = new Account();
//		account.setAccess_token("test");
//		account.setCertifications();
//		account.setChallengeaccounts(null);
//		account.setEmail("aa");
//		account.setNickname("js");
//		accountRepository.save(account);
		
		
		Wallet wallet = new Wallet();
		wallet.setAddress("test1");
		wallet.setBalance(BigDecimal.valueOf(123));
		//wallet.setAccount(account);
		walletRepository.save(wallet);
	}

}
