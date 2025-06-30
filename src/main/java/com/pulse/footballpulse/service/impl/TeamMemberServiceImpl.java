package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.TeamInviteDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.TeamEntity;
import com.pulse.footballpulse.entity.TeamMemberEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.RoleInTeam;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.repository.TeamMemberRepository;
import com.pulse.footballpulse.repository.TeamRepository;
import com.pulse.footballpulse.repository.UserRepository;
import com.pulse.footballpulse.service.EmailService;
import com.pulse.footballpulse.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final EmailService emailService;
    @Override
    public ResponseEntity<ApiResponse<?>> inviteMember(UUID teamId, TeamInviteDto dto, UserEntity requester) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new DataNotFoundException("Team not found"));

        if (!team.getOwnerId().equals(requester.getId())){
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("You are not an admin of the team")
                    .status(403)
                    .data(null)
                    .build());
        }

        emailService.sendInvitationEmail(dto.getEmail(), requester.getUsername(), teamId);
        return ResponseEntity.ok(ApiResponse.builder().message("Invitation sent").status(200).data(null).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> showTeamMembers(UUID teamId) {
        List<TeamMemberEntity> members = teamMemberRepository.findByTeam(teamId);
        if (!members.isEmpty()){

        }
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

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> joinTeam(UserEntity user, String inviteToken,UUID teamId) {
        if (!user.getInviteToken().equals(inviteToken)) {
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .message("Invalid url")
                            .status(400)
                            .data(null)
                            .build()
            );
        }
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new DataNotFoundException("Team not found"));

        if (teamMemberRepository.existsByUserIdAndTeamId(user.getId(),teamId)){
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("You already have in team")
                    .data(null)
                    .status(403)
                    .build());
        }
        user.setInviteToken(null);
        userRepository.save(user);
        teamMemberRepository.save(TeamMemberEntity.builder()
                .team(team)
                .user(user)
                .role(RoleInTeam.PLAYER)
                .build());
        return ResponseEntity.ok(ApiResponse.builder().message("Successfully join").status(200).data(null).build());
    }
}
