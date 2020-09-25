package com.ssafy.blockchallen.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class certificationDTO {
	private long uid;
	private long cid;
	private MultipartFile picture;
	private String regDate;
	//private String regDate;
}
