package com.ssafy.blockchallen.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Certification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "인증 ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "challenge_id")
	@ApiModelProperty(required = true, value = "챌린지 ID")
	private Challenge challenge;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	@ApiModelProperty(required = true, value = "회원 계정 ID")
	private Account account;
	
	@ApiModelProperty(required = true, value = "인증 사진")
	private String picture;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(required = true, value = "등록시간")
	private Date regDate;
	
	@Column(name = "is_reported")
	@ApiModelProperty(required = true, value = "신고여부")
	private Boolean isReported;
}
