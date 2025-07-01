package com.pulse.footballpulse.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pulse.footballpulse.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,UUID> {
    
    //Find comments by postID using Pageable
    Page<CommentEntity> findByPostID(UUID postID, Pageable pageable);
    //Find comment by post
    List<CommentEntity> findByPostID(UUID postID);

    //Find comments by user
    List<CommentEntity> findByUserID(UUID userID);

    //Find comments by userID using Pageable
    Page<CommentEntity> findByUserID(UUID userID, Pageable pageable);

    @Query("SELECT c FROM CommentEntity c WHERE c.post.id = :postId AND c.parentComment IS NULL")
    Page<CommentEntity> findRootCommentByPost(@Param("postId") UUID postId, Pageable pageable);

    @Query("SELECT c FROM CommentEntity c WHERE c.parentComment.id = :parentId ORDER BY c.createdAt ASC")
    List<CommentEntity> findReplies(@Param("parentId") UUID parentId);

    @Query("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment.id = :parentId")
    int countReplies(@Param("parentId") UUID parentId);

    List<CommentEntity> findByPostIdOrderByCreatedAtAsc(UUID postId);
    List<CommentEntity> findByParentCommentIdOrderByCreatedAtAsc(UUID parentId);
}
