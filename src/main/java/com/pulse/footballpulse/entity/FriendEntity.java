package com.pulse.footballpulse.entity;

import com.pulse.footballpulse.entity.enums.FriendStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class FriendEntity extends BaseEntity {
    private UUID user;
    private UUID friend;
    private FriendStatus status;
}
