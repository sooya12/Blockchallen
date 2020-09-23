package com.ssafy.blockchallen.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class myChallengeDTO {
	private long id;
	private int fee;
	private boolean isRunning;
	private double progressRate;
	private String endDate;
	
	public static class Builder {
		private long id;
		private int fee;
		private boolean isRunning;
		private double progressRate;
		private String endDate;
		
		public Builder() {
			
		}
		public Builder id(long id) {
			this.id = id;
			return this;
		}
		public Builder fee(int fee) {
			this.fee = fee;
			return this;
		}
		public Builder isRunning(boolean isRunning) {
			this.isRunning = isRunning;
			return this;
		}
		public Builder progressRate(double progressRate) {
			this.progressRate = progressRate;
			return this;
		}
		public Builder endDate(String endDate) {
			this.endDate = endDate;
			return this;
		}
		public myChallengeDTO build() {
			return new myChallengeDTO(this);
		}
	}
	private myChallengeDTO(Builder builder) {
		id = builder.id;
		fee = builder.fee;
		isRunning = builder.isRunning;
		progressRate = builder.progressRate;
		endDate = builder.endDate;
	} 
}
