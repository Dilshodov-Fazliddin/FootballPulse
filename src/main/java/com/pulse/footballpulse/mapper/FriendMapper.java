package com.pulse.footballpulse.mapper;

import com.pulse.footballpulse.domain.FriendCreateDto;
import com.pulse.footballpulse.domain.response.FriendResponseDto;
import com.pulse.footballpulse.entity.FriendEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.FriendStatus;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendMapper {
    private final UserRepository userRepository;

    public FriendEntity toFriend(FriendCreateDto friendCreateDto) {
        UserEntity user = userRepository.findById(friendCreateDto.getUserId()).orElseThrow(() -> new DataNotFoundException("User Id not found with the id" + friendCreateDto.getUserId()));
        UserEntity friend = userRepository.findById(friendCreateDto.getUserId()).orElseThrow(() -> new DataNotFoundException("Friend Id not found with the id" + friendCreateDto.getFriendId()));
        return FriendEntity.builder()
                .user(user)
                .friend(friend)
                .status(FriendStatus.PENDING)
                .build();
    }

    public FriendResponseDto toFriendResponse(FriendEntity friendEntity) {
        return FriendResponseDto.builder()
                .friendId(friendEntity.getId())
                .userId(friendEntity.getUser().getId())
                .status(friendEntity.getStatus())
                .id(friendEntity.getId())
                .build();

    }
}
