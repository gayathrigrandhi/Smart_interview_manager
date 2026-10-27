package com.interviewmanagement.smart_interview_management.repository;

import com.interviewmanagement.smart_interview_management.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}