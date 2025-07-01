package com.pulse.footballpulse.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                .comment(createDto.getComment())
                .user(user)
                .post(post)
                .likes(0)
                 .dislikes(0)
                .isEdited(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
    public CommentDto toCommentDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        dto.setLikes(entity.getLikes());
        dto.setDislikes(entity.getDislikes());
        dto.setIsEdited(entity.getIsEdited());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        if (entity.getPost() != null) {
            dto.setPostId(entity.getPost().getId());
        }

        if (entity.getParentComment() != null) {
            dto.setParentCommentId(entity.getParentComment().getId());
        }

        return dto;
    }
    public ThreadedCommentDto toThreadedCommentDto(CommentEntity entity) {
        ThreadedCommentDto dto = new ThreadedCommentDto();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        dto.setLikes(entity.getLikes());
        dto.setDislikes(entity.getDislikes());
        dto.setIsEdited(entity.getIsEdited());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        return dto;
    }
    public void updateEntityFromDto(CommentEntity entity, UpdateCommentDto updateDto) {
        if (updateDto.getComment() != null && !updateDto.getComment().isBlank()) {
            entity.setComment(updateDto.getComment());
            entity.setUpdatedAt(LocalDateTime.now());
            entity.setIsEdited(true);
        }
    }

    public List<ThreadedCommentDto> buildThreadedCommentTree(List<CommentEntity> rootComments) {
        return rootComments.stream()
                .map(this::buildReplies)
                .collect(Collectors.toList());
    }

    private ThreadedCommentDto buildReplies(CommentEntity entity) {
        ThreadedCommentDto dto = toThreadedCommentDto(entity);

        if (entity.getReplies() != null && !entity.getReplies().isEmpty()) {
            dto.setReplies(entity.getReplies().stream()
                    .map(this::buildReplies)
                    .collect(Collectors.toList()));
        } else {
            dto.setReplies(List.of());
        }

        return dto;
    }
}
