package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.FriendCreateDto;
import com.pulse.footballpulse.domain.response.FriendResponseDto;

public interface FriendService {
    FriendResponseDto addFriend(FriendCreateDto friendCreateDto);



}
