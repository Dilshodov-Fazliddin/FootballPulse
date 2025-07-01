package com.pulse.footballpulse.domain.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeamResponse {
    private UUID id;
    private String name;
    private Integer memberCount;
}
