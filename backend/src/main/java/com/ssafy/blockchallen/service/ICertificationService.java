package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.entity.Certification;

public interface ICertificationService {
	Certification register(Certification certification);
	
	Certification declaration(Long id, Certification certification);
}
