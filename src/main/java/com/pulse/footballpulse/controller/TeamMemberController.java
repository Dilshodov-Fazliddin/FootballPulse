package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.TeamInviteDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/football-pulse/team-member")
@RequiredArgsConstructor
public class TeamMemberController {
    private final TeamMemberService memberService;

    @PostMapping("/invite/{teamId}")
    public ResponseEntity<ApiResponse<?>> inviteMember(
            @RequestBody TeamInviteDto dto,
            @AuthenticationPrincipal UserEntity requester,
            @PathVariable UUID teamId
    ){
        return memberService.inviteMember(teamId, dto, requester);
    }

    @PostMapping("/join-team")
    public ResponseEntity<ApiResponse<?>> joinTeam(
            @RequestParam UUID teamId,
            @RequestParam String inviteToken,
            @AuthenticationPrincipal UserEntity user
    ){
        return memberService.joinTeam(user, inviteToken, teamId);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<ApiResponse<?>> leaveTeam(
            @AuthenticationPrincipal UserEntity member,
            @PathVariable UUID teamId
    ){
        return memberService.leaveTeam(teamId, member);
    }

    @DeleteMapping("/{teamId}/{memberId}")
    public ResponseEntity<ApiResponse<?>> deleteMember(
            @PathVariable UUID teamId,
            @PathVariable UUID memberId,
            @AuthenticationPrincipal UserEntity requester
    ){
        return memberService.deleteMember(teamId, memberId, requester);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<?>> showMembers(
            @PathVariable UUID teamId
    ){
        return memberService.showTeamMembers(teamId);
    }
}
