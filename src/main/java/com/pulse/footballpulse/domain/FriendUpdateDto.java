package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.FriendStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendUpdateDto {
    private FriendStatus status;
}
