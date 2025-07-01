package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.LoginDto;
import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    ResponseEntity<ApiResponse<?>>signUp(UserCreateDto userDto);
    ResponseEntity<ApiResponse<?>>login(LoginDto loginDto);
    UUID getCurrentUserId();
}
