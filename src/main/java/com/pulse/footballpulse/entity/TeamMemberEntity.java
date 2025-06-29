package com.pulse.footballpulse.entity;

import com.pulse.footballpulse.entity.enums.RoleInTeam;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeamMemberEntity extends BaseEntity {
    @ManyToOne
    private TeamEntity team;
    @ManyToOne
    private UserEntity user;
    private RoleInTeam role;
}
