package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.EmailPostSubmissionDto;
import com.pulse.footballpulse.domain.PostCreateDto;
import com.pulse.footballpulse.domain.PostResponseDto;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.PostStatus;
import com.pulse.footballpulse.entity.enums.UserRoles;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.repository.UserRepository;
import com.pulse.footballpulse.service.EmailService;
import com.pulse.footballpulse.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final PostService postService;

    @Value("${app.email.admin.notification}")
    private String adminEmail;

    @Value("${app.email.submission.address}")
    private String submissionEmail;

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

    @Override
    public PostResponseDto processEmailSubmission(EmailPostSubmissionDto emailSubmission) {
        try {
            // Find author by email
            UserEntity author = userRepository.findByMail(emailSubmission.getAuthorEmail());

            if (author == null) {
                throw new DataNotFoundException("Author not found with email: " + emailSubmission.getAuthorEmail());
            }

            // Check if user has permission to submit posts
            if (!hasAuthorPermission(author.getRole())) {
                throw new IllegalArgumentException("User does not have permission to submit posts via email");
            }

            // Create PostCreateDto from email submission
            PostCreateDto postCreateDto = new PostCreateDto();
            postCreateDto.setTitle(emailSubmission.getTitle());
            postCreateDto.setContent(emailSubmission.getContent());
            postCreateDto.setTags(emailSubmission.getTags());

            // Handle attachments - for now, we'll use the first attachment as image URL
            if (emailSubmission.getAttachmentUrls() != null && !emailSubmission.getAttachmentUrls().isEmpty()) {
                postCreateDto.setImageUrl(emailSubmission.getAttachmentUrls().get(0));
            }

            // Create post through PostService
            PostResponseDto createdPost = postService.createPost(postCreateDto, author.getId());

            // Send confirmation to author
            sendSubmissionConfirmation(author.getMail(), author.getFirstName() + " " + author.getLastName(), createdPost);

            // Notify admin about new submission
            notifyAdminAboutEmailSubmission(createdPost);

            log.info("Email submission processed successfully for author: {}", emailSubmission.getAuthorEmail());
            return createdPost;

        } catch (Exception e) {
            log.error("Failed to process email submission from {}: {}", emailSubmission.getAuthorEmail(), e.getMessage());
            throw new RuntimeException("Failed to process email submission", e);
        }
    }

    @Override
    public void notifyAdminAboutEmailSubmission(PostResponseDto post) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(adminEmail);
            message.setFrom(submissionEmail);
            message.setSubject("New Post Submission for Review - " + post.getTitle());

            String emailBody = buildAdminNotificationBody(post);
            message.setText(emailBody);

            mailSender.send(message);
            log.info("Admin notification sent for post: {}", post.getId());
        } catch (Exception e) {
            log.error("Failed to send admin notification for post {}: {}", post.getId(), e.getMessage());
        }
    }

    @Override
    public void sendSubmissionConfirmation(String authorEmail, String authorName, PostResponseDto post) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(authorEmail);
            message.setFrom(submissionEmail);
            message.setSubject("Submission Confirmation - " + post.getTitle());

            String emailBody = buildSubmissionConfirmationBody(authorName, post);
            message.setText(emailBody);

            mailSender.send(message);
            log.info("Submission confirmation sent to author: {}", authorEmail);
        } catch (Exception e) {
            log.error("Failed to send submission confirmation to {}: {}", authorEmail, e.getMessage());
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

    private String buildAdminNotificationBody(PostResponseDto post) {
        StringBuilder body = new StringBuilder();
        body.append("A new post has been submitted via email and requires review:\n\n");
        body.append("Title: ").append(post.getTitle()).append("\n");
        body.append("Author: ").append(post.getAuthorName()).append("\n");
        body.append("Post ID: ").append(post.getId()).append("\n");
        body.append("Status: ").append(post.getStatus()).append("\n");
        body.append("Submitted: ").append(post.getCreatedAt()).append("\n\n");
        body.append("Please review this post in the admin panel.\n\n");
        body.append("Football Pulse Admin System");
        return body.toString();
    }

    private String buildSubmissionConfirmationBody(String authorName, PostResponseDto post) {
        StringBuilder body = new StringBuilder();
        body.append("Dear ").append(authorName).append(",\n\n");
        body.append("Thank you for your submission to Football Pulse!\n\n");
        body.append("Post Details:\n");
        body.append("Title: ").append(post.getTitle()).append("\n");
        body.append("Submission ID: ").append(post.getId()).append("\n");
        body.append("Status: ").append(post.getStatus()).append("\n\n");
        body.append("Your post is now in the review queue. Our editorial team will review it shortly.\n");
        body.append("You will receive another email once the review is complete.\n\n");
        body.append("Best regards,\nFootball Pulse Team");
        return body.toString();
    }

    private boolean hasAuthorPermission(UserRoles role) {
        return role == UserRoles.ROLE_AUTHOR || 
               role == UserRoles.ROLE_CLUB || 
               role == UserRoles.ROLE_ADMIN;
    }
}
