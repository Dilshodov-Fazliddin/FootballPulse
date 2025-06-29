package com.pulse.footballpulse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeamEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
    private UUID ownerId;
    @OneToMany
    private List<TeamMemberEntity>members;
}
