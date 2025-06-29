package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String birthday;
    private Gender gender;
    private String imageUrl;
    private String description;
    private String password;
    private String mail;
    private String username;
}
