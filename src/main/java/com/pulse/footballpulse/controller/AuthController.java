package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.LoginDto;
import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/football-pulse/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<?>> signUp(@RequestBody UserCreateDto userCreateDto) {
        return userService.signUp(userCreateDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PutMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verify(@RequestParam String email, @RequestParam Integer code) {
        return userService.verifyAccount(email, code);
    }
}
