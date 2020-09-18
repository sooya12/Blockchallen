package com.ssafy.blockchallen.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.blockchallen.entity.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
	Optional<Challenge> findById(Long id);
	@Query(value = "select * from table limit ?1, 2", nativeQuery = true)
	Collection<Challenge> infinite(int limit);
}
