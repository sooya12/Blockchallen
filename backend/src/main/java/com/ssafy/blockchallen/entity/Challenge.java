package com.ssafy.blockchallen.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    
    @OneToMany(mappedBy = "challenge")
    private Set<Certification> certifications;
    
    @OneToMany(mappedBy = "challenge")
	private Set<ChallengeAccount> challengeaccounts;

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
		if(this.challengeaccounts == null)
			this.challengeaccounts = new HashSet<ChallengeAccount>();
		return challengeaccounts;
	}
	
	public void addChallengeAccount(ChallengeAccount challengeaccount) {
		this.getChallengeAccountInternal().add(challengeaccount);
		challengeaccount.setChallenge(this);
	}
}
