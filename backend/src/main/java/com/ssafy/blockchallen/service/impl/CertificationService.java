package com.ssafy.blockchallen.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.entity.Challenge;
import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.repository.CertificationRepository;
import com.ssafy.blockchallen.repository.ChallengeRepository;
import com.ssafy.blockchallen.service.ICertificationService;

@Service
public class CertificationService implements ICertificationService {

	@Autowired
	private CertificationRepository certificationRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ChallengeRepository challengeRepository;

	@Override
	public Certification register(long userId, long challengeId, byte[] picture, String regDate) {
		Certification certification = new Certification();
		Optional<Account> account = accountRepository.findById(userId);
		Optional<Challenge> challenge = challengeRepository.findById(challengeId);
		
		certification.setAccount(account.get());
		certification.setChallenge(challenge.get());
		certification.setPicture(picture);
		certification.setRegDate(regDate);
		
		// 해당 챌린지의 시작 시간과 종료 시간을 받아와서 regDate값이 그 사이 값이면 실패 아니면 성공 처리?
		
		return certificationRepository.save(certification);
	}

	// 신고자도 같이??
	@Override
	public Certification report(long id) {
		Optional<Certification> certification = certificationRepository.findById(id);
		if(certification.isPresent()) {
			certification.get().setIsReported(true);
			certificationRepository.save(certification.get());
			return certification.get();
		}
		else {
			return null;
		}
	}
}
