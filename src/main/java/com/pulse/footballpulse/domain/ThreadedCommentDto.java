package com.pulse.footballpulse.domain;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThreadedCommentDto {
    private UUID id;
    private String comment;
    private UUID userId;
    private Integer likeCount;
    private List<ThreadedCommentDto> replies;

    public static ThreadedCommentDto buildWithReplies(ThreadedCommentDto parent, List<ThreadedCommentDto> replies) {
        ThreadedCommentDto dto = new ThreadedCommentDto();
        dto.setId(parent.getId());
        dto.setComment(parent.getComment());
        dto.setUserId(parent.getUserId());
        dto.setLikeCount(parent.getLikeCount());
        dto.setReplies(replies);
        return dto;
    }
}
