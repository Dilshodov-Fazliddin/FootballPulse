package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.EmailPostSubmissionDto;
import com.pulse.footballpulse.domain.PostResponseDto;

public interface EmailService {
    
    /**
     * Send email notification to author about post status change
     */
    void sendPostStatusNotification(String authorEmail, String authorName, PostResponseDto post);
    
    /**
     * Process email submission from author and create post
     */
    PostResponseDto processEmailSubmission(EmailPostSubmissionDto emailSubmission);
    
    /**
     * Send notification to admin about new email submission
     */
    void notifyAdminAboutEmailSubmission(PostResponseDto post);
    
    /**
     * Send confirmation email to author about successful submission
     */
    void sendSubmissionConfirmation(String authorEmail, String authorName, PostResponseDto post);
}