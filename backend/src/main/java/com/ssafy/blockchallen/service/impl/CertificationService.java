package com.ssafy.blockchallen.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.repository.CertificationRepository;
import com.ssafy.blockchallen.service.ICertificationService;

@Service
public class CertificationService implements ICertificationService {

	@Autowired
	private CertificationRepository certificationRepository;
	
	@Override
	public Certification register(Certification certification) {
		return certificationRepository.save(certification);
	}

	@Override
	public Certification report(Account account, Certification certification) {
		Optional<Certification> ctf = certificationRepository.findById(certification.getId());// 받아온 인증 정보의 id로 검색
		ctf.setReporter(account);
		ctf.setIsReported(true);
		return certificationRepository.save(ctf);
	}

}
