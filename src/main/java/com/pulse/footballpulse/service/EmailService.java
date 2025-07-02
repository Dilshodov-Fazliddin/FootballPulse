package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.EmailPostSubmissionDto;
import com.pulse.footballpulse.domain.PostResponseDto;

import java.util.UUID;

public interface EmailService {
    
    void sendPostStatusNotification(String authorEmail, String authorName, PostResponseDto post);
    
    PostResponseDto processEmailSubmission(EmailPostSubmissionDto emailSubmission);
    
    void notifyAdminAboutEmailSubmission(PostResponseDto post);

    void sendSubmissionConfirmation(String authorEmail, String authorName, PostResponseDto post);

    void sendVerificationCode(String name,String mail, int verificationCode);

    void sendInvitationEmail(String targetEmail, String inviterName, UUID teamId);
    void sendUnBlockOrBlockMessage(String email,boolean status);
    void sendForgetPasswordConfirmationCode (String email,Integer code);
    void sendMessageAboutPasswordChanged(String email);
}