package com.ssafy.blockchallen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.entity.Certification.Builder;
import com.ssafy.blockchallen.repository.CertificationRepository;
import com.ssafy.blockchallen.service.ICertificationService;

public class CertificationService implements ICertificationService {

	private CertificationRepository certificationRepository;
	
	@Autowired
	public CertificationService(CertificationRepository certificationRepository) {
		this.certificationRepository = certificationRepository;
	}
	
	
	@Override
	public Certification register(Certification certification) {
		Certification ctf = new Certification();
		ctf.setChallenge(null);
		ctf.setAccount(null);
		ctf.setPicture(null);
		ctf.setRegDate(null);
		ctf.setIsReported(null);
		return ctf;
	}

}
