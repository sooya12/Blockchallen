package com.ssafy.blockchallen;

import com.ssafy.blockchallen.repository.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CertificationRunner implements ApplicationRunner {

    @Autowired
    CertificationRepository certificationrepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
