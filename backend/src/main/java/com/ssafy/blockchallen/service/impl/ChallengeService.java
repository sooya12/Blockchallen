package com.ssafy.blockchallen.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.blockchallen.dto.certificationForCLDTO;
import com.ssafy.blockchallen.dto.certificationListDTO;
import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.dto.detailChallengeDTO;
import com.ssafy.blockchallen.dto.failDTO;
import com.ssafy.blockchallen.dto.myChallengeDTO;
import com.ssafy.blockchallen.dto.resultChallengeDTO;
import com.ssafy.blockchallen.dto.successDTO;
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
				.certificationStartTime(challenge.getCertificationStartTime())
				.certificationEndTime(challenge.getCertificationEndTime())
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
					.certificationStartTime(challenge.get().getCertificationStartTime())
					.certificationEndTime(challenge.get().getCertificationEndTime())
					.users(challenge.get().getAccounts())
					.build();
			
			return retChallenge;
		}
		return null;
	}

	public List<myChallengeDTO> MyChallenges(long id) throws ParseException {
		Optional<Account> account = accountRepository.findById(id);
		List<myChallengeDTO> challenges = new ArrayList<myChallengeDTO>();
		if(account.isPresent()) {
			for (Challenge challenge : account.get().getChallenges()) {
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
				
				// 파싱해서 날짜 얻기
				Date startDate = format.parse(challenge.getStartDate());
				Date endDate = format.parse(challenge.getEndDate());
				
				long calDate = endDate.getTime() - startDate.getTime();
				long challengeDays = calDate / (24*60*60*1000) + 1; // 날짜로 계산(시작일 포함)

				Date date = new Date(endDate.getTime() + (24*60*60*1000));
				boolean running = date.compareTo(new Date()) > 0 ? true:false;
				double rate = (double)account.get().getCertifications().stream().filter(el->el.getChallenge().getId()==challenge.getId() && !el.getIsReported()).count()/challengeDays;
				
				challenges.add(new myChallengeDTO.Builder()
						.id(challenge.getId())
						.name(challenge.getName())
						.fee(challenge.getFee())
						.isRunning(running)
						.progressRate((double)Math.round(rate*10)/10)
						.endDate(challenge.getEndDate())
						.build());
			}
			
//			if(challenges.size()==0)
//				return challenges;
			
			return challenges.stream().sorted(new Comparator<myChallengeDTO>() {
				public int compare(myChallengeDTO o1, myChallengeDTO o2) {
					if(o1.isRunning() && !o2.isRunning()) {
						return -1;
					} else if (!o1.isRunning() && o2.isRunning()) {
						return 1;
					} else {
						return o1.getEndDate().compareTo(o2.getEndDate());
					}
				}
			}).collect(Collectors.toList());
		}
		return null;
	}

	public Collection<Challenge> getChallenges(String option) {
		if(option.equals("fast"))
			return challengeRepository.findAllOrderByStartDateAsc();
		else if(option.equals("slow"))
			return challengeRepository.findAllOrderByStartDateDesc();
		else if(option.equals("expensive"))
			return challengeRepository.findAllOrderByFeeDesc();
		else if(option.equals("cheap"))
			return challengeRepository.findAllOrderByFeeAsc();
		else
			return challengeRepository.findAll();
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

	public resultChallengeDTO getResult(long id) throws ParseException {
		Optional<Challenge> challenge = challengeRepository.findById(id);
		if(challenge.isPresent()) {
			String start = challenge.get().getStartDate();
			String end = challenge.get().getEndDate();
			boolean random = challenge.get().getIsRandom();
			int totalFee = challenge.get().getFee() * (int)challenge.get().getAccounts().stream().count();
			int fee = challenge.get().getFee();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			
			// 파싱해서 날짜 얻기
			Date startDate = format.parse(start);
			Date endDate = format.parse(end);
			
			long calDate = endDate.getTime() - startDate.getTime();
			long challengeDays = calDate / (24*60*60*1000) + 1; // 날짜로 계산(시작일 포함)
			
			List<successDTO> successlist = new ArrayList<successDTO>();
			List<failDTO> faillist = new ArrayList<failDTO>();
			int successcnt = (int)challenge.get().getAccounts().stream().filter(el->el.getCertifications().stream().filter(e->e.getChallenge().getId()==id && !e.getIsReported()).count()==challengeDays).count();
			int failcnt = (int)challenge.get().getAccounts().stream().filter(el->el.getCertifications().stream().filter(e->e.getChallenge().getId()==id && !e.getIsReported()).count()!=challengeDays).count();
			
			if(!random) { // 균등
				for (Account account : challenge.get().getAccounts()) {
					if(account.getCertifications().stream().filter(el->el.getChallenge().getId()==id && !el.getIsReported()).count()==challengeDays) { // 성공
						successlist.add(new successDTO.Builder()
								.uid(account.getId())
								.nickname(account.getNickname())
								.prize(totalFee/successcnt)
								.build());
						
					} else { // 실패
						faillist.add(new failDTO.Builder()
								.uid(account.getId())
								.nickname(account.getNickname())
								.build());
					}
				}
				
			} else { // 랜덤
				boolean[] use = new boolean[successcnt];
				Random rand = new Random();
				for (Account account : challenge.get().getAccounts()) {
					if(account.getCertifications().stream().filter(el->el.getChallenge().getId()==id && !el.getIsReported()).count()==challengeDays) { // 성공
						int divnum = 1;
						if(successcnt%2==0) { // 성공 짝수
							divnum = (successcnt-1)*(successcnt/2);
						} else {
							divnum = (successcnt-1)*(successcnt/2) + successcnt/2;
						}
						
						int bonus = -1;
						while(!use[bonus = rand.nextInt(successcnt)])
							
							successlist.add(new successDTO.Builder()
									.uid(account.getId())
									.nickname(account.getNickname())
									.prize(fee+(fee*failcnt)*(bonus/divnum))
									.build());
					} else { // 실패
						faillist.add(new failDTO.Builder()
								.uid(account.getId())
								.nickname(account.getNickname())
								.build());
					}
				}
			}
			
			return new resultChallengeDTO.Builder()
					.successlist(successlist.stream().sorted(new Comparator<successDTO>() {
						public int compare(successDTO o1, successDTO o2) {
							return o1.getPrize() > o2.getPrize() ? -1 : 1;
						}
					}).collect(Collectors.toList()))
					.faillist(faillist)
					.build();
			
			
		}
		return null;
	}
}
