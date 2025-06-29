package com.pulse.footballpulse.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CommentEntity extends BaseEntity {
    private String comment;
    private UUID userId;
    private UUID postId;
    private Integer like;
    private Integer dislike;
}
