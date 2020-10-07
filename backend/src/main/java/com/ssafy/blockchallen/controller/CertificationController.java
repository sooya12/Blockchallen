package com.ssafy.blockchallen.controller;

import com.ssafy.blockchallen.dto.certificationDTO;
import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.service.ICertificationService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blockchallen")
public class CertificationController {
	public static final Logger logger = LoggerFactory.getLogger(CertificationController.class);
	
	@Autowired
	private ICertificationService certificationService;
	
	@ApiOperation(value = "인증 등록하기")
	@RequestMapping(value = "/certification/register", method = RequestMethod.POST)
	public Boolean register(@ModelAttribute certificationDTO certification) throws IOException {
		return certificationService.register( certification.getUid(),certification.getCid(), certification.getPicture().getBytes(),certification.getRegDate());
	}

	@ApiOperation(value = "인증 위변조 검사")
	@RequestMapping(value = "/certification/block/{pid}", method = RequestMethod.GET)
	public Object checkBlock(@PathVariable("pid") long pid) {
		return new ResponseEntity<>(certificationService.checkBlock(pid), HttpStatus.OK); // 일치하면 true. 불일치하면 false
	}

	@ApiOperation(value = "인증 신고하기")
	@RequestMapping(value = "/certification/report", method = RequestMethod.GET)
	public Object setReport(@RequestParam("pid") long pid, @RequestParam("uid") long uid) {// 인증사진 id 받아옴
		
		Certification report = certificationService.report(pid, uid);
		if(report != null) {
			return new ResponseEntity<>(report,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("인증 신고 실패",HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "인증 가능여부 체크")
	@RequestMapping(value = "/certification/date", method = RequestMethod.GET)
	public Boolean checkPicture(@RequestParam("uid") long uid, @RequestParam("cid") long cid) {// 인증사진 id 받아옴
		
		return certificationService.check(uid, cid);
	}
	
}
