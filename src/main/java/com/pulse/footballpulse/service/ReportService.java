package com.pulse.footballpulse.service;

import com.pulse.footballpulse.domain.ReportDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReportService {
    ResponseEntity<ApiResponse<?>> createReport(ReportDto reportDto);
    ResponseEntity<ApiResponse<List<ReportDto>>> getReport();
}
