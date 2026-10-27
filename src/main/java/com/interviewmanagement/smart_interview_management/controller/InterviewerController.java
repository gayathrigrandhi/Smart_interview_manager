package com.interviewmanagement.smart_interview_management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.interviewmanagement.smart_interview_management.entity.Interviewer;
import com.interviewmanagement.smart_interview_management.service.InterviewerService;

@RestController
@RequestMapping("/api/interviewers")
public class InterviewerController {

    private final InterviewerService interviewerService;

    public InterviewerController(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }

    @PostMapping
    public ResponseEntity<Interviewer> createInterviewer(
            @RequestBody Interviewer interviewer) {

        return ResponseEntity.ok(
                interviewerService.createInterviewer(interviewer)
        );
    }

    @GetMapping
    public ResponseEntity<List<Interviewer>> getAllInterviewers() {

        return ResponseEntity.ok(
                interviewerService.getAllInterviewers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interviewer> getInterviewerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                interviewerService.getInterviewerById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interviewer> updateInterviewer(
            @PathVariable Long id,
            @RequestBody Interviewer interviewer) {

        return ResponseEntity.ok(
                interviewerService.updateInterviewer(id, interviewer)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInterviewer(
            @PathVariable Long id) {

        interviewerService.deleteInterviewer(id);

        return ResponseEntity.ok("Interviewer deleted successfully");
    }
}