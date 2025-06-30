package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.PostStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponseDto {
    private UUID id;
    private String title;
    private String content;
    private String authorName;
    private UUID authorId;
    private PostStatus status;
    private String rejectionReason;
    private String imageUrl;
    private Integer likes;
    private Integer dislikes;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
