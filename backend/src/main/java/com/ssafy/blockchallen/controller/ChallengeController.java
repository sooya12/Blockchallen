package com.ssafy.blockchallen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.blockchallen.dto.createChallengeDTO;
import com.ssafy.blockchallen.dto.detailChallengeDTO;
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
			return new ResponseEntity<>("챌린지 생성 성공", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("챌린지 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(path = "/challenge", method = RequestMethod.GET)
	public Object datailChallenge(@RequestParam long id) {
		
		detailChallengeDTO challenge = challengeService.detailChallenge(id);
		if(challenge != null)
			return new ResponseEntity<>(challenge, HttpStatus.OK);
		else
			return new ResponseEntity<>("존재하지 않는 챌린지", HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(path = "/challenges", method = RequestMethod.GET)
	public Object getChallenges() {
		return new ResponseEntity<>(challengeService.getChallenges(),HttpStatus.OK);
	}
	
	@RequestMapping(path = "/mychallenges/{id}", method = RequestMethod.GET)
	public Object myChallenges(@PathVariable("id") long id) {
		return challengeService.MyChallenges(id);
	}
	
	@RequestMapping(path = "/challenges")
	    public Object selectInfiniteLoading(@RequestParam("limit") int limit) {

		return new ResponseEntity<>(challengeService.infinite(limit), HttpStatus.OK);
	}
}
