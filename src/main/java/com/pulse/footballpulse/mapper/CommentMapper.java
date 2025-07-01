package com.pulse.footballpulse.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pulse.footballpulse.domain.CommentDto;
import com.pulse.footballpulse.domain.CreateCommentDto;
import com.pulse.footballpulse.domain.ThreadedCommentDto;
import com.pulse.footballpulse.domain.UpdateCommentDto;
import com.pulse.footballpulse.entity.CommentEntity;
import com.pulse.footballpulse.entity.PostEntity;
import com.pulse.footballpulse.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentMapper {
    public CommentEntity toEntity(CreateCommentDto createDto, UserEntity user, PostEntity post) 
    {
        return CommentEntity.builder()
                .comment(createDto.getContent())
                .user(user)
                .post(post)
                .like(0)
                .build();
    }
    public CommentDto toCommentDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        
        if (entity.getPost() != null) {
            dto.setPostId(entity.getPost().getId());
        }
        
        dto.setParentCommentId(entity.getParentComment() != null ? 
            entity.getParentComment().getId() : null);
        dto.setLikeCount(entity.getLike());
        dto.setReplyCount(entity.getReplies() != null ? entity.getReplies().size() : 0);
        
        return dto;
    }
    public ThreadedCommentDto toThreadedCommentDto(CommentEntity entity) {
        ThreadedCommentDto dto = new ThreadedCommentDto();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        
        dto.setLikeCount(entity.getLike());
        
        // Replies are populated separately in the service layer
        dto.setReplies(null); 
        
        return dto;
    }
    public void updateEntityFromDto(CommentEntity entity, UpdateCommentDto updateDto) {
        if (updateDto.getComment() != null && !updateDto.getComment().isBlank()) {
            entity.setComment(updateDto.getComment());
            entity.setUpdatedAt(LocalDateTime.now());
        }
    }
}
