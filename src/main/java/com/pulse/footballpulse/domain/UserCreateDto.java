package com.pulse.footballpulse.domain;

import com.pulse.footballpulse.entity.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    @NotBlank(message = "your firstname cannot be empty")
    private String firstName;
    @NotBlank(message = "your lastname cannot be empty")
    private String lastName;
    @NotBlank(message = "your birthday cannot be empty")
    private String birthday;
    private Gender gender;
    private String imageUrl;
    private String description;
    @NotBlank(message = "your password cannot be empty")
    private String password;
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Invalid email format"
    )
    private String mail;
    @NotBlank(message = "your username cannot be empty")
    private String username;
}
