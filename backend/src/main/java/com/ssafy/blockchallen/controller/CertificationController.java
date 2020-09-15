package com.ssafy.blockchallen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	@RequestMapping(value = "/certification/register", method = RequestMethod.POST)
	public Certification register(@RequestBody Certification certification) {
		return certificationService.register(certification);
	}
	
	@ApiOperation(value = "인증 신고하기")
	@RequestMapping(value = "/certification/{userId}", method = RequestMethod.PUT)
	public Certification declaration(@PathVariable long userId, @RequestBody Certification certification) {
		return certificationService.declaration(userId, certification);
	}
}
