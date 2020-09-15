package com.ssafy.blockchallen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.blockchallen.dto.createChallengeDTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen")
public class ChallengeController {

	@RequestMapping(path = "/challenge", method = RequestMethod.POST)	
	public Object createChallenge(createChallengeDTO challenge) {
		
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
