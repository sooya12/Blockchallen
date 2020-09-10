package com.ssafy.blockchallen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.blockchallen.entity.ChallengeAccount;

@Repository
public interface ChallengeAccountRepository extends JpaRepository<ChallengeAccount, Long> {

}
