package com.ssafy.blockchallen.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value="지갑 번호")
	private Long id;
	
	@ApiModelProperty(required = true, value="회원 번호")
	private long ownerId;
	
	@ApiModelProperty(required = true, value="지갑 주소")
	private String address;

}
