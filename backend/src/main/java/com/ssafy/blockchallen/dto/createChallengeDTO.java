package com.ssafy.blockchallen.dto;

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
	private long uid;
	private int certificationStartTime;
	private int certificationEndTime;
}
