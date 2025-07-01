package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.PostCreateDto;
import com.pulse.footballpulse.domain.PostResponseDto;
import com.pulse.footballpulse.domain.PostUpdateDto;
import com.pulse.footballpulse.domain.PostUpdateStatusDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.enums.PostStatus;
import com.pulse.footballpulse.service.PostService;
import com.pulse.footballpulse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/football-pulse/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    // Public endpoints (accessible by all users including guests)

    @GetMapping("/approved")
    public ResponseEntity<ApiResponse<Page<PostResponseDto>>> getApprovedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.getApprovedPosts(pageable);

        return ResponseEntity.ok(ApiResponse.<Page<PostResponseDto>>builder()
                .status(200)
                .message("Approved posts retrieved successfully")
                .data(posts)
                .build());
    }
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPostById(@PathVariable UUID postId) {
        PostResponseDto post = postService.getPostById(postId);

        return ResponseEntity.ok(ApiResponse.<PostResponseDto>builder()
                .status(200)
                .message("Post retrieved successfully")
                .data(post)
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PostResponseDto>>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.searchPosts(keyword, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<PostResponseDto>>builder()
                .status(200)
                .message("Search results retrieved successfully")
                .data(posts)
                .build());
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<ApiResponse<Page<PostResponseDto>>> getPostsByAuthor(
            @PathVariable UUID authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.getPostsByAuthor(authorId, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<PostResponseDto>>builder()
                .status(200)
                .message("Author posts retrieved successfully")
                .data(posts)
                .build());
    }

    // User endpoints (like/dislike functionality)

    @PostMapping("/{postId}/like")
    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<ApiResponse<String>> likePost(@PathVariable UUID postId) {
        UUID userId = userService.getCurrentUserId();
        postService.likePost(postId, userId);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(200)
                .message("Post liked successfully")
                .build());
    }

    @PostMapping("/{postId}/dislike")
    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<ApiResponse<String>> dislikePost(@PathVariable UUID postId) {
        UUID userId = userService.getCurrentUserId();
        postService.dislikePost(postId, userId);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(200)
                .message("Post disliked successfully")
                .build());
    }

    // Author endpoints (create, update, delete posts)

    @PostMapping
    @PreAuthorize("hasAnyRole('AUTHOR', 'CLUB', 'ADMIN')")
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(@RequestBody PostCreateDto postCreateDto) {
        UUID authorId = userService.getCurrentUserId();
        PostResponseDto post = postService.createPost(postCreateDto, authorId);

        return ResponseEntity.ok(ApiResponse.<PostResponseDto>builder()
                .status(201)
                .message("Post created successfully and sent for review")
                .data(post)
                .build());
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasAnyRole('AUTHOR', 'CLUB', 'ADMIN')")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(
            @PathVariable UUID postId,
            @RequestBody PostUpdateDto postUpdateDto) {

        UUID authorId = userService.getCurrentUserId();
        PostResponseDto post = postService.updatePost(postId, postUpdateDto, authorId);

        return ResponseEntity.ok(ApiResponse.<PostResponseDto>builder()
                .status(200)
                .message("Post updated successfully")
                .data(post)
                .build());
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('AUTHOR', 'CLUB', 'ADMIN')")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable UUID postId) {
        UUID authorId = userService.getCurrentUserId();
        postService.deletePost(postId, authorId);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(200)
                .message("Post deleted successfully")
                .build());
    }

    @GetMapping("/my-posts")
    @PreAuthorize("hasAnyRole('AUTHOR', 'CLUB', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getMyPosts() {
        UUID authorId = userService.getCurrentUserId();
        List<PostResponseDto> posts = postService.getMyPosts(authorId);

        return ResponseEntity.ok(ApiResponse.<List<PostResponseDto>>builder()
                .status(200)
                .message("Your posts retrieved successfully")
                .data(posts)
                .build());
    }

    // Admin endpoints (moderation functionality)

    @PutMapping("/{postId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePostStatus(
            @PathVariable UUID postId,
            @RequestBody PostUpdateStatusDto statusDto) {

        PostResponseDto post = postService.updatePostStatus(postId, statusDto);

        return ResponseEntity.ok(ApiResponse.<PostResponseDto>builder()
                .status(200)
                .message("Post status updated successfully")
                .data(post)
                .build());
    }

    @GetMapping("/review")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PostResponseDto>>> getPostsForReview(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.getAllPostsForReview(pageable);

        return ResponseEntity.ok(ApiResponse.<Page<PostResponseDto>>builder()
                .status(200)
                .message("Posts for review retrieved successfully")
                .data(posts)
                .build());
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PostResponseDto>>> getPostsByStatus(
            @PathVariable PostStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.getPostsByStatus(status, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<PostResponseDto>>builder()
                .status(200)
                .message("Posts by status retrieved successfully")
                .data(posts)
                .build());
    }
}