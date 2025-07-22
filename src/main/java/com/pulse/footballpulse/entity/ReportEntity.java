package com.pulse.footballpulse.entity;

import com.pulse.footballpulse.entity.enums.ReportType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReportEntity extends BaseEntity {
    @Column(nullable = false)
    private String reasonForReport;
    @ManyToOne(cascade = CascadeType.MERGE)
    private UserEntity reporter;
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    @Column(nullable = false)
    private UUID reported;
}
