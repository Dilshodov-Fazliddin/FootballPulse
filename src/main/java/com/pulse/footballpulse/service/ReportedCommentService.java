package com.pulse.footballpulse.service;

import java.util.List;
import java.util.UUID;

import com.pulse.footballpulse.domain.ReportCommentDto;
import com.pulse.footballpulse.entity.ReportedCommentEntity;

public interface ReportedCommentService {
    void reportComment(ReportCommentDto dto, UUID userID);
    List<ReportedCommentEntity> getUnresolvedReports();
    void resolveReport(UUID reportId);
}
