package com.pulse.footballpulse.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulse.footballpulse.domain.ReportCommentDto;
import com.pulse.footballpulse.entity.CommentEntity;
import com.pulse.footballpulse.entity.ReportedCommentEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.repository.CommentRepository;
import com.pulse.footballpulse.repository.ReportedCommentRepository;
import com.pulse.footballpulse.repository.UserRepository;
import com.pulse.footballpulse.service.ReportedCommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportedCommentServiceImpl implements ReportedCommentService {

    private final ReportedCommentRepository reportRepo;
    private final UserRepository userRepo;
    private final CommentRepository commentRepo;

    @Override
    public void reportComment(ReportCommentDto dto, UUID userID) {
        UserEntity user = userRepo.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommentEntity comment = commentRepo.findById(dto.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        ReportedCommentEntity report = new ReportedCommentEntity();
        report.setComment(comment);
        report.setReporter(user);
        report.setReason(dto.getReason());
        report.setResolved(false);

        reportRepo.save(report);
    }

    @Override
    public List<ReportedCommentEntity> getUnresolvedReports() {
        return reportRepo.findByResolvedFalse();
    }

    @Override
    public void resolveReport(UUID reportId) {
        ReportedCommentEntity report = reportRepo.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setResolved(true);
        reportRepo.save(report);
    }
    
}
