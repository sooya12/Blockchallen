package com.ssafy.blockchallen;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.entity.Wallet;
import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.repository.CertificationRepository;
import com.ssafy.blockchallen.repository.WalletRepository;

@Component
public class WalletRunner implements ApplicationRunner{
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	CertificationRepository certificationRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Account account = new Account();
		account.setAccess_token("test2");
		account.setCertifications(null);
		account.setChallenges(null);
		account.setChallenges(null);
		account.setEmail("aaa");
		account.setNickname("jsl");
		accountRepository.save(account);
		
		Wallet wallet = new Wallet();
		wallet.setAddress("sadjfioasjio");
		wallet.setBalance(BigDecimal.valueOf(1231));
		wallet.setAccount(account);
		walletRepository.save(wallet);
		
		Certification ctf = new Certification();
		ctf.setChallenge(null);
		ctf.setAccount(account);
		ctf.setPicture("abcd");
		ctf.setRegDate("3210");
		//ctf.setIsReported(true);
		certificationRepository.save(ctf);

	}

}
