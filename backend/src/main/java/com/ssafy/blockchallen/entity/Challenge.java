package com.ssafy.blockchallen.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
public class Challenge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "챌린지 ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "captain_id")
	@JsonManagedReference
	@ApiModelProperty(required = true, value = "챌린지 방장")
	private Account captain;
	
	@ApiModelProperty(required = true, value = "챌린지 주제")
	private String name;
	
	@Column(name = "expire_date")
	@ApiModelProperty(required = true, value = "모집 마감일")
	private String expireDate;
	
	@Column(name = "start_date")
	@ApiModelProperty(required = true, value = "챌린지 시작일")
	private String startDate;
    
	@Column(name = "end_date")
	@ApiModelProperty(required = true, value = "챌린지 마감일")
    private String endDate;
    
	@ApiModelProperty(required = true, value = "배팅금액")
    private Integer fee;
    
    @Column(name = "is_random")
    @ApiModelProperty(required = true, value = "도전금 분배방식")
    private Boolean isRandom;

    @ApiModelProperty(required = true, value = "사진인증조건")
    private String certificationCondition;
    
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    @JsonBackReference
    @ApiModelProperty(value = "인증 set")
    private Set<Certification> certifications;
    
    @ManyToMany(mappedBy = "challenges", cascade = CascadeType.ALL)
    @JsonBackReference
    @ApiModelProperty(value = "참여한 회원 계정 set")
	private Set<Account> accounts;

	protected Set<Certification> getCertificationsInternal() {
		if(this.certifications == null)
			this.certifications = new HashSet<Certification>();
		return certifications;
	}
	
	public void addCertification(Certification certification) {
		this.getCertificationsInternal().add(certification);
		certification.setChallenge(this);
	}
	
	protected Set<Account> getAccountsInternal() {
		if(this.accounts == null)
			this.accounts = new HashSet<Account>();
		return accounts;
	}
	
	public void addAccount(Account account) {
		this.getAccountsInternal().add(account);
	}
	
	public static class Builder {
		private Account captain;
		private String name = "";
		private String expireDate = "";
		private String startDate = "";
		private String endDate = "";
		private Integer fee = 0;
		private Boolean isRandom = false;
		private String certificationCondition = "";
		private Set<Certification> certifications;
		private Set<Account> accounts;
		
		public Builder() {
			
		}
		public Builder captain(Account captain) {
			this.captain = captain;
			return this;
		}
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		public Builder expireDate(String expireDate) {
			this.expireDate = expireDate;
			return this;
		}
		public Builder startDate(String starDate) {
			this.startDate = starDate;
			return this;
		}
		public Builder endDate(String endDate) {
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
		public Builder accounts(Set<Account> accounts) {
			this.accounts = accounts;
			return this;
		}
		public Challenge build() {
			return new Challenge(this);
		}
	}
	private Challenge(Builder builder) {
		captain = builder.captain;
		name = builder.name;
		expireDate = builder.expireDate;
		startDate = builder.startDate;
		endDate = builder.endDate;
		fee = builder.fee;
		isRandom = builder.isRandom;
		certificationCondition = builder.certificationCondition;
		certifications = builder.certifications;
		accounts = builder.accounts;
	}
	
}
