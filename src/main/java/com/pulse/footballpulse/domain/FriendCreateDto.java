package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.FriendStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FriendCreateDto {
    private UUID userId;

    private UUID friendId;

    private FriendStatus status;

}
