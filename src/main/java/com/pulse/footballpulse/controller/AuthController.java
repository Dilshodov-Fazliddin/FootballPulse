package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.LoginDto;
import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.domain.response.ForgetPasswordDto;
import com.pulse.footballpulse.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/football-pulse/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<?>> signUp(@Valid @RequestBody UserCreateDto userCreateDto) {
        return userService.signUp(userCreateDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PutMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verify(@RequestParam String email, @RequestParam Integer code) {
        return userService.verifyAccount(email, code);
    }

    @PutMapping("/forgetPassword")
    public ResponseEntity<ApiResponse<?>> forgetPassword(@Valid @RequestParam String email) {
        return userService.setConfirmationCodeForNewPassword(email);
    }

    @PutMapping("/check-forget-password")
    public ResponseEntity<ApiResponse<?>> verifyForgetPassword(@Valid @RequestBody ForgetPasswordDto forgetPasswordDto) {
        return userService.checkCodeConfirmationForNewPassword(forgetPasswordDto);

    }

    @PutMapping("/set-new-password")
    public ResponseEntity<ApiResponse<?>> setNewPassword(@RequestParam String email, @RequestParam String newPassword) {
        return userService.setNewPassword(email, newPassword);
    }
}
