package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.PostCreateDto;
import com.pulse.footballpulse.domain.PostResponseDto;
import com.pulse.footballpulse.domain.PostUpdateDto;
import com.pulse.footballpulse.domain.PostUpdateStatusDto;
import com.pulse.footballpulse.entity.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostService {

    // Author operations
    PostResponseDto createPost(PostCreateDto postCreateDto, UUID authorId);
    PostResponseDto updatePost(UUID postId, PostUpdateDto postUpdateDto, UUID authorId);
    void deletePost(UUID postId, UUID authorId);
    List<PostResponseDto> getMyPosts(UUID authorId);

    // Admin operations
    PostResponseDto updatePostStatus(UUID postId, PostUpdateStatusDto statusDto);
    Page<PostResponseDto> getPostsByStatus(PostStatus status, Pageable pageable);
    Page<PostResponseDto> getAllPostsForReview(Pageable pageable);

    // Public operations
    Page<PostResponseDto> getApprovedPosts(Pageable pageable);
    PostResponseDto getPostById(UUID postId);
    Page<PostResponseDto> searchPosts(String keyword, Pageable pageable);
    Page<PostResponseDto> getPostsByAuthor(UUID authorId, Pageable pageable);

    // Like/Dislike operations
    void likePost(UUID postId, UUID userId);
    void dislikePost(UUID postId, UUID userId);
}
