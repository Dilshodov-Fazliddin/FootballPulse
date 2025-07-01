package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/football-pulse/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTeam(
            @RequestParam String name,
            @AuthenticationPrincipal UserEntity user
    ){
        return teamService.createTeam(name, user);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<ApiResponse<?>> updateTeam(
            @RequestParam String newName,
            @PathVariable UUID teamId
    ){
        return teamService.editTeam(teamId,newName);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<ApiResponse<?>> deleteTeam(
            @PathVariable UUID teamId
    ){
        return teamService.deleteTeam(teamId);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTeams(){
        return teamService.getTeams();
    }
}
