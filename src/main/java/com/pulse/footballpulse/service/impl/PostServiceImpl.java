package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.PostCreateDto;
import com.pulse.footballpulse.domain.PostResponseDto;
import com.pulse.footballpulse.domain.PostUpdateDto;
import com.pulse.footballpulse.domain.PostUpdateStatusDto;
import com.pulse.footballpulse.entity.PostEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.PostStatus;
import com.pulse.footballpulse.entity.enums.UserRoles;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.exception.ForbiddenException;
import com.pulse.footballpulse.mapper.PostMapper;
import com.pulse.footballpulse.repository.PostRepository;
import com.pulse.footballpulse.repository.UserRepository;
import com.pulse.footballpulse.event.PostStatusChangedEvent;
import com.pulse.footballpulse.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public PostResponseDto createPost(PostCreateDto postCreateDto, UUID authorId) {
        UserEntity author = userRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author not found"));

        // Check if the user has permission to create posts (AUTHOR, CLUB, ADMIN)
        if (!hasAuthorPermission(author.getRole())) {
            throw new ForbiddenException("User does not have permission to create posts");
        }

        PostEntity postEntity = postMapper.toEntity(postCreateDto, author);
        PostEntity savedPost = postRepository.save(postEntity);

        return postMapper.toResponseDto(savedPost);
    }

    @Override
    public PostResponseDto updatePost(UUID postId, PostUpdateDto postUpdateDto, UUID authorId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));

        UserEntity author = userRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author not found"));

        // Check if user is the author of the post or has admin privileges
        if (!postEntity.getAuthor().getId().equals(authorId) && !author.getRole().equals(UserRoles.ROLE_ADMIN)) {
            throw new ForbiddenException("User does not have permission to update this post");
        }

        postMapper.updateEntityFromDto(postEntity, postUpdateDto);
        PostEntity updatedPost = postRepository.save(postEntity);

        return postMapper.toResponseDto(updatedPost);
    }

    @Override
    public void deletePost(UUID postId, UUID authorId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));

        UserEntity author = userRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author not found"));

        // Check if user is the author of the post or has admin privileges
        if (!postEntity.getAuthor().getId().equals(authorId) && !author.getRole().equals(UserRoles.ROLE_ADMIN)) {
            throw new ForbiddenException("User does not have permission to delete this post");
        }

        postRepository.delete(postEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getMyPosts(UUID authorId) {
        List<PostEntity> posts = postRepository.findByAuthorId(authorId);
        return posts.stream()
                .map(postMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto updatePostStatus(UUID postId, PostUpdateStatusDto statusDto) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));

        postEntity.setStatus(statusDto.getStatus());

        if (statusDto.getStatus() == PostStatus.REJECTED && statusDto.getRejectionReason() != null) {
            postEntity.setRejectionReason(statusDto.getRejectionReason());
        }

        PostEntity updatedPost = postRepository.save(postEntity);

        // Publish event for email notification
        try {
            String authorName = updatedPost.getAuthor().getFirstName() + " " + updatedPost.getAuthor().getLastName();
            PostResponseDto responseDto = postMapper.toResponseDto(updatedPost);

            PostStatusChangedEvent event = new PostStatusChangedEvent(
                responseDto,
                updatedPost.getAuthor().getMail(),
                authorName
            );

            eventPublisher.publishEvent(event);
        } catch (Exception e) {
            // Log error but don't fail the status update
            System.err.println("Failed to send email notification: " + e.getMessage());
        }

        return postMapper.toResponseDto(updatedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostsByStatus(PostStatus status, Pageable pageable) {
        Page<PostEntity> posts = postRepository.findByStatus(status, pageable);
        return posts.map(postMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPostsForReview(Pageable pageable) {
        Page<PostEntity> posts = postRepository.findPostsForReview(pageable);
        return posts.map(postMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getApprovedPosts(Pageable pageable) {
        Page<PostEntity> posts = postRepository.findByStatus(PostStatus.APPROVED, pageable);
        return posts.map(postMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(UUID postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));

        return postMapper.toResponseDto(postEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> searchPosts(String keyword, Pageable pageable) {
        Page<PostEntity> posts = postRepository.searchApprovedPosts(keyword, pageable);
        return posts.map(postMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostsByAuthor(UUID authorId, Pageable pageable) {
        Page<PostEntity> posts = postRepository.findByAuthorId(authorId, pageable);
        return posts.map(postMapper::toResponseDto);
    }

    @Override
    public void likePost(UUID postId, UUID userId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));

        // TODO: Implement like logic with user tracking to prevent duplicate likes
        postEntity.setLikes(postEntity.getLikes() + 1);
        postRepository.save(postEntity);
    }

    @Override
    public void dislikePost(UUID postId, UUID userId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));

        // TODO: Implement dislike logic with user tracking to prevent duplicate dislikes
        postEntity.setDislikes(postEntity.getDislikes() + 1);
        postRepository.save(postEntity);
    }

    private boolean hasAuthorPermission(UserRoles role) {
        return role == UserRoles.ROLE_AUTHOR || 
               role == UserRoles.ROLE_CLUB || 
               role == UserRoles.ROLE_ADMIN;
    }
}
