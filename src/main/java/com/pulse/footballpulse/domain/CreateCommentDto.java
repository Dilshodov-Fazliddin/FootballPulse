package com.pulse.footballpulse.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDto {
    private String content;
    private UUID postId;
    private UUID parentCommentId;

}
