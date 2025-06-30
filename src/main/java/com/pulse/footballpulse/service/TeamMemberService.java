package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.TeamInviteDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public interface TeamMemberService {
    ResponseEntity<ApiResponse<?>> inviteMember(UUID teamId,TeamInviteDto dto,UserEntity requester);
    ResponseEntity<ApiResponse<?>> showTeamMembers(UUID teamId);
    ResponseEntity<ApiResponse<?>> leaveTeam(UUID teamId,UUID memberId);
    ResponseEntity<ApiResponse<?>> deleteMember(UUID teamId, UUID memberId, UserEntity requester);
    ResponseEntity<ApiResponse<?>> joinTeam(UserEntity user,String inviteToken,UUID teamId);
}
