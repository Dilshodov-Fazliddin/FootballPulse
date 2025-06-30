package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.PostStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostUpdateStatusDto {
    private PostStatus status;
    private String rejectionReason;
}