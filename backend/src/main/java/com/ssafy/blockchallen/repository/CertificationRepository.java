package com.ssafy.blockchallen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.blockchallen.entity.Account;
import com.ssafy.blockchallen.entity.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
	
}
