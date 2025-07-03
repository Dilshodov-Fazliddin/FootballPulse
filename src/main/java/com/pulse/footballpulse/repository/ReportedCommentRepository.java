package com.pulse.footballpulse.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pulse.footballpulse.entity.ReportedCommentEntity;

@Repository
public interface ReportedCommentRepository extends JpaRepository<ReportedCommentEntity,UUID> {
    List<ReportedCommentEntity> findByResolveFalse();
}
