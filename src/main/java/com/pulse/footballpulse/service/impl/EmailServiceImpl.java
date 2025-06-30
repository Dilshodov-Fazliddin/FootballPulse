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
import com.pulse.footballpulse.util.EmailTemplateBuilder;
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
    private final EmailTemplateBuilder emailTemplateBuilder;
    @Override
    public void sendPostStatusNotification(String authorEmail, String authorName, PostResponseDto post) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(authorEmail);
            message.setFrom(submissionEmail);
            message.setSubject("Post Status Update - " + post.getTitle());

            String emailBody = emailTemplateBuilder.buildStatusNotificationBody(authorName, post);
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
            UserEntity author = userRepository.findByMail(emailSubmission.getAuthorEmail())
                    .orElseThrow(()->new DataNotFoundException("Author not found"));

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

            String emailBody = emailTemplateBuilder.buildAdminNotificationBody(post);
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

            String emailBody = emailTemplateBuilder.buildSubmissionConfirmationBody(authorName, post);
            message.setText(emailBody);

            mailSender.send(message);
            log.info("Submission confirmation sent to author: {}", authorEmail);
        } catch (Exception e) {
            log.error("Failed to send submission confirmation to {}: {}", authorEmail, e.getMessage());
        }
    }

    @Override
    public void sendVerificationCode(String name,String mail, int verificationCode) {
        try {
            userRepository.findByMail(mail);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail);
            message.setSubject("Verification Code");
            message.setText(emailTemplateBuilder.buildVerificationCode(name, verificationCode));
            mailSender.send(message);
            log.info("Verification code sent");
        } catch (Exception e) {
            log.error("Failed to send verification code to {}: {}", mail, e.getMessage());
        }
    }


    private boolean hasAuthorPermission(UserRoles role) {
        return role == UserRoles.ROLE_AUTHOR || 
               role == UserRoles.ROLE_CLUB || 
               role == UserRoles.ROLE_ADMIN;
    }
}
