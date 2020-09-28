package com.ssafy.blockchallen.service;

import com.ssafy.blockchallen.entity.Certification;

public interface ICertificationService {
	Boolean register(long userId, long challengeId, byte[] picture, String regDate);
	
	Certification report(long pid, long uid);
	
	Boolean check(long uid, long cid);
}
