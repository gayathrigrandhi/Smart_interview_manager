package com.interviewmanagement.smart_interview_management.controller;

import com.interviewmanagement.smart_interview_management.dto.InterviewRequest;
import com.interviewmanagement.smart_interview_management.entity.Interview;
import com.interviewmanagement.smart_interview_management.service.InterviewService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "*")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // GET ALL INTERVIEWS
    @GetMapping
    public ResponseEntity<List<Interview>> getAllInterviews() {
        return ResponseEntity.ok(interviewService.getAllInterviews());
    }

    // SCHEDULE INTERVIEW
    @PostMapping
    public ResponseEntity<?> scheduleInterview(
            @RequestBody InterviewRequest request) {

        try {
            Interview interview =
                    interviewService.scheduleInterview(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(interview);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // UPDATE INTERVIEW
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInterview(
            @PathVariable Long id,
            @RequestBody InterviewRequest request) {

        try {
            Interview interview =
                    interviewService.updateInterview(id, request);

            return ResponseEntity.ok(interview);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // DELETE INTERVIEW
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInterview(@PathVariable Long id) {

        try {
            interviewService.deleteInterview(id);

            return ResponseEntity.ok(
                    Map.of("message", "Interview deleted successfully")
            );

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
