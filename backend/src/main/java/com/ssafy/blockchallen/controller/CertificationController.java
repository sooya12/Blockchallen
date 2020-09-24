package com.ssafy.blockchallen.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.blockchallen.dto.certificationDTO;
import com.ssafy.blockchallen.dto.checkPictureDTO;
import com.ssafy.blockchallen.dto.reportDTO;
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
	@RequestMapping(value = "/certification/register", method = RequestMethod.POST)
	public Object register(@ModelAttribute certificationDTO certification) throws IOException {
		System.out.println(certification.getCid());
		System.out.println(certification.getUid());
		System.out.println(certification.getPicture());
		Certification newCertification = certificationService.register( certification.getUid(),certification.getCid(), certification.getPicture().getBytes(),certification.getRegDate());
		
		if(newCertification != null) {
			return new ResponseEntity<>(newCertification, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("인증 등록 실패", HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "인증 신고하기")
	@RequestMapping(value = "/certification/{id}", method = RequestMethod.POST)
	public Object setReport(@PathVariable("id") long id) {// 인증사진 id 받아옴
		Certification report = certificationService.report(id);
		if(report != null) {
			return new ResponseEntity<>(report,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("인증 신고 실패",HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "인증 가능여부 체크")
	@RequestMapping(value = "/certification/date", method = RequestMethod.GET)
	public Boolean checkPicture(@ModelAttribute checkPictureDTO checkpicture) {// 인증사진 id 받아옴
		
		return certificationService.check(checkpicture.getUid(), checkpicture.getCid());
	}
	
}
