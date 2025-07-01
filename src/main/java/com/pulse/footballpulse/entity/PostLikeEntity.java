package com.pulse.footballpulse.entity;

import com.pulse.footballpulse.entity.enums.LikeType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_likes", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "user_id"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostLikeEntity extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType likeType;
}