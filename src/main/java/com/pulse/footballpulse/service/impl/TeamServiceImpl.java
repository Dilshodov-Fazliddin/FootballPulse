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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper mapper;
    @Override
    public ResponseEntity<ApiResponse<?>> createTeam(String name, UserEntity user) {
        TeamEntity newTeam = teamRepository.save(mapper.toEntity(name, user));
        return ResponseEntity.ok(ApiResponse.builder().message("Team created").status(200).data(newTeam).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> editTeam(UUID id, String name) {
        TeamEntity team = getById(id);
        team.setName(name);
        teamRepository.save(team);
        return ResponseEntity.ok(ApiResponse.builder().message("Team updated").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteTeam(UUID id) {
        teamRepository.delete(getById(id));
        return ResponseEntity.ok(ApiResponse.builder().message("Team deleted").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getTeams() {
        List<TeamResponse> response = teamRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.builder().data(response).message(null).status(200).build());
    }

    private TeamEntity getById(UUID id) {
        return teamRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Team not found"));
    }
}
