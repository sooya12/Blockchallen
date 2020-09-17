package com.ssafy.blockchallen.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Challenge;
import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.repository.ChallengeRepository;
import com.ssafy.blockchallen.service.IChallengeService;

@Service
public class ChallengeService implements IChallengeService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ChallengeRepository challengeRepository;
	
	public boolean createChallenge(createChallengeDTO challenge) {
		Optional<Account> captain = accountRepository.findById(challenge.getUid());
		if(!captain.isPresent()) {
			return false;
		}
		
		Challenge newChallenge = new Challenge.Builder()
				.captain(captain.get())
				.name(challenge.getName())
				.expireDate(challenge.getExpireDate())
				.startDate(challenge.getStartDate())
				.endDate(challenge.getEndDate())
				.fee(challenge.getFee())
				.isRandom(challenge.isRandom())
				.certificationCondition(challenge.getCertification())
				.build();
		newChallenge.addAccount(captain.get());
		
		
		captain.get().addCaptainChallenge(newChallenge);
		challengeRepository.save(newChallenge);
		
		return true;
	}

	
}
