package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.PostLikeEntity;
import com.pulse.footballpulse.entity.enums.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, UUID> {

    Optional<PostLikeEntity> findByPostIdAndUserId(UUID postId, UUID userId);

    @Query("SELECT COUNT(pl) FROM PostLikeEntity pl WHERE pl.post.id = :postId AND pl.likeType = :likeType")
    long countByPostIdAndLikeType(@Param("postId") UUID postId, @Param("likeType") LikeType likeType);
}
