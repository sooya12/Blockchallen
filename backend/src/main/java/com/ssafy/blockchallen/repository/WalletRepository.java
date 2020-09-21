package com.ssafy.blockchallen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
	
	Optional<Wallet> findById(Long id);
	Optional<Wallet> findByAccount(Account account);
	
}
