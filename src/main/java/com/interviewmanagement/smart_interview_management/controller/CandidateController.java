package com.interviewmanagement.smart_interview_management.controller;

import com.interviewmanagement.smart_interview_management.entity.Candidate;
import com.interviewmanagement.smart_interview_management.service.CandidateService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    // CREATE
    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateService.createCandidate(candidate);
    }

    // READ ALL
    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Candidate getCandidateById(@PathVariable Long id) {
        return candidateService.getCandidateById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Candidate updateCandidate(
            @PathVariable Long id,
            @RequestBody Candidate candidate) {

        return candidateService.updateCandidate(id, candidate);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteCandidate(@PathVariable Long id) {

        candidateService.deleteCandidate(id);

        return "Candidate deleted successfully";
    }
}