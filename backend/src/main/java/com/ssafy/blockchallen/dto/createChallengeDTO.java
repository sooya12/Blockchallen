package com.ssafy.blockchallen.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createChallengeDTO {
	private String name;
	private String startDate;
	private String endDate;
	private String expireDate;
	private int fee;
	private boolean isRandom;
	private String certification;
	private MultipartFile samplepicture;
	private long uid;
	private int certificationStartTime;
	private int certificationEndTime;
}
