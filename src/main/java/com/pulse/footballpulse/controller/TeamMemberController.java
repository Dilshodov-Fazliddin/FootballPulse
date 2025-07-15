package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.TeamInviteDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.service.TeamMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/football-pulse/team-member")
@RequiredArgsConstructor
@Tag(name = "Member api",description = "Team members and endpoints for managing them")
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @PostMapping("/invite/{teamId}")
    @PreAuthorize("hasRole('ROLE_CLUB')")
    @Operation(summary = "Invite member",description = "Must be added, must be available on the site and active")

    public ResponseEntity<ApiResponse<?>> inviteMember(
            @Valid @RequestBody TeamInviteDto dto,
            @AuthenticationPrincipal UserEntity requester,
            @PathVariable UUID teamId
    ){
        return teamMemberService.inviteMember(teamId, dto, requester);
    }

    @PostMapping("/join-team")
    @Operation(summary = "Join team",description = "Dynamic URL for email. Not displayed on the site.")
    public ResponseEntity<ApiResponse<?>> joinTeam(
            @RequestParam UUID teamId,
            @RequestParam String inviteToken,
            @AuthenticationPrincipal UserEntity user
    ){
        return teamMemberService.joinTeam(user, inviteToken, teamId);
    }

    @PutMapping("/{teamId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Leave the team",description = "Group admin cannot leave the group")
    public ResponseEntity<ApiResponse<?>> leaveTeam(
            @AuthenticationPrincipal UserEntity member,
            @PathVariable UUID teamId
    ){
        return teamMemberService.leaveTeam(teamId, member);
    }

    @DeleteMapping("/{teamId}/{memberId}")
    @PreAuthorize("hasRole('ROLE_CLUB')")
    @Operation(summary = "Delete member",description = "Only team owner")
    public ResponseEntity<ApiResponse<?>> deleteMember(
            @PathVariable UUID teamId,
            @PathVariable UUID memberId,
            @AuthenticationPrincipal UserEntity requester
    ){
        return teamMemberService.deleteMember(teamId, memberId, requester);
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "Show team members")
    public ResponseEntity<ApiResponse<?>> showMembers(
            @PathVariable UUID teamId
    ){
        return teamMemberService.showTeamMembers(teamId);
    }
}
