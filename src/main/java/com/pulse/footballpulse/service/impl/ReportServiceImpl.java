package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.ReportDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.entity.ReportEntity;
import com.pulse.footballpulse.mapper.ReportMapper;
import com.pulse.footballpulse.repository.ReportRepository;
import com.pulse.footballpulse.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;


    @Override
    public ResponseEntity<ApiResponse<?>> createReport(ReportDto reportDto) {
        ReportEntity entity = reportMapper.toEntity(reportDto);
        reportRepository.save(entity);
        log.info("Report created successfully {}", entity);
        return ResponseEntity.ok(ApiResponse.builder().data(null).message("Report created").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse<List<ReportDto>>> getReport() {

        List<ReportDto> reports= reportRepository.findAll()
                .stream()
                .map(reportEntity -> new ReportDto(
                        reportEntity.getReasonForReport(),
                        reportEntity.getReporter().getId(),
                        reportEntity.getReportType(),
                        reportEntity.getReported())
                ).toList();
        return ResponseEntity.ok(ApiResponse.<List<ReportDto>>builder().data(reports).status(200).message("All reports").build());
    }


}
