package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.FriendCreateDto;
import com.pulse.footballpulse.domain.response.FriendResponseDto;
import com.pulse.footballpulse.entity.FriendEntity;
import com.pulse.footballpulse.mapper.FriendMapper;
import com.pulse.footballpulse.repository.FriendRepository;
import com.pulse.footballpulse.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {
    private final FriendMapper friendMapper;
private final FriendRepository friendRepository;

    @Override
    public FriendResponseDto addFriend(FriendCreateDto friendCreateDto) {
FriendEntity friendEntity=friendMapper.toFriend(friendCreateDto);
        FriendEntity save = friendRepository.save(friendEntity);
        return friendMapper.toFriendResponse(save);
    }
}
