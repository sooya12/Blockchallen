package com.ssafy.blockchallen.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.ssafy.blockchallen.dto.certificationListDTO;
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
	public Object getChallenges(@RequestParam String option, @RequestParam int limit) {
		return new ResponseEntity<>(challengeService.infinite(option, limit),HttpStatus.OK);
	}
	
	@RequestMapping(path = "/mychallenges/{id}", method = RequestMethod.GET)
	public Object myChallenges(@PathVariable("id") long id) {
		return new ResponseEntity<>(challengeService.MyChallenges(id), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/challenge/certification", method = RequestMethod.GET)
	public Object getCertifications(@RequestParam long id) {
		System.out.println(id);
		List<certificationListDTO> list = challengeService.getCertifications(id);
		if(list != null)
			return new ResponseEntity<>(list, HttpStatus.OK);
		else
			return new ResponseEntity<>("존재하지 않는 챌린지", HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(path = "/testtest", method = RequestMethod.GET)
	public Object test() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(path = "/challenge/result/{id}", method = RequestMethod.GET)
	public Object challengeResult(@PathVariable("id") long id) {
		
		System.out.println("들어와줘");
		
//		String start = "2020-09-20";
//		String end = "2020-09-23";
//		
//		try {
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
//			
//			Date startDate = format.parse(start);
//			Date endDate = format.parse(end);
//			
//			long calDate = endDate.getTime() - startDate.getTime();
//			
//			long challengeDays = calDate / (24*60*60*1000) - 1; // 날짜로 계산(시작일 포함)
//			
//			System.out.println("몇일 차이나니~? " + challengeDays + "일!");
//			
//		} catch(ParseException e) {
//			e.printStackTrace();
//		}
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
}
