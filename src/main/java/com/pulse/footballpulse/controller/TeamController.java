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
@Tag(name = "Team api",description = "Guruh va uni boshqarish uchun api endpointlar")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Yangi guruh yaratish")
    public ResponseEntity<ApiResponse<?>> createTeam(
            @RequestParam String name,
            @AuthenticationPrincipal UserEntity user
    ){
        return teamService.createTeam(name, user);
    }

    @PutMapping("/{teamId}")
    @PreAuthorize("hasRole('ROLE_CLUB')")
    @Operation(summary = "Guruhni tahrirlash uchun",description = "Faqat guruh admini guruhni tahrirlay oladi")
    public ResponseEntity<ApiResponse<?>> updateTeam(
            @RequestParam String newName,
            @PathVariable UUID teamId
    ){
        return teamService.editTeam(teamId,newName);
    }

    @DeleteMapping("/{teamId}")
    @PreAuthorize("hasAnyRole('ROLE_CLUB','ROLE_ADMIN','ROLE_MODERATOR')")
    @Operation(summary = "Guruhni o'chirish",description = "Guruhni o'chirish. Biror muammo yoki nojoiz chatlar uchun admin va moderator ham o'chirishi mumkin")
    public ResponseEntity<ApiResponse<?>> deleteTeam(
            @PathVariable UUID teamId
    ){
        return teamService.deleteTeam(teamId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Barcha guruhlar ro'yxatini ko'rish",description = "faqat admin uchun")
    public ResponseEntity<ApiResponse<?>> getTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return teamService.getTeams(page,size);
    }

    @GetMapping("/search")
    @Operation(summary = "Nomi bo'yicha guruhlarni qidirish",description = "hamma uchun ochiq")
    public ResponseEntity<ApiResponse<?>> search(
            @RequestParam String name
    ){
        return teamService.searchTeam(name);
    }
}
