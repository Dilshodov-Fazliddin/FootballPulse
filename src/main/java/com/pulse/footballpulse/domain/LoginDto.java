package com.pulse.footballpulse.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    @Email(message = "mail cannot be empty")
    private String mail;
    @NotBlank(message = "password cannot be empty")
    private String password;

}
