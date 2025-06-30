package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.PostEntity;
import com.pulse.footballpulse.entity.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    // Find posts by status
    Page<PostEntity> findByStatus(PostStatus status, Pageable pageable);

    // Find posts by author
    List<PostEntity> findByAuthorId(UUID authorId);
    // Find posts by author used with Pageable
    Page<PostEntity> findByAuthorId(UUID authorId, Pageable pageable);

    // Find posts for review (TO_REVIEW and PENDING_REVIEW)
    @Query("SELECT p FROM PostEntity p WHERE p.status IN ('TO_REVIEW', 'PENDING_REVIEW')")
    Page<PostEntity> findPostsForReview(Pageable pageable);

//    // Find approved posts
//    Page<PostEntity> findByStatusOrderByCreatedDateDesc(PostStatus status, Pageable pageable);

    // Search posts by title or content (only approved posts)
    @Query("SELECT p FROM PostEntity p WHERE p.status = 'APPROVED' AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<PostEntity> searchApprovedPosts(@Param("keyword") String keyword, Pageable pageable);

    // Find posts by author and status
    List<PostEntity> findByAuthorIdAndStatus(UUID authorId, PostStatus status);
}
