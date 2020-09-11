package com.ssafy.blockchallen.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	private String neckname;
	
	@ApiModelProperty(required = true, value = "토큰")
	private String access_token;
	
	@OneToOne(mappedBy = "account")
	@ApiModelProperty(value = "지갑 ID")
	private Wallet wallet;
	
	@OneToMany(mappedBy = "account")
	private Set<Certification> certifications;
	
	@OneToMany(mappedBy = "account")
	private Set<ChallengeAccount> challengeaccounts;
	
	protected Set<Certification> getCertificationsInternal() {
		if(this.certifications == null)
			this.certifications = new HashSet<Certification>();
		return certifications;
	}
	
	public void addCertification(Certification certification) {
		this.getCertificationsInternal().add(certification);
		certification.setAccount(this);
	}
	
	protected Set<ChallengeAccount> getChallengeAccountInternal() {
		if(this.challengeaccounts == null)
			this.challengeaccounts = new HashSet<ChallengeAccount>();
		return challengeaccounts;
	}
	
	public void addChallengeAccount(ChallengeAccount challengeaccount) {
		this.getChallengeAccountInternal().add(challengeaccount);
		challengeaccount.setAccount(this);
	}
}
