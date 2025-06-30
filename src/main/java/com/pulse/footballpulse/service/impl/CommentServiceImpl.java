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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final ApplicationEventPublisher eventPublisher;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommentById'");
    }

    @Override
    public Page<CommentDto> getCommentsByPost(UUID postId, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommentsByPost'");
    }

    @Override
    public ThreadedCommentDto getCommentThread(UUID rootCommentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommentThread'");
    }
}