package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, UUID> {
}
