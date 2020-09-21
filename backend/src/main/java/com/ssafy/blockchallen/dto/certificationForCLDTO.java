package com.ssafy.blockchallen.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class certificationForCLDTO {
	private long id;
	private String picture;
	private String regDate;
	private boolean isReported;
	
	public static class Builder {
		private long id;
		private String picture;
		private String regDate;
		private boolean isReported;
		
		public Builder() {
			
		}
		public Builder id(long id) {
			this.id = id;
			return this;
		}
		public Builder picture(String picture) {
			this.picture = picture;
			return this;
		}
		public Builder regDate(String regDate) {
			this.regDate = regDate;
			return this;
		}
		public Builder isReported(boolean isReported) {
			this.isReported = isReported;
			return this;
		}
		public certificationForCLDTO build() {
			return new certificationForCLDTO(this);
		}
	}
	
	private certificationForCLDTO(Builder builder) {
		id = builder.id;
		picture = builder.picture;
		regDate = builder.regDate;
		isReported = builder.isReported;
	}
}
