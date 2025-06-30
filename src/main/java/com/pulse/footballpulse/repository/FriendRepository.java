package com.pulse.footballpulse.repository;

import com.pulse.footballpulse.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendRepository extends JpaRepository<FriendEntity, UUID> {
}
