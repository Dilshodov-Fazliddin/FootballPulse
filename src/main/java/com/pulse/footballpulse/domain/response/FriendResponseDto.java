package com.pulse.footballpulse.domain.response;

import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.FriendStatus;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Builder
@Getter
@Setter
public class FriendResponseDto {
   private UUID id;

    private UUID userId;

    private UUID friendId;

    private FriendStatus status;
}
