package com.ssafy.blockchallen.service.impl;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.entity.Challenge;
import com.ssafy.blockchallen.repository.AccountRepository;
import com.ssafy.blockchallen.repository.CertificationRepository;
import com.ssafy.blockchallen.repository.ChallengeRepository;
import com.ssafy.blockchallen.service.ICertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CertificationService implements ICertificationService {

	@Autowired
	private CertificationRepository certificationRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ChallengeRepository challengeRepository;

	@Override
	public Boolean register(long userId, long challengeId, byte[] picture, String regDate) {
		
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
	
		StringBuilder sb = new StringBuilder();
		String date; // 오늘 날짜

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
		
		if(date.compareTo(regDate) == 0) { // 캘린더의 오늘 날짜와 사진의 오늘날짜 비교해서 같으면
			Certification certification = new Certification();
			Optional<Account> account = accountRepository.findById(userId);
			Optional<Challenge> challenge = challengeRepository.findById(challengeId);
			
			String encryptPicture;
			
			try {
				encryptPicture = sha256(picture);
				
				Admin admin = Admin.build(new HttpService("https://j3a102.p.ssafy.io/geth"));
				
				String fromAddress = challenge.get().getAddress();
				String fromPassword = "ssafy";
				String toAddress = "0x03fb923A157c20565E36D7d518418E1b9b0c2C86";
				
				PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, fromPassword).sendAsync().get();
				
				BigInteger value = new BigInteger("0");
				BigInteger gasPrice = new BigInteger("100");
				BigInteger gasLimit = new BigInteger("4700000");
				
				EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
				BigInteger nonce = ethGetTransactionCount.getTransactionCount();
				
				Transaction transaction = Transaction.createFunctionCallTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, encryptPicture);
				
				if(personalUnlockAccount.accountUnlocked()) {
					EthSendTransaction est = admin.personalSendTransaction(transaction, "ssafy").sendAsync().get();
					String transactionHash = est.getTransactionHash();
					certification.setTransactionHash(transactionHash); // 트랜잭션해쉬 저장
					long id = est.getId();

					EthTransaction et = admin.ethGetTransactionByHash(transactionHash).sendAsync().get();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			certification.setAccount(account.get());
			certification.setChallenge(challenge.get());
			certification.setPicture(picture);
			certification.setRegDate(regDate);
			certification.setIsReported(false); // 감자가
			account.get().addCertification(certification); // 추가한
			challenge.get().addCertification(certification); // 부분이에요
			certificationRepository.save(certification);

			return true;
			
		}else { // 저장 안됨
			return false;
		}
	}

	@Override
	public Boolean checkBlock(long pid) {
		Boolean result = false;

		Optional<Certification> certification = certificationRepository.findById(pid);

		Admin admin = Admin.build(new HttpService("https://j3a102.p.ssafy.io/geth"));

		try {
			EthTransaction ethTransaction = admin.ethGetTransactionByHash(certification.get().getTransactionHash()).sendAsync().get();

			if(ethTransaction.getTransaction().isPresent()) {
				String blockData = ethTransaction.getResult().getInput();
				String dbData = "0x" + sha256(certification.get().getPicture());

				if(blockData.equals(dbData)) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
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

	/* SHA256 암호화 함수 */
	public static String sha256(byte[] value) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(value);

		StringBuilder sb = new StringBuilder();

		for(byte md : messageDigest.digest()) {
			sb.append(String.format("%02x", md));
		}

		return sb.toString();
	}
}
