package com.pulse.footballpulse.entity;

import com.pulse.footballpulse.entity.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class PostEntity extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity author;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;
    private String rejectionReason;
    private String imageUrl;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;
    private Integer likes;
    private Integer dislikes;
    @Singular
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags;
}
