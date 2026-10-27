package com.interviewmanagement.smart_interview_management.repository;

import com.interviewmanagement.smart_interview_management.entity.Interview;
import com.interviewmanagement.smart_interview_management.entity.InterviewStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("""
        SELECT COUNT(i) > 0
        FROM Interview i
        WHERE i.interviewer.interviewerId = :interviewerId
        AND i.interviewDate = :date
        AND i.status <> :cancelled
        AND i.startTime < :endTime
        AND i.endTime > :startTime
        AND i.interviewId <> :interviewId
    """)
    boolean interviewerHasConflict(
            Long interviewerId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            InterviewStatus cancelled,
            Long interviewId
    );

    @Query("""
        SELECT COUNT(i) > 0
        FROM Interview i
        WHERE i.candidate.candidateId = :candidateId
        AND i.interviewDate = :date
        AND i.status <> :cancelled
        AND i.startTime < :endTime
        AND i.endTime > :startTime
        AND i.interviewId <> :interviewId
    """)
    boolean candidateHasConflict(
            Long candidateId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            InterviewStatus cancelled,
            Long interviewId
    );
}