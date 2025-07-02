package com.pulse.footballpulse.mapper;

import com.pulse.footballpulse.domain.response.TeamResponse;
import com.pulse.footballpulse.entity.TeamEntity;
import com.pulse.footballpulse.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMapper {
    public TeamEntity toEntity(String name, UserEntity owner){
        return TeamEntity.builder()
                .name(name)
                .ownerId(owner.getId())
                .members(null)
                .build();
    }

    public TeamResponse toResponse(TeamEntity team){
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .memberCount(team.getMembers() != null ? team.getMembers().size() : null)
                .build();
    }

    public List<TeamResponse> toResponseList(List<TeamEntity> teams){
        return teams.stream()
                .map(this::toResponse)
                .toList();
    }
}
