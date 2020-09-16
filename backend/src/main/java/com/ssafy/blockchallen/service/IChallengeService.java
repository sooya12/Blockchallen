package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.dto.createChallengeDTO;

public interface IChallengeService {
	
	boolean createChallenge(createChallengeDTO challenge);
}
