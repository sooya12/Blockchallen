package com.ssafy.blockchallen.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class failDTO {
	private long uid;
	private String nickname;
	
	public static class Builder {
		private long uid;
		private String nickname;
		
		public Builder() {
			
		}
		public Builder uid(long uid) {
			this.uid = uid;
			return this;
		}
		public Builder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}
		public failDTO build() {
			return new failDTO(this);
		}
	}
	private failDTO(Builder builder) {
		uid = builder.uid;
		nickname = builder.nickname;
	}
}
