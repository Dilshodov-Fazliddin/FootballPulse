package com.pulse.footballpulse.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface TeamEmailService {
    void sendInvitationEmail(String targetEmail, String inviterName, UUID teamId);
}
