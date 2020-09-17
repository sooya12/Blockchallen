package com.ssafy.blockchallen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.service.ICertificationService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen")
public class CertificationController {
	public static final Logger logger = LoggerFactory.getLogger(CertificationController.class);
	
	@Autowired
	private ICertificationService certificationService;
	

	@ApiOperation(value = "인증 등록하기")
	@RequestMapping(value = "/certification/register", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Certification register(@RequestBody Certification certification) {
		return certificationService.register(certification);
	}
	
	@ApiOperation(value = "인증 신고하기")
	@RequestMapping(value = "/certification/{reporter}", method = RequestMethod.PUT)
	public Certification report(@RequestBody Account account, @RequestBody Certification certification) { // 신고자와 인증 정보 받아옴
		if(certification.getIsReported()==false) // 신고가 된적이 없는 인증이라면
			return certificationService.report(account, certification); // 갱신
		else {
			return certification;			
		}
	}
	
	
}
