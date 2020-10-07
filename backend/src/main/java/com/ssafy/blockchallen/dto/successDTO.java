package com.ssafy.blockchallen.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class successDTO {
	private long uid;
	private String nickname;
	private double prize;
	
	public static class Builder {
		private long uid;
		private String nickname;
		private double prize;
		
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
		public Builder prize(double prize) {
			this.prize = prize;
			return this;
		}
		public successDTO build() {
			return new successDTO(this);
		}
	}
	private successDTO(Builder builder) {
		uid = builder.uid;
		nickname = builder.nickname;
		prize = builder.prize;
	}
}
