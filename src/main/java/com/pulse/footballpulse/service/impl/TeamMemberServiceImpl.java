package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.TeamInviteDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.repository.TeamMemberRepository;
import com.pulse.footballpulse.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    @Override
    public ResponseEntity<ApiResponse<?>> inviteMember(UUID teamId, TeamInviteDto dto, UserEntity requester) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> showTeamMembers(UUID teamId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> leaveTeam(UUID teamId, UUID memberId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteMember(UUID teamId, UUID memberId, UserEntity requester) {
        return null;
    }
}
