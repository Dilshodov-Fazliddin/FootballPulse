package com.pulse.footballpulse.event;

import com.pulse.footballpulse.domain.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostStatusChangedEvent {
    private PostResponseDto post;
    private String authorEmail;
    private String authorName;
}