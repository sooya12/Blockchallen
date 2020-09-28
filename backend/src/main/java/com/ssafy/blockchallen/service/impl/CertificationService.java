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
		certification.setIsReported(false); // 감자가
		account.get().addCertification(certification); // 추가한
		challenge.get().addCertification(certification); // 부분이에요
		
		return certificationRepository.save(certification);
	}

	@Override
	public Certification report(long pid, long uid) {
		Optional<Certification> certification = certificationRepository.findById(pid);
		Optional<Account> account = accountRepository.findById(uid);
		if(certification.isPresent()) {
			certification.get().setIsReported(true);
			certification.get().setReporter(account.get());
			certificationRepository.save(certification.get());
			return certification.get();
		}
		else {
			return null;
		}
	}

	@Override
	public Boolean check(long uid, long cid) {
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

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
	
		StringBuilder sb = new StringBuilder();
		String date;
		if(month/10 < 1) {
			if(day/10 < 1) {
				 date = sb.append(year).append("-").append(0).append(month).append("-").append(0).append(day).toString();
			}
			else {
				 date = sb.append(year).append("-").append(0).append(month).append("-").append(day).toString();
			}
		}else {
			if(day/10 < 1) {
				 date = sb.append(year).append("-").append(month).append("-").append(0).append(day).toString();
			}
			else {
				 date = sb.append(year).append("-").append(month).append("-").append(day).toString();
			}
		}
		
		if(certification.size()==0) { // 감자가 수정한 부분입니다
			return true;
		} else if(date.compareTo(certification.get(0).getRegDate()) != 0) {
			return true; // 인증할 수 있다.
		} else {
			return false; // 인증할 수 없다.
		}
	}
}
