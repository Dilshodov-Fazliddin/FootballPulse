package com.pulse.footballpulse.mapper;

import com.pulse.footballpulse.domain.ReportDto;
import com.pulse.footballpulse.entity.ReportEntity;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportMapper {
    private final UserRepository userRepository;
    public ReportEntity toEntity(ReportDto reportDto) {
        UserEntity user = userRepository.findById(reportDto.getReporterId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        return ReportEntity.builder()
                .reasonForReport(reportDto.getReasonForReport())
                .reported(reportDto.getReportedId())
                .reporter(user)
                .reportType(reportDto.getReportType())
                .build();
    }
}
