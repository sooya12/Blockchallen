package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.entity.Challenge;

public interface IChallengeService {
	
	boolean createChallenge(createChallengeDTO challenge);
	Challenge detailChallenge(long id);
}
