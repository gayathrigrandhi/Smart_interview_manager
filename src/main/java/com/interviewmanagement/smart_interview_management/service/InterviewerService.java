package com.interviewmanagement.smart_interview_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.interviewmanagement.smart_interview_management.entity.Interviewer;
import com.interviewmanagement.smart_interview_management.repository.InterviewerRepository;

@Service
public class InterviewerService {

    private final InterviewerRepository interviewerRepository;

    public InterviewerService(InterviewerRepository interviewerRepository) {
        this.interviewerRepository = interviewerRepository;
    }

    public Interviewer createInterviewer(Interviewer interviewer) {
        return interviewerRepository.save(interviewer);
    }

    public List<Interviewer> getAllInterviewers() {
        return interviewerRepository.findAll();
    }

    public Interviewer getInterviewerById(Long id) {
        return interviewerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interviewer not found"));
    }

    public Interviewer updateInterviewer(Long id, Interviewer interviewer) {

        Interviewer existing = getInterviewerById(id);

        existing.setName(interviewer.getName());
        existing.setEmail(interviewer.getEmail());
        existing.setDepartment(interviewer.getDepartment());
        existing.setExpertiseArea(interviewer.getExpertiseArea());

        return interviewerRepository.save(existing);
    }

    public void deleteInterviewer(Long id) {
        interviewerRepository.deleteById(id);
    }
}