package com.pulse.footballpulse.mapper;

import com.pulse.footballpulse.domain.PostCreateDto;
import com.pulse.footballpulse.domain.PostResponseDto;
import com.pulse.footballpulse.domain.PostUpdateDto;
import com.pulse.footballpulse.entity.PostEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostMapper {

    public PostEntity toEntity(PostCreateDto postCreateDto, UserEntity author) {
        return PostEntity.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .author(author)
                .status(PostStatus.TO_REVIEW)
                .imageUrl(postCreateDto.getImageUrl())
                .tags(postCreateDto.getTags())
                .likes(0)
                .dislikes(0)
                .build();
    }

    public PostResponseDto toResponseDto(PostEntity postEntity) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(postEntity.getId());
        dto.setTitle(postEntity.getTitle());
        dto.setContent(postEntity.getContent());
        dto.setAuthorName(postEntity.getAuthor().getFirstName() + " " + postEntity.getAuthor().getLastName());
        dto.setAuthorId(postEntity.getAuthor().getId());
        dto.setStatus(postEntity.getStatus());
        dto.setRejectionReason(postEntity.getRejectionReason());
        dto.setImageUrl(postEntity.getImageUrl());
        dto.setLikes(postEntity.getLikes());
        dto.setDislikes(postEntity.getDislikes());
        dto.setTags(postEntity.getTags());
        dto.setCreatedAt(postEntity.getCreatedAt());
        dto.setUpdatedAt(postEntity.getUpdatedAt());
        return dto;
    }

    public void updateEntityFromDto(PostEntity postEntity, PostUpdateDto postUpdateDto) {
        if (postUpdateDto.getTitle() != null) {
            postEntity.setTitle(postUpdateDto.getTitle());
        }
        if (postUpdateDto.getContent() != null) {
            postEntity.setContent(postUpdateDto.getContent());
        }
        if (postUpdateDto.getImageUrl() != null) {
            postEntity.setImageUrl(postUpdateDto.getImageUrl());
        }
        if (postUpdateDto.getTags() != null) {
            postEntity.setTags(postUpdateDto.getTags());
        }
    }
}
