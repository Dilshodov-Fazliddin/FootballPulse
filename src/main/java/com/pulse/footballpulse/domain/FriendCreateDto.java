package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.FriendStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendCreateDto {
    @NotNull(message = "user id can not be null")
    private UUID userId;
    @NotNull(message = "friend id can not be ")
    private UUID friendId;
}
