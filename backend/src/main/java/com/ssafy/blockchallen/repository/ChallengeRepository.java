package com.ssafy.blockchallen.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.blockchallen.entity.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
	Optional<Challenge> findById(Long id);
	Collection<Challenge> findAllOrderByStartDateAsc();
	Collection<Challenge> findAllOrderByStartDateDesc();
	Collection<Challenge> findAllOrderByFeeAsc();
	Collection<Challenge> findAllOrderByFeeDesc();
}
