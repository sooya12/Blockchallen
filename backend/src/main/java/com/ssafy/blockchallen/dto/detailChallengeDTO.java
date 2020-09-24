package com.ssafy.blockchallen.dto;

import java.util.Set;

import com.ssafy.blockchallen.entity.Account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class detailChallengeDTO {
	private long id;
	private String name;
	private String startDate;
	private String endDate;
	private String expireDate;
	private int fee;
	private boolean isRandom;
	private String certificationCondition;
	private int certificationStartTime;
	private int certificationEndTime;
	private Set<Account> users;
	
	public static class Builder {
		private long id = -1;
		private String name = "";
		private String startDate = "";
		private String endDate = "";
		private String expireDate = "";
		private int fee = 0;
		private boolean isRandom = false;
		private String certificationCondition;
		private int certificationStartTime;
		private int certificationEndTime;
		private Set<Account> users;
		
		public Builder() {
			
		}
		public Builder id(long id) {
			this.id = id;
			return this;
		}
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		public Builder startDate(String startDate) {
			this.startDate = startDate;
			return this;
		}
		public Builder endDate(String endDate) {
			this.endDate = endDate;
			return this;
		}
		public Builder expireDate(String expireDate) {
			this.expireDate = expireDate;
			return this;
		}
		public Builder fee(int fee) {
			this.fee = fee;
			return this;
		}
		public Builder isRandom(boolean isRandom) {
			this.isRandom = isRandom;
			return this;
		}
		public Builder certificationCondition(String certificationCondition) {
			this.certificationCondition = certificationCondition;
			return this;
		}
		public Builder certificationStartTime(int certificationStartTime) {
			this.certificationStartTime = certificationStartTime;
			return this;
		}
		public Builder certificationEndTime(int certificationEntTime) {
			this.certificationEndTime = certificationEntTime;
			return this;
		}
		public Builder users(Set<Account> users) {
			this.users = users;
			return this;
		}
		public detailChallengeDTO build() {
			return new detailChallengeDTO(this);
		}
	}
	
	private detailChallengeDTO(Builder builder) {
		id = builder.id;
		name = builder.name;
		startDate = builder.startDate;
		endDate = builder.endDate;
		expireDate = builder.expireDate;
		fee = builder.fee;
		isRandom = builder.isRandom;
		certificationCondition = builder.certificationCondition;
		certificationStartTime = builder.certificationStartTime;
		certificationEndTime = builder.certificationEndTime;
		users = builder.users;
	}
}
