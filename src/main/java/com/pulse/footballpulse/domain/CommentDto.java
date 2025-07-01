package com.pulse.footballpulse.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private UUID id;
    private String comment;
    private UUID userId;
    private UUID postId;
    private UUID parentCommentId;
    private Integer likeCount;
    private Integer replyCount;
}
