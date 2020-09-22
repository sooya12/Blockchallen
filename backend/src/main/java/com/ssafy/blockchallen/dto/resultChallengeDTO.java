package com.ssafy.blockchallen.dto;

import java.util.List;

public class resultChallengeDTO {
	private List<successDTO> successlist;
	private List<failDTO> faillist;
	
	public static class Builder {
		private List<successDTO> successlist;
		private List<failDTO> faillist;
		
		public Builder() {
			
		}
		public Builder successlist(List<successDTO> successlist) {
			this.successlist = successlist;
			return this;
		}
		public Builder faillist(List<failDTO> faillist) {
			this.faillist = faillist;
			return this;
		}
		public resultChallengeDTO build() {
			return new resultChallengeDTO(this);
		}
		
	}
	private resultChallengeDTO(Builder builder) {
		successlist = builder.successlist;
		faillist = builder.faillist;
	}
}
