package com.pulse.footballpulse.entity;

import jakarta.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;
    @Column(name = "likes_count")
    private Integer like;
    @Column(name = "dislikes_count")
    private Integer dislike;
}
