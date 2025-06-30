package com.pulse.footballpulse.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pulse.footballpulse.domain.CommentDto;
import com.pulse.footballpulse.domain.CreateCommentDto;
import com.pulse.footballpulse.domain.ThreadedCommentDto;
import com.pulse.footballpulse.domain.UpdateCommentDto;

public interface CommentService {
    CommentDto createComment(CreateCommentDto createDto, UUID userId);
    CommentDto updateComment(UUID commentId, UpdateCommentDto updateDto);
    void deleteComment(UUID commentId);
    CommentDto getCommentById(UUID commentId);
    Page<CommentDto> getCommentsByPost(UUID postId, Pageable pageable);
    ThreadedCommentDto getCommentThread(UUID rootCommentId);
}
