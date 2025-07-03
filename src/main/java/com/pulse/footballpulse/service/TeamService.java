package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public interface TeamService {
    ResponseEntity<ApiResponse<?>> createTeam(String name, UserEntity user);
    ResponseEntity<ApiResponse<?>> editTeam(UUID id,String name);
    ResponseEntity<ApiResponse<?>> deleteTeam(UUID id);
    ResponseEntity<ApiResponse<?>> getTeams(int page, int size);
    ResponseEntity<ApiResponse<?>> searchTeam(String name);
}
