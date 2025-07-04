package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.LoginDto;
import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.domain.response.ForgetPasswordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Service
public interface UserService {
    ResponseEntity<ApiResponse<?>>signUp(UserCreateDto userDto);
    ResponseEntity<ApiResponse<?>>login(LoginDto loginDto);
    UUID getCurrentUserId();
    ResponseEntity<ApiResponse<?>> verifyAccount (String email, Integer code);
    ResponseEntity<ApiResponse<?>>block(UUID userId);
    ResponseEntity<ApiResponse<?>>unBlock(UUID userId);
    ResponseEntity<ApiResponse<?>>setConfirmationCodeForNewPassword(String email);
    ResponseEntity<ApiResponse<?>>checkCodeConfirmationForNewPassword(ForgetPasswordDto forgetPasswordDto);
    ResponseEntity<ApiResponse<?>>setNewPassword(String email, String password);
    ResponseEntity<ApiResponse<?>>dynamicUpdateUserProfile(UUID id, Map<String,Object> updates);
    ResponseEntity<ApiResponse<?>>getProfile(Principal principal);
}
