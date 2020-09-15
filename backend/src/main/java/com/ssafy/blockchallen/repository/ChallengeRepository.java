package com.ssafy.blockchallen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.blockchallen.entity.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

}
