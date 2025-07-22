package com.pulse.footballpulse.util;

import com.pulse.footballpulse.domain.PostResponseDto;
import com.pulse.footballpulse.entity.enums.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class
EmailTemplateBuilder {
    public String buildSubmissionConfirmationBody(String authorName, PostResponseDto post) {
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
    public String buildAdminNotificationBody(PostResponseDto post) {
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

    public String buildStatusNotificationBody(String authorName, PostResponseDto post) {
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

    public String buildVerificationCode(String authorName, Integer code) {
        return "Dear " + authorName + "\n" +
                "Welcome to our system 😉" + "\n" +
                "Your verification code 🗝️: " + code;
    }


    public String buildBlockMessage(String authorName) {
        return "Dear " + authorName + "\n" +
                "Unfortunately, your account has blocked";
    }

    public String buildUnBlockMessage(String authorName) {
        return "Dear " + authorName + "\n" +
                "Your account unlocked" +"\n"+
                "We hope you never do anything forbidden.";
    }

    public String buildForgetPasswordCodeBody(String authorName, int code ) {
        return "Dear " + authorName +"\n"+
                "Do you want change your password ?" + "\n"+
                "It is your code confirmation: " + code;
    }

    public String buildMessageAboutPasswordChanged(String authorName ) {
        return "Dear " + authorName +"\n"+
                "Your password successfully changed";
    }
}
