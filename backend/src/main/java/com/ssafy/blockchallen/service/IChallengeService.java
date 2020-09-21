package com.ssafy.blockchallen.service;

import java.util.Collection;
import java.util.List;
//import java.util.List;
import java.util.Set;

import com.ssafy.blockchallen.dto.certificationListDTO;

import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.dto.detailChallengeDTO;
import com.ssafy.blockchallen.entity.Challenge;

public interface IChallengeService {
	
	boolean createChallenge(createChallengeDTO challenge);
	detailChallengeDTO detailChallenge(long id);
	Set<Challenge> MyChallenges(long id);
	Collection<Challenge> infinite(String option, int limit);
	List<certificationListDTO> getCertifications(long id);
}
