package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.TeamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
    List<TeamEntity> findByNameContainingIgnoreCase(String name);
}
