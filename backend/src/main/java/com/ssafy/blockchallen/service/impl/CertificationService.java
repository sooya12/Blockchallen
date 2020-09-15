package com.ssafy.blockchallen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.repository.CertificationRepository;
import com.ssafy.blockchallen.service.ICertificationService;

@Service
public class CertificationService implements ICertificationService {

	private CertificationRepository certificationRepository;
	
	@Autowired
	public CertificationService(CertificationRepository certificationRepository) {
		this.certificationRepository = certificationRepository;
	}
	
	@Override
	public Certification register(Certification certification) {
		return certificationRepository.save(certification);
	}

	@Override
	public Certification declaration(long id, Certification certification) {
		Certification ctf = certificationRepository.findById(id).orElse(null);
		
		ctf.setIsReported(true);
		ctf.setIsReported(certification.getIsReported());
		return certificationRepository.save(ctf);
	}

}
