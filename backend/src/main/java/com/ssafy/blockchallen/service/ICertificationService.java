package com.ssafy.blockchallen.service;

import java.util.List;

import com.ssafy.blockchallen.entity.Certification;

public interface ICertificationService {
	Certification register(long userId, long challengeId, byte[] picture, String regDate);
	
	Certification report(long pid, long uid);
	
	Boolean check(long uid, long cid);
}
