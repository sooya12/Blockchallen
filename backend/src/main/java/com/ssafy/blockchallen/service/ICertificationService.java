package com.ssafy.blockchallen.service;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;

public interface ICertificationService {
	@Transactional
	Certification register(Certification certification);
	
	@Transactional
	Certification report(Account account ,Certification certification);
}
