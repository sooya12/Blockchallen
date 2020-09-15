package com.ssafy.blockchallen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.repository.CertificationRepository;

@Component
public class CertificationRunner implements ApplicationRunner{
	@Autowired
	CertificationRepository certificationrepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Certification ctf = new Certification();
		ctf.setChallenge(null);
		ctf.setAccount(null);
		ctf.setPicture("test");
		ctf.setRegDate("123");
		ctf.setIsReported(false);
		certificationrepository.save(ctf);
	}
}
