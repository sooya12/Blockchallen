package com.ssafy.blockchallen.service.impl;

import java.io.IOException;
import java.math.BigInteger;
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
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import com.ssafy.blockchallen.dto.certificationForCLDTO;
import com.ssafy.blockchallen.dto.certificationListDTO;
import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.dto.detailChallengeDTO;
import com.ssafy.blockchallen.dto.failDTO;
import com.ssafy.blockchallen.dto.idDTO;
import com.ssafy.blockchallen.dto.myChallengeDTO;
import com.ssafy.blockchallen.dto.resultChallengeDTO;
import com.ssafy.blockchallen.dto.successDTO;
import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.entity.Challenge;
import com.ssafy.blockchallen.entity.Reward;
import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.repository.ChallengeRepository;
import com.ssafy.blockchallen.repository.RewardRepository;
import com.ssafy.blockchallen.repository.WalletRepository;
import com.ssafy.blockchallen.service.IChallengeService;

@Service
public class ChallengeService implements IChallengeService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ChallengeRepository challengeRepository;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	RewardRepository rewardRepository;
	
	public boolean createChallenge(createChallengeDTO challenge) throws IOException, InterruptedException, ExecutionException{
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
				.isRandom(challenge.isIsRandom())
				.certificationCondition(challenge.getCertification())
				.samplepicture(challenge.getSamplepicture().getBytes())
				.certificationStartTime(challenge.getCertificationStartTime())
				.certificationEndTime(challenge.getCertificationEndTime())
				.address(challenge.getAddress())
				.build();
		newChallenge.addAccount(account.get());
		challengeRepository.save(newChallenge);
		
		Admin admin = Admin.build(new HttpService("https://j3a102.p.ssafy.io/geth"));

        String fromAddress = "0x03fb923A157c20565E36D7d518418E1b9b0c2C86";
        String fromPassword = "ssafy";
        String toAddress = challenge.getAddress();

        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, fromPassword).sendAsync().get();
        
        BigInteger value = new BigInteger("100000000000000000");
        BigInteger gasPrice = new BigInteger("100");
        BigInteger gasLimit = new BigInteger("4700000");
        
        EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        Transaction transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value);

        if(personalUnlockAccount.accountUnlocked()) {
//            admin.personalSendTransaction(transaction, fromPassword).sendAsync().get();
            admin.ethSendTransaction(transaction).sendAsync().get();
            System.out.println("1EH 송금");
        }
		
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
					.samplepicture(challenge.get().getSamplepicture())
					.certificationStartTime(challenge.get().getCertificationStartTime())
					.certificationEndTime(challenge.get().getCertificationEndTime())
					.users(challenge.get().getAccounts())
					.address(challenge.get().getAddress())
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
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				
				// 파싱해서 날짜 얻기
				Date startDate = format.parse(challenge.getStartDate());
				Date endDate = format.parse(challenge.getEndDate());
				
				long calDate = endDate.getTime() - startDate.getTime();
				long challengeDays = calDate / (24*60*60*1000) + 1; // 날짜로 계산(시작일 포함)

				Date date = new Date(endDate.getTime() + (24*60*60*1000));
				boolean running = date.compareTo(new Date()) > 0 ? true:false;
				date = new Date(startDate.getTime());
				boolean start = date.compareTo(new Date()) <= 0 ? true:false;
				double rate = (double)account.get().getCertifications().stream().filter(el->el.getChallenge().getId()==challenge.getId() && !el.getIsReported()).count()/challengeDays;
				
				challenges.add(new myChallengeDTO.Builder()
						.id(challenge.getId())
						.name(challenge.getName())
						.fee(challenge.getFee())
						.isRunning(running)
						.progressRate((double)Math.round(rate*1000)/10)
						.isStart(start)
						.endDate(challenge.getEndDate())
						.build());
			}
			
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
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			// 파싱해서 날짜 얻기
			Date startDate = format.parse(start);
			Date endDate = format.parse(end);
			
			long calDate = endDate.getTime() - startDate.getTime();
			long challengeDays = calDate / (24*60*60*1000) + 1; // 날짜로 계산(시작일 포함)
			
			List<successDTO> successlist = new ArrayList<successDTO>();
			List<failDTO> faillist = new ArrayList<failDTO>();
			
			for (Account account : challenge.get().getAccounts()) {
				Optional<Reward> reward = rewardRepository.findByAccountAndChallenge(account, challenge.get());
				if(reward.isPresent()) {
					successlist.add(new successDTO.Builder()
							.uid(account.getId())
							.nickname(account.getNickname())
							.prize(reward.get().getPrize())
							.build());
				} else {
					faillist.add(new failDTO.Builder()
							.uid(account.getId())
							.nickname(account.getNickname())
							.build());
				}
			}
			
//			int successcnt = (int)challenge.get().getAccounts().stream().filter(el->el.getCertifications().stream().filter(e->e.getChallenge().getId()==id && !e.getIsReported()).count()==challengeDays).count();
//			int failcnt = (int)challenge.get().getAccounts().stream().filter(el->el.getCertifications().stream().filter(e->e.getChallenge().getId()==id && !e.getIsReported()).count()!=challengeDays).count();
//			
//			if(!random) { // 균등
//				for (Account account : challenge.get().getAccounts()) {
//					if(account.getCertifications().stream().filter(el->el.getChallenge().getId()==id && !el.getIsReported()).count()==challengeDays) { // 성공
//						successlist.add(new successDTO.Builder()
//								.uid(account.getId())
//								.nickname(account.getNickname())
//								.prize(totalFee/successcnt)
//								.build());
//						
//					} else { // 실패
//						faillist.add(new failDTO.Builder()
//								.uid(account.getId())
//								.nickname(account.getNickname())
//								.build());
//					}
//				}
//				
//			} else { // 랜덤
//				boolean[] use = new boolean[successcnt];
//				Random rand = new Random();
//				for (Account account : challenge.get().getAccounts()) {
//					if(account.getCertifications().stream().filter(el->el.getChallenge().getId()==id && !el.getIsReported()).count()==challengeDays) { // 성공
//						int divnum = 1;
//						if(successcnt%2==0) { // 성공 짝수
//							divnum = (successcnt-1)*(successcnt/2);
//						} else {
//							divnum = (successcnt-1)*(successcnt/2) + successcnt/2;
//						}
//						
//						int bonus = -1;
//						while(!use[bonus = rand.nextInt(successcnt)])
//							
//							successlist.add(new successDTO.Builder()
//									.uid(account.getId())
//									.nickname(account.getNickname())
//									.prize(fee+(fee*failcnt)*(bonus/divnum))
//									.build());
//					} else { // 실패
//						faillist.add(new failDTO.Builder()
//								.uid(account.getId())
//								.nickname(account.getNickname())
//								.build());
//					}
//				}
//			}
			
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

	public Object challengeParticipate(idDTO ids) {
		Optional<Account> account = accountRepository.findById(ids.getUid());
		Optional<Challenge> challenge = challengeRepository.findById(ids.getCid());
		
		if(account.isPresent() && challenge.isPresent()) {
			if(!challenge.get().getAccounts().stream().anyMatch(el->el.getId().equals(ids.getUid()))) {
				challenge.get().addAccount(account.get());
				challengeRepository.save(challenge.get());
				return true;
			} else
				return false;
		} else
			return null;
		
	}

	public Object checkParticipate(long uid, long cid) {
		Optional<Challenge> challenge = challengeRepository.findById(cid);
		if(challenge.isPresent()) {
			return challenge.get().getAccounts().stream().anyMatch(el->el.getId().equals(uid));
		} else
			return null;
	}

	@Transactional
	@Scheduled(cron = "0 0 0 * * *") // 초(0-59) 분(0-59) 시(0-23) 일(1-31) 월(1-12) 요일(0-7)
	@Override
	public void deleteUnderachieving() throws InterruptedException, ExecutionException, ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(new Date());
		
		Admin admin = Admin.build(new HttpService("https://j3a102.p.ssafy.io/geth"));
		
		List<Challenge> challenges = challengeRepository.findAllByStartDate(today).stream().filter(el->el.getAccounts().size()<3).collect(Collectors.toList());
		for (Challenge challenge : challenges) {

			//String fromAddress = "0x03fb923A157c20565E36D7d518418E1b9b0c2C86"; // 코인베이스 테스트용
	        String fromAddress = challenge.getAddress(); // 챌린지 지갑의 주소
	        String fromPassword = "ssafy"; // 챌린지 지갑의 패스워드
	        
	        Set<Account> accounts = challenge.getAccounts(); // 챌린지 참여 계정
	        Iterator<Account> iter = accounts.iterator();
	        
	        while(iter.hasNext()) {
	        	Account account = iter.next();
	        	System.out.println(challenge.getId() + " " + challenge.getName() + " " + account.getNickname());
	        		        	
	        	//String toAddress = "0x02C777293721d140EDecca8131D1b5ADD821b066";
	        	String toAddress = walletRepository.findByAccount(account).get().getAddress(); // 챌린지 참여 유저의 지갑 주소
	        	System.out.println("주소 : " + toAddress);
	        	
	        	PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, fromPassword).sendAsync().get();
	        	
	        	BigInteger value = new BigInteger(challenge.getFee().toString()); // 챌린지에 참여 비용
	        	BigInteger eth = new BigInteger("1000000000000000000"); // 기준 단위 1이더
	        	BigInteger gasPrice = new BigInteger("100");
	        	BigInteger gasLimit = new BigInteger("4700000");
	        	
	        	EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
	        	
	        	BigInteger nonce = ethGetTransactionCount.getTransactionCount();
	        	Transaction transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value.multiply(eth)); // 환불 처리
	        	
	        	if(personalUnlockAccount.accountUnlocked()) {
	        		admin.personalSendTransaction(transaction, fromPassword).sendAsync().get();
	        		System.out.println("1EH 송금");
	        	}
	        }
			challengeRepository.deleteById(challenge.getId()); // db에서 챌린지 삭제
			
		}
		
		// 어제 날짜
		Date dDate = new Date();
		dDate = new Date(dDate.getTime() + (1000 * 60 * 60 * 24 * -1));
		SimpleDateFormat dSdf = new SimpleDateFormat("yyyy-MM-dd");
		String yesterday = dSdf.format(dDate);
		System.out.println(yesterday);
				
		List<Challenge> endChallenges = (List<Challenge>) challengeRepository.findAllByEndDate(yesterday); // 종료된 모든 챌린지
		for (Challenge challenge : endChallenges) {
			Set<Account> accounts = challenge.getAccounts(); // 종료된 챌린지에 참여했던 계정
			int total = accounts.size(); // 챌린지 총 잠여자 수
			int complete = 0; // 챌린지 성공한 사람의 수
			List<Account> winners = new ArrayList<Account>(); // 성공한 사람들의 지갑 주소 저장
			System.out.println("챌린지 이름 : " + challenge.getName());
			
			for (Account account : accounts) {
				SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
				
				// 파싱해서 날짜 얻기
				Date startDate = formats.parse(challenge.getStartDate());
				Date endDate = formats.parse(challenge.getEndDate());
				
				long calDate = endDate.getTime() - startDate.getTime();
				long challengeDays = calDate / (24*60*60*1000) + 1; // 날짜로 계산(시작일 포함)
				
				double rate = (double)account.getCertifications().stream().filter(el->el.getChallenge().getId()==challenge.getId() && !el.getIsReported()).count()/challengeDays;
				
				double progressRate = ((double)Math.round(rate*1000)/10);
				System.out.println("챌린지 참여 인원 : " + account.getNickname() + ", 달성률 : " + progressRate);
				
				if(progressRate >= 85.0) { // 챌린지에 성공했다면
					winners.add(account);
					complete++;
				}
			}
			System.out.println("챌린지 참여자 수 : " +total +", 성공자 수 : " + complete);
			
			if(!challenge.getIsRandom()) { // 균등

				for (Account winner : winners) {
					//String fromAddress = "0x03fb923A157c20565E36D7d518418E1b9b0c2C86"; // 코인베이스
					String fromAddress = challenge.getAddress(); // 챌린지 지갑의 주소
					String fromPassword = "ssafy"; // 챌린지 지갑의 패스워드
					
					String toAddress = winner.getWallet().getAddress(); // 챌린지 참여 유저의 지갑 주소
					System.out.println("주소 : " + toAddress);
					
					PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, fromPassword).sendAsync().get();
					
					BigInteger value = new BigInteger(challenge.getFee().toString()); // 챌린지에 참여 비용
					BigInteger eth = new BigInteger("1000000000000000000"); // 기준 단위 1이더
					BigInteger all = new BigInteger(String.valueOf(total)); // 전체 인원
					BigInteger totalReward = new BigInteger("1").multiply(eth).multiply(value).multiply(all); // 총 상금
					BigInteger success = new BigInteger(String.valueOf(complete)); // 성공인원
					BigInteger reward = totalReward.divide(success); // 인별 상금
					
					System.out.println("총 상금 " + totalReward);
					System.out.println("인별 상금 " + reward);
					
					BigInteger gasPrice = new BigInteger("100");
					BigInteger gasLimit = new BigInteger("4700000");
					
					EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
					
					BigInteger nonce = ethGetTransactionCount.getTransactionCount();
					Transaction transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, reward); // 환불 처리
					
					if(personalUnlockAccount.accountUnlocked()) {
						admin.personalSendTransaction(transaction, fromPassword).sendAsync().get();
						System.out.println("1EH 송금");
					}
					
					Optional<Reward> getReward = rewardRepository.findByAccountAndChallenge(winner, challenge);
					if(!getReward.isPresent()) {
						Reward newReward = new Reward.Builder()
								.account(winner)
								.challenge(challenge)
								.prize(total*challenge.getFee())
								.build();
						rewardRepository.save(newReward);
					}
					
				}
				
			} 
			else { // 랜덤
				boolean[] use = new boolean[complete];
				Random rand = new Random();
				
				for (Account winner : winners) {
					//String fromAddress = "0x03fb923A157c20565E36D7d518418E1b9b0c2C86"; // 코인베이스
					String fromAddress = challenge.getAddress(); // 챌린지 지갑의 주소
					String fromPassword = "ssafy"; // 챌린지 지갑의 패스워드
					
					String toAddress = winner.getWallet().getAddress(); // 챌린지 참여 유저의 지갑 주소
					System.out.println("주소 : " + toAddress);
					
					PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, fromPassword).sendAsync().get();
					
					BigInteger value = new BigInteger(challenge.getFee().toString()); // 챌린지에 참여 비용
					BigInteger eth = new BigInteger("1000000000000000000"); // 기준 단위 1이더
					BigInteger all = new BigInteger(String.valueOf(total)); // 전체 인원
					BigInteger totalReward = new BigInteger("1").multiply(eth).multiply(value).multiply(all); // 총 상금
					
					int divnum = 1;
					if(complete%2==0) { // 성공 짝수
						divnum = (complete-1)*(complete/2);
					} else {
						divnum = (complete-1)*(complete/2) + complete/2;
					}
					int bonus = rand.nextInt(complete);
					while(true) {
						if(!use[bonus]) {
							use[bonus] = true;
							break;
						}
						bonus = rand.nextInt(complete);
					}
					int prize = challenge.getFee() + (challenge.getFee()*(challenge.getAccounts().size()-complete)*(bonus/divnum));
					
					BigInteger reward = new BigInteger("1").multiply(eth).multiply(new BigInteger(String.valueOf(prize))); // 인별 상금
					
					System.out.println("총 상금 " + totalReward);
					System.out.println("인별 상금 " + reward);
					
					BigInteger gasPrice = new BigInteger("100");
					BigInteger gasLimit = new BigInteger("4700000");
					
					EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
					
					BigInteger nonce = ethGetTransactionCount.getTransactionCount();
					Transaction transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, reward); // 환불 처리
					
					if(personalUnlockAccount.accountUnlocked()) {
						admin.personalSendTransaction(transaction, fromPassword).sendAsync().get();
						System.out.println("1EH 송금");
					}
					
					Optional<Reward> getReward = rewardRepository.findByAccountAndChallenge(winner, challenge);
					if(!getReward.isPresent()) {
						Reward newReward = new Reward.Builder()
								.account(winner)
								.challenge(challenge)
								.prize(prize)
								.build();
						rewardRepository.save(newReward);
					} else {
						getReward.get().setPrize(prize);
						rewardRepository.save(getReward.get());
					}
					
				}
			}
		}
	}
}
