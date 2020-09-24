package com.ssafy.blockchallen.service.impl;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
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
		
		return certificationRepository.save(certification);
	}

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

	@Override
	public Boolean check(long uid, long cid) {
		boolean flag = false;
		
		Optional<Account> account = accountRepository.findById(uid);
		Optional<Challenge> challenge = challengeRepository.findById(cid);
			
		List<Certification> certification = certificationRepository.findByAccountAndChallenge(account.get(), challenge.get());
		certification.sort(new Comparator<Certification>() {
			@Override
			public int compare(Certification o1, Certification o2) {
				if(o1.getRegDate().compareTo(o2.getRegDate()) > 0) {
					return -1;
				}
				else
					return 1;
			}
		});
		
		Calendar cal = Calendar.getInstance();
		System.out.println(cal);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		System.out.println("현재 시각은 " + year + "년도 " + month + "월 " + day + "일 " + hour + "시 " + min + "분 " + sec + "초입니다.");
		
		//certification.get(0).getRegDate()
		
		return flag;
	}
}
