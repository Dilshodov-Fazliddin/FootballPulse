package com.pulse.footballpulse.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulse.footballpulse.domain.ReportCommentDto;
import com.pulse.footballpulse.entity.ReportedCommentEntity;
import com.pulse.footballpulse.service.ReportedCommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports/comments")
@RequiredArgsConstructor
public class ReportedCommenController {
    
    private final ReportedCommentService reportService;

    @PreAuthorize("hasAnyRole('USER', 'AUTHOR', 'CLUB', 'ADMIN', 'MODERATOR')")
    @PostMapping
    public ResponseEntity<Void> reportComment(@RequestBody ReportCommentDto dto,
                                              @RequestHeader("X-User-ID") UUID userId) {
        reportService.reportComment(dto, userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/unresolved")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<List<ReportedCommentEntity>> getUnresolvedReports() {
        return ResponseEntity.ok(reportService.getUnresolvedReports());
    }

    @PatchMapping("/{reportId}/resolve")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> resolveReport(@PathVariable UUID reportId) {
        reportService.resolveReport(reportId);
        return ResponseEntity.noContent().build();
    }
}
