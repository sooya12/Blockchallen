package com.ssafy.blockchallen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;
import com.ssafy.blockchallen.entity.Challenge;
import com.ssafy.blockchallen.entity.Wallet;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
	Optional<Wallet> findByAccount(Account account);
	Optional<Challenge> findByChallenge(Challenge challenge);
}
