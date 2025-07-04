package com.pulse.footballpulse.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulse.footballpulse.domain.CommentDto;
import com.pulse.footballpulse.domain.CreateCommentDto;
import com.pulse.footballpulse.domain.ThreadedCommentDto;
import com.pulse.footballpulse.domain.UpdateCommentDto;
import com.pulse.footballpulse.service.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football-pulse/comments")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;

    
    
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CommentDto> createComment(@RequestBody CreateCommentDto dto,
                                                    @RequestHeader("X-User-ID") UUID userId) {
        CommentDto created = commentService.createComment(dto, userId);
        return ResponseEntity.created(URI.create("/api/comments/" + created.getId())).body(created);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CommentDto> updateComment(@PathVariable UUID id,
                                                    @RequestBody UpdateCommentDto dto) {
        return ResponseEntity.ok(commentService.updateComment(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable UUID id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<CommentDto>> getCommentsByPost(@PathVariable UUID postId,
                                                              Pageable pageable) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId, pageable));
    }

    @GetMapping("/{id}/thread")
    public ResponseEntity<ThreadedCommentDto> getCommentThread(@PathVariable UUID id) {
        return ResponseEntity.ok(commentService.getCommentThread(id));
    }

    @PostMapping("/{id}/like")
    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CommentDto> likeComment(@PathVariable UUID id,
                                              @RequestHeader("X-User-ID") UUID userId) {
    return ResponseEntity.ok(commentService.likeComment(id, userId));
    }

    @PostMapping("/{id}/dislike")
    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CommentDto> dislikeComment(@PathVariable UUID id,
                                                @RequestHeader("X-User-ID") UUID userId) {
    return ResponseEntity.ok(commentService.dislikeComment(id, userId));
    }

    @PostMapping("/{parentId}/reply")
    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CommentDto> replyToComment(
        @PathVariable UUID parentId,
        @RequestBody CreateCommentDto dto,
        @RequestHeader("X-User-ID") UUID userId) {
    return ResponseEntity.ok(commentService.replyToComment(parentId, dto, userId));
    }
}
