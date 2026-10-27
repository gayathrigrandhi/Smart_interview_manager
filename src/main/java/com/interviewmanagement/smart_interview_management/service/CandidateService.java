package com.interviewmanagement.smart_interview_management.service;

import com.interviewmanagement.smart_interview_management.entity.Candidate;
import com.interviewmanagement.smart_interview_management.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    // CREATE
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    // READ ALL
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // READ BY ID
    public Candidate getCandidateById(Long id) {

        Optional<Candidate> candidate =
                candidateRepository.findById(id);

        if (candidate.isPresent()) {
            return candidate.get();
        }

        return null;
    }

    // UPDATE
    public Candidate updateCandidate(Long id, Candidate updatedCandidate) {

        Optional<Candidate> existingCandidate =
                candidateRepository.findById(id);

        if (existingCandidate.isPresent()) {

            Candidate candidate = existingCandidate.get();

            candidate.setName(updatedCandidate.getName());
            candidate.setEmail(updatedCandidate.getEmail());
            candidate.setPhone(updatedCandidate.getPhone());
            candidate.setSkillset(updatedCandidate.getSkillset());

            return candidateRepository.save(candidate);
        }

        return null;
    }

    // DELETE
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}