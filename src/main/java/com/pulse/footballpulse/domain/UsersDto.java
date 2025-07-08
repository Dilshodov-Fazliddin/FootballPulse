package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.UserRoles;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsersDto {
    private String firstName;
    private String lastName;
    private String mail;
    private String birthday;
    private String gender;
    private String username;
    private UserRoles role;
}
