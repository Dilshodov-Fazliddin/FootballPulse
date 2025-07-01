package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/football-pulse/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> blockUser(@RequestParam UUID id){
        return userService.block(id);
    }
    @PutMapping("/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> unblockUser(@RequestParam UUID id){
        return userService.unBlock(id);
    }

}
