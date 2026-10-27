package com.interviewmanagement.smart_interview_management.service;

import com.interviewmanagement.smart_interview_management.dto.InterviewRequest;
import com.interviewmanagement.smart_interview_management.entity.Candidate;
import com.interviewmanagement.smart_interview_management.entity.Interview;
import com.interviewmanagement.smart_interview_management.entity.Interviewer;
import com.interviewmanagement.smart_interview_management.repository.CandidateRepository;
import com.interviewmanagement.smart_interview_management.repository.InterviewerRepository;
import com.interviewmanagement.smart_interview_management.repository.InterviewRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final CandidateRepository candidateRepository;
    private final InterviewerRepository interviewerRepository;

    public InterviewService(
            InterviewRepository interviewRepository,
            CandidateRepository candidateRepository,
            InterviewerRepository interviewerRepository) {

        this.interviewRepository = interviewRepository;
        this.candidateRepository = candidateRepository;
        this.interviewerRepository = interviewerRepository;
    }

    // GET ALL INTERVIEWS
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    // SCHEDULE INTERVIEW
    public Interview scheduleInterview(InterviewRequest request) {

        Candidate candidate = candidateRepository
                .findById(request.getCandidateId())
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

        Interviewer interviewer = interviewerRepository
                .findById(request.getInterviewerId())
                .orElseThrow(() ->
                        new RuntimeException("Interviewer not found"));

        validateInterviewTime(request);

        checkInterviewConflict(
                interviewer.getInterviewerId(),
                request,
                null
        );

        Interview interview = new Interview();

        interview.setCandidate(candidate);
        interview.setInterviewer(interviewer);
        interview.setInterviewDate(request.getInterviewDate());
        interview.setStartTime(request.getStartTime());
        interview.setEndTime(request.getEndTime());

        return interviewRepository.save(interview);
    }

    // DELETE INTERVIEW
    public void deleteInterview(Long id) {

        if (!interviewRepository.existsById(id)) {
            throw new RuntimeException("Interview not found");
        }

        interviewRepository.deleteById(id);
    }

    // UPDATE INTERVIEW
    public Interview updateInterview(Long id, InterviewRequest request) {

        Interview interview = interviewRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));

        Candidate candidate = candidateRepository
                .findById(request.getCandidateId())
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

        Interviewer interviewer = interviewerRepository
                .findById(request.getInterviewerId())
                .orElseThrow(() ->
                        new RuntimeException("Interviewer not found"));

        validateInterviewTime(request);

        checkInterviewConflict(
                interviewer.getInterviewerId(),
                request,
                id
        );

        interview.setCandidate(candidate);
        interview.setInterviewer(interviewer);
        interview.setInterviewDate(request.getInterviewDate());
        interview.setStartTime(request.getStartTime());
        interview.setEndTime(request.getEndTime());

        return interviewRepository.save(interview);
    }

    // VALIDATE DATE AND TIME
    private void validateInterviewTime(InterviewRequest request) {

        if (request.getInterviewDate() == null
                || request.getStartTime() == null
                || request.getEndTime() == null) {

            throw new IllegalArgumentException(
                    "Interview date, start time, and end time are required"
            );
        }

        if (!request.getStartTime().isBefore(request.getEndTime())) {

            throw new IllegalArgumentException(
                    "Start time must be earlier than end time"
            );
        }
    }

    // CHECK INTERVIEWER SCHEDULING CONFLICTS
    private void checkInterviewConflict(
            Long interviewerId,
            InterviewRequest request,
            Long excludeInterviewId) {

        List<Interview> interviews = interviewRepository.findAll();

        for (Interview existing : interviews) {

            // Skip the interview currently being updated
            if (excludeInterviewId != null
                    && existing.getInterviewId().equals(excludeInterviewId)) {
                continue;
            }

            // Skip incomplete interviewer references
            if (existing.getInterviewer() == null
                    || existing.getInterviewer().getInterviewerId() == null) {
                continue;
            }

            boolean sameInterviewer =
                    existing.getInterviewer()
                            .getInterviewerId()
                            .equals(interviewerId);

            boolean sameDate =
                    request.getInterviewDate().equals(
                            existing.getInterviewDate()
                    );

            // Two interviews conflict when their time intervals overlap
            boolean overlapping =
                    request.getStartTime().isBefore(existing.getEndTime())
                    && request.getEndTime().isAfter(existing.getStartTime());

            if (sameInterviewer && sameDate && overlapping) {

                throw new IllegalArgumentException(
                        "Interview conflict: This interviewer already has "
                        + "an interview scheduled during the selected time."
                );
            }
        }
    }
}