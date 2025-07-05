package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.FriendStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendUpdateDto {
    @NotNull(message = "status can not be null")
    private FriendStatus status;
}
