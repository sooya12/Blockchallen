package com.ssafy.blockchallen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.service.IChallengeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen")
public class ChallengeController {

	@Autowired
	private IChallengeService challengeService;
	
	@RequestMapping(path = "/challenge", method = RequestMethod.POST)	
	public Object createChallenge(@RequestBody createChallengeDTO challenge) {
		
		if(challengeService.createChallenge(challenge)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
