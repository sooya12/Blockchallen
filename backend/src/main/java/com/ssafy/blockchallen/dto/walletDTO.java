package com.ssafy.blockchallen.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class walletDTO {
	private long id;
	private String address;
	private BigDecimal balance = BigDecimal.valueOf(0);
}
