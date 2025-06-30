package com.pulse.footballpulse.service;

import com.pulse.footballpulse.event.PostStatusChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface EmailNotificationService {
    
    /**
     * Handle post status change events and send email notifications
     */
    @EventListener
    @Async
    void handlePostStatusChanged(PostStatusChangedEvent event);
    
    /**
     * Send email notification to author about post status change
     */
    void sendPostStatusNotification(String authorEmail, String authorName, com.pulse.footballpulse.domain.PostResponseDto post);
}