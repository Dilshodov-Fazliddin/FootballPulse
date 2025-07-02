package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.TeamEntity;
import com.pulse.footballpulse.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, UUID> {
    boolean existsByUserIdAndTeamId(UUID memberId, UUID teamId);

    @Query("""
    select tm from TeamMemberEntity tm where tm.team.id = :id
    """)
    List<TeamMemberEntity> findByTeam(@Param("id") UUID teamId);

    Optional<TeamMemberEntity> findByUserIdAndTeamId(UUID userId, UUID teamId);

}
