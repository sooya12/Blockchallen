package com.ssafy.blockchallen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
	
	// 지갑 주소로 지갑 정보 조회
	Wallet findByAccount(Account account);
	
	//Wallet findAllByAddress(Collect)
	
}
