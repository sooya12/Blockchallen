package com.ssafy.blockchallen.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.ssafy.blockchallen.dto.certificationListDTO;

import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.dto.detailChallengeDTO;
import com.ssafy.blockchallen.dto.idDTO;
import com.ssafy.blockchallen.dto.myChallengeDTO;
import com.ssafy.blockchallen.dto.resultChallengeDTO;
import com.ssafy.blockchallen.entity.Challenge;

public interface IChallengeService {
	
	boolean createChallenge(createChallengeDTO challenge) throws IOException, InterruptedException, ExecutionException;
	detailChallengeDTO detailChallenge(long id);
	List<myChallengeDTO> MyChallenges(long id) throws ParseException;
	Collection<Challenge> getChallenges(String option);
	List<certificationListDTO> getCertifications(long id);
	resultChallengeDTO getResult(long id) throws ParseException;
	Object challengeParticipate(idDTO ids);
	Object checkParticipate(long uid, long cid);
	void deleteUnderachieving() throws InterruptedException, ExecutionException;
}
