package com.ssafy.blockchallen.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssafy.blockchallen.entity.Account.Builder;

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
public class Challenge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "챌린지 ID")
	private Long id;
	
	@ApiModelProperty(required = true, value = "챌린지 주제")
	private String name;
	
	@Column(name = "start_date")
    @Temporal(TemporalType.DATE)
	@ApiModelProperty(required = true, value = "챌린지 시작일(모집마감일 다음 날)")
	private Date startDate;
    
	@Column(name = "end_date")
    @Temporal(TemporalType.DATE)
	@ApiModelProperty(required = true, value = "챌린지 마감일")
    private Date endDate;
    
	@ApiModelProperty(required = true, value = "배팅금액")
    private Integer fee;
    
    @Column(name = "is_random")
    @ApiModelProperty(required = true, value = "도전금 분배방식")
    private Boolean isRandom;

    @ApiModelProperty(required = true, value = "사진인증조건")
    private String certificationCondition;
    
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private Set<Certification> certifications;
    
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
	private Set<ChallengeAccount> challengeAccounts;

	protected Set<Certification> getCertificationsInternal() {
		if(this.certifications == null)
			this.certifications = new HashSet<Certification>();
		return certifications;
	}
	
	public void addCertification(Certification certification) {
		this.getCertificationsInternal().add(certification);
		certification.setChallenge(this);
	}
	
	protected Set<ChallengeAccount> getChallengeAccountInternal() {
		if(this.challengeAccounts == null)
			this.challengeAccounts = new HashSet<ChallengeAccount>();
		return challengeAccounts;
	}
	
	public void addChallengeAccount(ChallengeAccount challengeAccount) {
		this.getChallengeAccountInternal().add(challengeAccount);
		challengeAccount.setChallenge(this);
	}
	
	public static class Builder {
		private String name = "";
		private Date startDate;
		private Date endDate;
		private Integer fee = 0;
		private Boolean isRandom = false;
		private String certificationCondition = "";
		private Set<Certification> certifications;
		private Set<ChallengeAccount> challengeAccounts;
		
		public Builder() {
			
		}
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		public Builder startDate(Date starDate) {
			this.startDate = starDate;
			return this;
		}
		public Builder endDate(Date endDate) {
			this.endDate = endDate;
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
		public Builder certifications(Set<Certification> certifications) {
			this.certifications = certifications;
			return this;
		}
		public Builder challengeAccounts(Set<ChallengeAccount> challengeAccounts) {
			this.challengeAccounts = challengeAccounts;
			return this;
		}
		public Challenge build() {
			return new Challenge(this);
		}
	}
	private Challenge(Builder builder) {
		name = builder.name;
		startDate = builder.startDate;
		endDate = builder.endDate;
		fee = builder.fee;
		isRandom = builder.isRandom;
		certificationCondition = builder.certificationCondition;
		certifications = builder.certifications;
		challengeAccounts = builder.challengeAccounts;
	}
	
}
