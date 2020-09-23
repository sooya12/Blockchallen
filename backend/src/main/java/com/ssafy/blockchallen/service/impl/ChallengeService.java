package com.ssafy.blockchallen.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.dto.detailChallengeDTO;
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
		Optional<Account> account = accountRepository.findById(challenge.getUid());
		if(!account.isPresent()) {
			return false;
		}
		
		Challenge newChallenge = new Challenge.Builder()
				.name(challenge.getName())
				.expireDate(challenge.getExpireDate())
				.startDate(challenge.getStartDate())
				.endDate(challenge.getEndDate())
				.fee(challenge.getFee())
				.isRandom(challenge.isRandom())
				.certificationCondition(challenge.getCertification())
				.build();
		newChallenge.addAccount(account.get());
		challengeRepository.save(newChallenge);
		
		return true;
	}

	public detailChallengeDTO detailChallenge(long id) {
		Optional<Challenge> challenge = challengeRepository.findById(id);
		if(challenge.isPresent()) {
			detailChallengeDTO retChallenge = new detailChallengeDTO.Builder()
					.id(challenge.get().getId())
					.name(challenge.get().getName())
					.startDate(challenge.get().getStartDate())
					.endDate(challenge.get().getEndDate())
					.expireDate(challenge.get().getExpireDate())
					.fee(challenge.get().getFee())
					.isRandom(challenge.get().getIsRandom())
					.certificationCondition(challenge.get().getCertificationCondition())
					.users(challenge.get().getAccounts())
					.build();
			
			return retChallenge;
		}
		return null;
	}

	public Set<Challenge> MyChallenges(long id) {
		Optional<Account> account = accountRepository.findById(id);
		if(account.isPresent()) {
			return account.get().getChallenges();
		}
		return null;
	}

	public Collection<Challenge> infinite(String option, int limit) {
		if(option.equals("fast"))
			return challengeRepository.infinite("order by start_date asc", limit);
		else if(option.equals("slow"))
			return challengeRepository.infinite("order by start_date desc", limit);
		else if(option.equals("expensive"))
			return challengeRepository.infinite("order by fee desc", limit);
		else if(option.equals("cheap"))
			return challengeRepository.infinite("order by fee asc", limit);
		else
			return challengeRepository.infinite("", limit);
	}
	
}
