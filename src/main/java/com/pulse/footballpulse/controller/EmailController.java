package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.EmailPostSubmissionDto;
import com.pulse.footballpulse.domain.PostResponseDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    /**
     * Process email submission manually (for admin use)
     * This endpoint allows admins to manually process email submissions
     */
    @PostMapping("/submissions/process")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PostResponseDto>> processEmailSubmission(
            @RequestBody EmailPostSubmissionDto emailSubmission) {
        
        try {
            PostResponseDto post = emailService.processEmailSubmission(emailSubmission);
            
            return ResponseEntity.ok(ApiResponse.<PostResponseDto>builder()
                    .status(200)
                    .message("Email submission processed successfully")
                    .data(post)
                    .build());
                    
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.<PostResponseDto>builder()
                            .status(400)
                            .message("Failed to process email submission: " + e.getMessage())
                            .build());
        }
    }

    /**
     * Send test notification (for admin testing)
     */
    @PostMapping("/test-notification")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> sendTestNotification(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String postTitle) {
        
        try {
            // Create a dummy post for testing
            PostResponseDto testPost = new PostResponseDto();
            testPost.setTitle(postTitle);
            testPost.setStatus(com.pulse.footballpulse.entity.enums.PostStatus.APPROVED);
            
            emailService.sendPostStatusNotification(email, name, testPost);
            
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .status(200)
                    .message("Test notification sent successfully to " + email)
                    .build());
                    
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.<String>builder()
                            .status(400)
                            .message("Failed to send test notification: " + e.getMessage())
                            .build());
        }
    }

    /**
     * Webhook endpoint for email service integration
     * This can be used by external email services to automatically process submissions
     */
    @PostMapping("/webhook/submission")
    public ResponseEntity<ApiResponse<PostResponseDto>> webhookEmailSubmission(
            @RequestBody EmailPostSubmissionDto emailSubmission,
            @RequestHeader(value = "X-Webhook-Secret", required = false) String webhookSecret) {
        
        // TODO: Implement webhook secret validation for security
        // if (!isValidWebhookSecret(webhookSecret)) {
        //     return ResponseEntity.status(401).build();
        // }
        
        try {
            PostResponseDto post = emailService.processEmailSubmission(emailSubmission);
            
            return ResponseEntity.ok(ApiResponse.<PostResponseDto>builder()
                    .status(200)
                    .message("Email submission processed via webhook")
                    .data(post)
                    .build());
                    
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.<PostResponseDto>builder()
                            .status(400)
                            .message("Webhook processing failed: " + e.getMessage())
                            .build());
        }
    }
}