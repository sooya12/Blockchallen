package com.ssafy.blockchallen.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "회원 계정 ID")
	private Long id;
	
	@ApiModelProperty(required = true, value = "이메일")
	private String email;
	
	@ApiModelProperty(required = true, value = "별명")
	private String nickname;
	
	@ApiModelProperty(required = true, value = "토큰")
	private String access_token;
	
	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	@JsonBackReference
	@ApiModelProperty(value = "지갑 ID")
	private Wallet wallet;
	
	@OneToMany(mappedBy = "captain", cascade = CascadeType.ALL)
	@JsonBackReference
	@ApiModelProperty(value = "방장인 챌린지 set")
	private Set<Challenge> captainChallenges;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	@JsonBackReference
	@ApiModelProperty(value = "인증 set")
	private Set<Certification> certifications;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	@ApiModelProperty(value = "참여한 챌린지 set")
	private Set<Challenge> challenges;
	
	protected Set<Challenge> getCaptainChallengesInternal() {
		if(this.captainChallenges == null)
			this.captainChallenges = new HashSet<Challenge>();
		return captainChallenges;
	}
	
	public void addCaptainChallenge(Challenge challenge) {
		this.getCaptainChallengesInternal().add(challenge);
		challenge.setCaptain(this);
	}
	
	protected Set<Certification> getCertificationsInternal() {
		if(this.certifications == null)
			this.certifications = new HashSet<Certification>();
		return certifications;
	}
	
	public void addCertification(Certification certification) {
		this.getCertificationsInternal().add(certification);
		certification.setAccount(this);
	}
	
	protected Set<Challenge> getChallengesInternal() {
		if(this.challenges == null)
			this.challenges = new HashSet<Challenge>();
		return challenges;
	}
	
	public void addChallengeAccount(Challenge challenge) {
		this.getChallengesInternal().add(challenge);
		challenge.addAccount(this);
	}
	
	
	public static class Builder {
		private String email = "";
		private String nickname = "";
		private String access_token = "";
		private Wallet wallet = null;
		private Set<Challenge> captainChallenges;
		private Set<Certification> certifications;
		private Set<Challenge> challenges;
		
		public Builder() {
			
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
		public Builder captainChallenges(Set<Challenge> captainChallenges) {
			this.captainChallenges = captainChallenges;
			return this;
		}
		public Builder certifications(Set<Certification> certifications) {
			this.certifications = certifications;
			return this;
		}
		public Builder challenges(Set<Challenge> challenges) {
			this.challenges = challenges;
			return this;
		}
		public Account build() {
			return new Account(this);
		}
	}
	private Account(Builder builder) {
		email = builder.email;
		nickname = builder.nickname;
		access_token = builder.access_token;
		wallet = builder.wallet;
		captainChallenges = builder.captainChallenges;
		certifications = builder.certifications;
		challenges = builder.challenges;
	}
}
