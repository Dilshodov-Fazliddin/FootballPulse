package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.PostResponseDto;
import com.pulse.footballpulse.entity.enums.PostStatus;
import com.pulse.footballpulse.event.PostStatusChangedEvent;
import com.pulse.footballpulse.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${app.email.submission.address}")
    private String submissionEmail;

    @Override
    @EventListener
    @Async
    public void handlePostStatusChanged(PostStatusChangedEvent event) {
        log.info("Handling post status change event for post: {}", event.getPost().getId());
        try {
            sendPostStatusNotification(event.getAuthorEmail(), event.getAuthorName(), event.getPost());
        } catch (Exception e) {
            log.error("Failed to send email notification for post status change: {}", e.getMessage());
        }
    }

    @Override
    public void sendPostStatusNotification(String authorEmail, String authorName, PostResponseDto post) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(authorEmail);
            message.setFrom(submissionEmail);
            message.setSubject("Post Status Update - " + post.getTitle());

            String emailBody = buildStatusNotificationBody(authorName, post);
            message.setText(emailBody);

            mailSender.send(message);
            log.info("Status notification sent to author: {}", authorEmail);
        } catch (Exception e) {
            log.error("Failed to send status notification to {}: {}", authorEmail, e.getMessage());
        }
    }

    private String buildStatusNotificationBody(String authorName, PostResponseDto post) {
        StringBuilder body = new StringBuilder();
        body.append("Dear ").append(authorName).append(",\n\n");
        body.append("Your post \"").append(post.getTitle()).append("\" has been reviewed.\n\n");
        body.append("Status: ").append(post.getStatus()).append("\n");

        if (post.getStatus() == PostStatus.APPROVED) {
            body.append("Congratulations! Your post has been approved and is now live on Football Pulse.\n");
        } else if (post.getStatus() == PostStatus.REJECTED) {
            body.append("Unfortunately, your post has been rejected.\n");
            if (post.getRejectionReason() != null) {
                body.append("Reason: ").append(post.getRejectionReason()).append("\n");
            }
            body.append("Please review our content guidelines and feel free to submit again.\n");
        } else if (post.getStatus() == PostStatus.PENDING_REVIEW) {
            body.append("Your post requires additional review. We'll notify you once the review is complete.\n");
        }

        body.append("\nBest regards,\nFootball Pulse Team");
        return body.toString();
    }
}