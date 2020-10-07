package com.ssafy.blockchallen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Challenge;
import com.ssafy.blockchallen.entity.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long>{
	Optional<Reward> findByAccountAndChallenge(Account account, Challenge challenge);
}
