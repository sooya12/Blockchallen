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
public class Reward {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "회원-챌린지 ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	@ApiModelProperty(value = "회원")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "challenge_id")
	@ApiModelProperty(value = "챌린지")
	private Challenge challenge;
	
	@ApiModelProperty(value = "상금")
	private int prize;
}
