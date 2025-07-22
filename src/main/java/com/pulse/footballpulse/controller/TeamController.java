package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/football-pulse/teams")
@RequiredArgsConstructor
@Tag(name = "Team api",description = "Teams and endpoints for managing them")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Create new team")
    public ResponseEntity<ApiResponse<?>> createTeam(
            @RequestParam String name,
            @AuthenticationPrincipal UserEntity user
    ){
        return teamService.createTeam(name, user);
    }

    @PutMapping("/{teamId}")
    @PreAuthorize("hasRole('ROLE_CLUB')")
    @Operation(summary = "Update team",description = "Only team owner")
    public ResponseEntity<ApiResponse<?>> updateTeam(
            @RequestParam String newName,
            @PathVariable UUID teamId
    ){
        return teamService.editTeam(teamId,newName);
    }

    @DeleteMapping("/{teamId}")
    @PreAuthorize("hasAnyRole('ROLE_CLUB','ROLE_ADMIN')")
    @Operation(summary = "Delete team",description = "Delete a team. Admins and moderators can also delete a group for any problems or inappropriate chats.")
    public ResponseEntity<ApiResponse<?>> deleteTeam(
            @PathVariable UUID teamId
    ){
        return teamService.deleteTeam(teamId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Show all teams",description = "Only admin")
    public ResponseEntity<ApiResponse<?>> getTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return teamService.getTeams(page,size);
    }

    @GetMapping("/search")
    @Operation(summary = "Search teams by name",description = "Open for anyone")
    public ResponseEntity<ApiResponse<?>> search(
            @RequestParam String name
    ){
        return teamService.searchTeam(name);
    }
}
