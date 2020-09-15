package com.ssafy.blockchallen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
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
	private Long uid;
}
