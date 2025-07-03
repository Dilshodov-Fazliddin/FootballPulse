package com.pulse.footballpulse.mapper;

import com.pulse.footballpulse.domain.response.TeamMemberResponse;
import com.pulse.footballpulse.entity.TeamMemberEntity;
import com.pulse.footballpulse.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberMapper {

    public TeamMemberResponse toResponse(TeamMemberEntity member){
        return TeamMemberResponse.builder()
                .id(member.getId())
                .firstname(member.getUser().getFirstName())
                .lastname(member.getUser().getLastName())
                .imageUrl(member.getUser().getImageUrl())
                .roleInTeam(member.getRole().name())
                .email(member.getUser().getMail())
                .birthDate(member.getUser().getBirthday())
                .username(member.getUser().getUsername())
                .build();
    }

    public List<TeamMemberResponse> toResponseList(List<TeamMemberEntity> members){
        return members.stream()
                .map(this::toResponse)
                .toList();
    }
}
