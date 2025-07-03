package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.FriendEntity;
import com.pulse.footballpulse.entity.enums.FriendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface FriendRepository extends JpaRepository<FriendEntity, UUID>, JpaSpecificationExecutor<FriendEntity> {
    Page<FriendEntity> findAllByUserId(UUID userId, Pageable pageable);



    @Query("select fr from FriendEntity fr where fr.status=:status and fr.user.id=:userId")
    Page<FriendEntity> findByStatus(@Param("userId") UUID userId,@Param("status") FriendStatus status, Pageable pageable);
}
