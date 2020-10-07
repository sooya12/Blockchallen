package com.ssafy.blockchallen;

import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.repository.CertificationRepository;
import com.ssafy.blockchallen.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class WalletRunner implements ApplicationRunner {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CertificationRepository certificationRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

}
