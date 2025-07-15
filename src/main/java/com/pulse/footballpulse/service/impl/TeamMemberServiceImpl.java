package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.TeamInviteDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.domain.response.TeamMemberResponse;
import com.pulse.footballpulse.entity.TeamEntity;
import com.pulse.footballpulse.entity.TeamMemberEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.RoleInTeam;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.mapper.TeamMemberMapper;
import com.pulse.footballpulse.repository.TeamMemberRepository;
import com.pulse.footballpulse.repository.TeamRepository;
import com.pulse.footballpulse.repository.UserRepository;
import com.pulse.footballpulse.service.EmailService;
import com.pulse.footballpulse.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final TeamMemberMapper mapper;

    @Override
    public ResponseEntity<ApiResponse<?>> inviteMember(UUID teamId, TeamInviteDto dto, UserEntity requester) {
        TeamEntity team = getTeamOrThrow(teamId);
        if (!requester.getId().equals(team.getOwnerId())) {
            return response("You are not the team admin", HttpStatus.FORBIDDEN);
        }
        emailService.sendInvitationEmail(dto.getEmail(), requester.getUsername(), teamId);
        return response("Invitation sent", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> showTeamMembers(UUID teamId) {
        List<TeamMemberEntity> members = teamMemberRepository.findByTeam(teamId);
        List<TeamMemberResponse> response = mapper.toResponseList(members);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(response).status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> leaveTeam(UUID teamId, UserEntity member) {
        TeamMemberEntity teamMember = teamMemberRepository
                .findByUserIdAndTeamId(member.getId(), teamId)
                .orElseThrow(() -> new DataNotFoundException("Member not found"));
        teamMemberRepository.delete(teamMember);
        return response("Left the team", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteMember(UUID teamId, UUID memberId, UserEntity requester) {
        TeamEntity team = getTeamOrThrow(teamId);
        if (!requester.getId().equals(team.getOwnerId())) {
            return response("You are not the team admin", HttpStatus.FORBIDDEN);
        }
        TeamMemberEntity member = teamMemberRepository
                .findByUserIdAndTeamId(memberId, teamId)
                .orElseThrow(() -> new DataNotFoundException("Member not found"));
        teamMemberRepository.delete(member);
        return response("Member removed", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> joinTeam(UserEntity user, String inviteToken, UUID teamId) {
        if (!inviteToken.equals(user.getInviteToken())) {
            return response("Invalid invitation token", HttpStatus.BAD_REQUEST);
        }
        if (teamMemberRepository.existsByUserIdAndTeamId(user.getId(), teamId)) {
            return response("Already in team", HttpStatus.CONFLICT);
        }
        TeamEntity team = getTeamOrThrow(teamId);
        user.setInviteToken(null);
        userRepository.save(user);
        teamMemberRepository.save(TeamMemberEntity.builder()
                .team(team)
                .user(user)
                .role(RoleInTeam.PLAYER)
                .build());
        return response("Successfully joined team", HttpStatus.OK);
    }

    private TeamEntity getTeamOrThrow(UUID teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new DataNotFoundException("Team not found"));
    }

    private ResponseEntity<ApiResponse<?>> response(String message, HttpStatus status) {
        return new ResponseEntity<>(ApiResponse.builder()
                .message(message)
                .status(status.value())
                .data(null)
                .build(), status);
    }
}
