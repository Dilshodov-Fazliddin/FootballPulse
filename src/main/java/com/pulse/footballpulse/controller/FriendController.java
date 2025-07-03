package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.FriendCreateDto;
import com.pulse.footballpulse.domain.FriendUpdateDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.domain.response.FriendResponseDto;
import com.pulse.footballpulse.entity.enums.FriendStatus;
import com.pulse.footballpulse.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/friend")
public class FriendController {
    private final FriendService friendService;

    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB')")
    @PostMapping("send")
    public ResponseEntity<ApiResponse<FriendResponseDto>> addFriend(@RequestBody FriendCreateDto friendCreateDto) {
        FriendResponseDto responseDto = friendService.addFriend(friendCreateDto);
        return ResponseEntity.ok(ApiResponse.<FriendResponseDto>builder()
                .status(201)
                .data(responseDto)
                .message("Friend added successfully")
                .build());
    }


    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN')")
    @PostMapping("{id}/accept")
    public ResponseEntity<ApiResponse<?>> acceptFriendRequest(@PathVariable UUID id) {
        FriendResponseDto responseDto = friendService.acceptFriend(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(responseDto)
                .message("accept request successfully")
                .status(201)
                .build());

    }

    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN')")
    @PostMapping("{id}/reject")
    public ResponseEntity<ApiResponse<FriendResponseDto>> rejectFriendRequest(@PathVariable UUID id) {
        FriendResponseDto responseDto = friendService.rejectFriend(id);
        return ResponseEntity.ok(ApiResponse.<FriendResponseDto>builder()
                .data(responseDto)
                .message("reject request successfully")
                .status(201)
                .build());

    }

    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Page<FriendResponseDto>>> getAllFriends(@PathVariable UUID userId,
                                                                              @RequestParam(required = false) UUID friendId,
                                                                              @RequestParam(required = false) FriendStatus friendStatus,
                                                                              @RequestParam(required = false, defaultValue = "10") int size,
                                                                              @RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FriendResponseDto> friends = friendService.getFriends(pageable, userId, friendId, friendStatus);

        return ResponseEntity.ok(ApiResponse.<Page<FriendResponseDto>>builder()
                .status(200)
                .message("Search results retrieved successfully")
                .data(friends)
                .build());
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<FriendResponseDto>>> searchAllFriends(@RequestParam(required = false) UUID userId,
                                                                                 @RequestParam(required = false) UUID friendId,
                                                                                 @RequestParam(required = false) FriendStatus friendStatus,
                                                                                 @RequestParam(required = false, defaultValue = "10") int size,
                                                                                 @RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FriendResponseDto> friends = friendService.getFriends(pageable, userId, friendId, friendStatus);

        return ResponseEntity.ok(ApiResponse.<Page<FriendResponseDto>>builder()
                .status(200)
                .message("Search results retrieved successfully")
                .data(friends)
                .build());
    }


    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> deleteFriend(@PathVariable UUID id) {
        friendService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(204)
                .message("Delete request successfully")
                .build());
    }

    @PreAuthorize("hasAnyRole( 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FriendResponseDto>> updateFriend(
            @PathVariable UUID id,
            @RequestBody FriendUpdateDto updateDto) {

        FriendResponseDto updated = friendService.updateFriend(id, updateDto);
        return ResponseEntity.ok(
                ApiResponse.<FriendResponseDto>builder()
                        .status(200)
                        .message("Friend request updated successfully")
                        .data(updated)
                        .build()
        );
    }


}
