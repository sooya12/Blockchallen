package com.ssafy.blockchallen.dto;

import com.ssafy.blockchallen.entity.Wallet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class findAccountDTO {
	
	private long id;
	private String email;
	private String nickname;
	private String access_token;
	private Wallet wallet;
	
	public static class Builder {
		private long id = 0;
		private String email = "";
		private String nickname = "";
		private String access_token = "";
		private Wallet wallet;
		
		public Builder() {
			
		}
		public Builder id(long id) {
			this.id = id;
			return this;
		}
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		public Builder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}
		public Builder access_token(String access_token) {
			this.access_token = access_token;
			return this;
		}
		public Builder wallet(Wallet wallet) {
			this.wallet = wallet;
			return this;
		}
		public findAccountDTO build() {
			return new findAccountDTO(this);
		}
	}
	private findAccountDTO(Builder builder) {
		id = builder.id;
		email = builder.email;
		nickname = builder.nickname;
		access_token = builder.access_token;
		wallet = builder.wallet;
	}
	
}
