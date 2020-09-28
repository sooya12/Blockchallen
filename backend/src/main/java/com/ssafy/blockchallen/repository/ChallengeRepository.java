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
	@Query(value = "select * from challenge order by start_date asc", nativeQuery = true)
	Collection<Challenge> findAllOrderByStartDateAsc();
	@Query(value = "select * from challenge order by start_date desc", nativeQuery = true)
	Collection<Challenge> findAllOrderByStartDateDesc();
	@Query(value = "select * from challenge order by fee asc", nativeQuery = true)
	Collection<Challenge> findAllOrderByFeeAsc();
	@Query(value = "select * from challenge order by fee desc", nativeQuery = true)
	Collection<Challenge> findAllOrderByFeeDesc();
	Collection<Challenge> findAllByStartDate(String startDate);
}
