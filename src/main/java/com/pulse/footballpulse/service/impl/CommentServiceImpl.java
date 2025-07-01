package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.*;
import com.pulse.footballpulse.entity.CommentEntity;
import com.pulse.footballpulse.entity.PostEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.mapper.CommentMapper;
import com.pulse.footballpulse.repository.CommentRepository;
import com.pulse.footballpulse.repository.PostRepository;
import com.pulse.footballpulse.repository.UserRepository;
import com.pulse.footballpulse.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(CreateCommentDto createDto, UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
                
        PostEntity post = postRepository.findById(createDto.getPostId())
                .orElseThrow(() -> new DataNotFoundException("Post not found"));

        CommentEntity comment = commentMapper.toEntity(createDto, user, post);
        CommentEntity savedComment = commentRepository.save(comment);
        
        return commentMapper.toCommentDto(savedComment);
    }

    @Override
    public CommentDto updateComment(UUID commentId, UpdateCommentDto updateDto) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("Comment Not Found"));

        commentMapper.updateEntityFromDto(comment, updateDto);
        CommentEntity updated = commentRepository.save(comment);
        
        return commentMapper.toCommentDto(updated);
    }

    @Override
    public void deleteComment(UUID commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("Comment Not Found"));

        commentRepository.delete(comment);
    }

    @Override
    public CommentDto getCommentById(UUID commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper::toCommentDto)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    public Page<CommentDto> getCommentsByPost(UUID postId, Pageable pageable) {
         List<CommentEntity> allComments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
        int start = Math.min((int) pageable.getOffset(), allComments.size());
        int end = Math.min(start + pageable.getPageSize(), allComments.size());

        List<CommentDto> content = allComments.subList(start, end).stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, allComments.size());
    }

    @Override
    public ThreadedCommentDto getCommentThread(UUID rootCommentId) {
        CommentEntity root = commentRepository.findById(rootCommentId)
                .orElseThrow(() -> new RuntimeException("Root comment not found"));

        return buildThreadedComment(root);
    }

    private ThreadedCommentDto buildThreadedComment(CommentEntity entity) {
        ThreadedCommentDto dto = commentMapper.toThreadedCommentDto(entity);

        List<CommentEntity> replies = commentRepository.findByParentCommentIdOrderByCreatedAtAsc(entity.getId());

        if (replies != null && !replies.isEmpty()) {
            dto.setReplies(replies.stream()
                    .map(this::buildThreadedComment)
                    .collect(Collectors.toList()));
        } else {
            dto.setReplies(new ArrayList<>());
        }

        return dto;
    }
}