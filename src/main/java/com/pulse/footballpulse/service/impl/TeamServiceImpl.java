package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.domain.response.TeamResponse;
import com.pulse.footballpulse.entity.TeamEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.mapper.TeamMapper;
import com.pulse.footballpulse.repository.TeamRepository;
import com.pulse.footballpulse.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper mapper;

    @Override
    public ResponseEntity<ApiResponse<?>> createTeam(String name, UserEntity user) {
        TeamEntity newTeam = mapper.toEntity(name, user);
        teamRepository.save(newTeam);
        return ResponseEntity.ok(ApiResponse.builder().data(null).message("Team created").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> editTeam(UUID id, String name) {
        TeamEntity team = getById(id);
        team.setName(name);
        teamRepository.save(team);
        return ResponseEntity.ok(ApiResponse.builder().message("Team updated").data(null).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteTeam(UUID id) {
        TeamEntity team = getById(id);
        teamRepository.delete(team);
        return ResponseEntity.ok(ApiResponse.builder().message("Team deleted").data(null).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getTeams(int page, int size) {
        Page<TeamEntity> teams = teamRepository.findAll(PageRequest.of(page, size));
        Page<TeamResponse> responsePage = teams.map(mapper::toResponse);
        return ResponseEntity.ok(ApiResponse.builder().data(responsePage).message(null).status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> searchTeam(String name) {
        List<TeamEntity> teams = teamRepository.findByNameContainingIgnoreCase(name);
        List<TeamResponse> response = mapper.toResponseList(teams);
        return ResponseEntity.ok(ApiResponse.builder().data(response).message(null).status(200).build());
    }

    private TeamEntity getById(UUID id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Team not found"));
    }
}
