package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.ReportType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReportDto {
    @NotBlank(message = "Reason can't be empty")
    private String reasonForReport;
    private UUID reporterId;
    private ReportType reportType;
    private UUID reportedId;
}
