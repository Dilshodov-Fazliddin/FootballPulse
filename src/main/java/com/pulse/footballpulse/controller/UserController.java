package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.UsersDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.enums.UserRoles;
import com.pulse.footballpulse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequestMapping("/football-pulse/user")
@RestController
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

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyRole('USER','AUTHOR','ADMIN','MODERATOR','CLUB')")
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable UUID userId, @RequestBody Map<String, Object>updates){
        return userService.dynamicUpdateUserProfile(userId,updates);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','AUTHOR','ADMIN','MODERATOR','CLUB')")
    public ResponseEntity<ApiResponse<?>> getUserProfile(Principal principal){
        return userService.getProfile(principal);
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UsersDto>>> getAllUsers(@RequestParam(defaultValue = "0")int page,
                                                                   @RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }

    @PutMapping("/role/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> updateUserRole(@PathVariable UUID id, @RequestParam UserRoles role){
        return userService.updateUserRole(id, role);
    }

}

