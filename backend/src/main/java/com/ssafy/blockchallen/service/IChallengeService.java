package com.ssafy.blockchallen.service;

import java.util.List;

import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.dto.detailChallengeDTO;
import com.ssafy.blockchallen.entity.Challenge;

public interface IChallengeService {
	
	boolean createChallenge(createChallengeDTO challenge);
	detailChallengeDTO detailChallenge(long id);
	List<Challenge> getChallenges();
}
