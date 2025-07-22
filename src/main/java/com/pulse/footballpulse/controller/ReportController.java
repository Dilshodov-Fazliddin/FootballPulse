package com.pulse.footballpulse.controller;

import com.pulse.footballpulse.domain.ReportDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/football-pulse/reports")
@RequiredArgsConstructor public class ReportController {
    private final ReportService reportService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER','CLUB','AUTHOR')")
    public ResponseEntity<ApiResponse<?>>createReport(@RequestBody ReportDto reportDto){
        return reportService.createReport(reportDto);
    }
    @GetMapping("/get")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ReportDto>>>getAll(){
        return reportService.getReport();
    }

}
