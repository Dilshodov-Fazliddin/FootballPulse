package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.FriendCreateDto;
import com.pulse.footballpulse.domain.FriendUpdateDto;
import com.pulse.footballpulse.domain.response.FriendResponseDto;
import com.pulse.footballpulse.entity.enums.FriendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FriendService {
    FriendResponseDto addFriend(FriendCreateDto friendCreateDto);
    Page<FriendResponseDto> getFriends(Pageable pageable, UUID userId,UUID friendId,FriendStatus friendStatus);

    Page<FriendResponseDto> getByStatus(UUID userId, FriendStatus status, Pageable pageable);

    FriendResponseDto acceptFriend(UUID id);

    FriendResponseDto rejectFriend(UUID id);

    void deleteById(UUID id);

    FriendResponseDto updateFriend(UUID id, FriendUpdateDto updateDto);
}
