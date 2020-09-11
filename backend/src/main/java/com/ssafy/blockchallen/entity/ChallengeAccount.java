package com.ssafy.blockchallen.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class ChallengeAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "챌린지-회원계정 ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "challenge_id")
	@ApiModelProperty(required = true, value = "챌린지 ID")
	private Challenge challenge;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	@ApiModelProperty(required = true, value = "회원 계정 ID")
	private Account account;
	
}
