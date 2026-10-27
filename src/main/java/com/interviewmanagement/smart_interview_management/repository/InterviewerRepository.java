package com.interviewmanagement.smart_interview_management.repository;

import com.interviewmanagement.smart_interview_management.entity.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewerRepository
        extends JpaRepository<Interviewer, Long> {
}