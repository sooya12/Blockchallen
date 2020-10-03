package com.ssafy.blockchallen.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@ApiModelProperty(required = true, value = "챌린지 주제")
	private String name;
	
	@Column(name = "start_date")
	@ApiModelProperty(required = true, value = "챌린지 시작일")
	private String startDate;
    
	@Column(name = "end_date")
	@ApiModelProperty(required = true, value = "챌린지 마감일")
    private String endDate;

	@Column(name = "expire_date")
	@ApiModelProperty(required = true, value = "모집 마감일")
	private String expireDate;
    
	@ApiModelProperty(required = true, value = "배팅금액")
    private Integer fee;
    
    @Column(name = "is_random")
    @ApiModelProperty(required = true, value = "도전금 분배방식")
    private Boolean isRandom;

    @ApiModelProperty(required = true, value = "사진인증조건")
    private String certificationCondition;
    
    @ApiModelProperty(required = true, value = "사진인증조건 예시")
    @Column(name = "", columnDefinition = "MEDIUMBLOB")
    private byte[] samplepicture;
    
    @ApiModelProperty(value = "인증가능 시작시간")
    private Integer certificationStartTime;

    @ApiModelProperty(value = "인증가능 종료시간")
    private Integer certificationEndTime;
    
    @ApiModelProperty(required = true, value="챌린지 지갑 주소")
	private String address;
    
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    @JsonBackReference
    @ApiModelProperty(value = "인증 set")
    private Set<Certification> certifications;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
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
		account.addChallenge(this);
	}
	
	public static class Builder {
		private String name = "";
		private String startDate = "";
		private String endDate = "";
		private String expireDate = "";
		private Integer fee = 0;
		private Boolean isRandom = false;
		private String certificationCondition = "";
		private byte[] samplepicture;
	    private Integer certificationStartTime;
	    private Integer certificationEndTime;
	    private String address;
		private Set<Certification> certifications;
		private Set<Account> accounts;
		
		public Builder() {
			
		}
		public Builder name(String name) {
			this.name = name;
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
		public Builder expireDate(String expireDate) {
			this.expireDate = expireDate;
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
		public Builder samplepicture(byte[] samplepicture) {
			this.samplepicture = samplepicture;
			return this;
		}
		public Builder certificationStartTime(int certificationStartTime) {
			this.certificationStartTime = certificationStartTime;
			return this;
		}
		public Builder certificationEndTime(int certificationEndTime) {
			this.certificationEndTime = certificationEndTime;
			return this;
		}
		public Builder address(String address) {
			this.address = address;
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
		name = builder.name;
		expireDate = builder.expireDate;
		startDate = builder.startDate;
		endDate = builder.endDate;
		fee = builder.fee;
		isRandom = builder.isRandom;
		certificationCondition = builder.certificationCondition;
		samplepicture = builder.samplepicture;
		certifications = builder.certifications;
		accounts = builder.accounts;
		certificationStartTime = builder.certificationStartTime;
		certificationEndTime = builder.certificationEndTime;
		address = builder.address;
	}
	
}
