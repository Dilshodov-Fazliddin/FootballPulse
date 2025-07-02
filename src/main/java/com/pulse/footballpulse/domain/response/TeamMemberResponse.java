package com.pulse.footballpulse.domain.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamMemberResponse {
    private UUID id;
    private String email;
    private String username;
    private String imageUrl;
    private String firstname;
    private String lastname;
    private String birthDate;
    private String roleInTeam;
}
