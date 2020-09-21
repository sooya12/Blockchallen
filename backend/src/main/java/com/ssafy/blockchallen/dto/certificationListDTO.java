package com.ssafy.blockchallen.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class certificationListDTO {
	private long id;
	private String nickname;
	private String email;
	private List<certificationForCLDTO> certification;
	private long progress;
	
	public static class Builder {
		private long id;
		private String nickname;
		private String email;
		private List<certificationForCLDTO> certification;
		private long progress;
		
		public Builder() {
			
		}
		public Builder id(long id) {
			this.id = id;
			return this;
		}
		public Builder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		public Builder certification(List<certificationForCLDTO> certification) {
			this.certification = certification;
			return this;
		}
		public Builder progress(long progress) {
			this.progress = progress;
			return this;
		}
		public certificationListDTO build() {
			return new certificationListDTO(this);
		}
	}
	
	private certificationListDTO(Builder builder) {
		id = builder.id;
		nickname = builder.nickname;
		email = builder.email;
		certification = builder.certification;
		progress = builder.progress;
	}
}
