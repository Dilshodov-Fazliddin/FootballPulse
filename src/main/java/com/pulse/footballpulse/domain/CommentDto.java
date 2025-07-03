package com.pulse.footballpulse.domain;

import java.time.LocalDateTime;
import java.util.List;
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
    private Integer likes;
    private Integer dislikes;
    private Boolean isEdited;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID userId;
    private UUID postId;
    private UUID parentCommentId;
    private String username;
    
   
    
    private List<CommentDto> replies;
}
