package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.PostEntity;
import com.pulse.footballpulse.entity.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    Page<PostEntity> findByStatus(PostStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"author"})
    List<PostEntity> findByAuthorId(UUID authorId);

    @EntityGraph(attributePaths = {"author"})
    Page<PostEntity> findByAuthorId(UUID authorId, Pageable pageable);

    @Query("SELECT p FROM PostEntity p WHERE p.status IN ('TO_REVIEW', 'PENDING_REVIEW')")
    Page<PostEntity> findPostsForReview(Pageable pageable);

    @Query("SELECT p FROM PostEntity p WHERE p.status = 'APPROVED' AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<PostEntity> searchApprovedPosts(@Param("keyword") String keyword, Pageable pageable);

    @EntityGraph(attributePaths = {"author"})
    List<PostEntity> findByAuthorIdAndStatus(UUID authorId, PostStatus status);
}
