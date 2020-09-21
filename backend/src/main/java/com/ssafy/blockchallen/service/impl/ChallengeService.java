package com.ssafy.blockchallen.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.blockchallen.dto.certificationForCLDTO;
import com.ssafy.blockchallen.dto.certificationListDTO;
import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.dto.detailChallengeDTO;
import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;
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
	

	public List<certificationListDTO> getCertifications(long id) {
		Optional<Challenge> challenge = challengeRepository.findById(id);
		if(challenge.isPresent()) {
			List<certificationListDTO> list = new ArrayList<certificationListDTO>();
			for (Account account : challenge.get().getAccounts()) {
				Iterator<Certification> iter = account.getCertifications().stream().filter(el->el.getChallenge().getId()==id).collect(Collectors.toSet()).iterator();
				List<certificationForCLDTO> certification = new ArrayList<certificationForCLDTO>();
				while(iter.hasNext()) {
					Certification temp = iter.next();
					certification.add(new certificationForCLDTO.Builder()
							.id(temp.getId())
							.picture(temp.getPicture())
							.regDate(temp.getRegDate())
							.isReported(temp.getIsReported())
							.build());
				}
				certification = certification.stream().sorted(new Comparator<certificationForCLDTO>() {
					public int compare(certificationForCLDTO o1, certificationForCLDTO o2) {
						return o2.getRegDate().compareTo(o1.getRegDate());
					}
				}).collect(Collectors.toList());
				
				list.add(new certificationListDTO.Builder()
						.id(account.getId())
						.nickname(account.getNickname())
						.email(account.getEmail())
						.certification(certification)
						.progress(account.getCertifications().stream().filter(el->el.getChallenge().getId()==id && !el.getIsReported()).count())
						.build());
			}
			return list.stream().sorted(new Comparator<certificationListDTO>() {
				public int compare(certificationListDTO o1, certificationListDTO o2) {
					if(o1.getCertification().size()==0)
						return 1;
					else if(o2.getCertification().size()==0)
						return -1;
					else {
						return o2.getCertification().get(0).getRegDate().compareTo(o1.getCertification().get(0).getRegDate());
					}
				}
			}).collect(Collectors.toList());
		}
		return null;
	}
}
