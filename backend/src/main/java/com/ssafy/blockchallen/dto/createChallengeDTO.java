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

	@Override
	public String toString() {
		return "createChallengeDTO{" +
				"name='" + name + '\'' +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				", expireDate='" + expireDate + '\'' +
				", fee=" + fee +
				", isRandom=" + isRandom +
				", certification='" + certification + '\'' +
				", samplepicture=" + samplepicture +
				", uid=" + uid +
				", certificationStartTime=" + certificationStartTime +
				", certificationEndTime=" + certificationEndTime +
				'}';
	}
}
