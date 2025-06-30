package com.pulse.footballpulse.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeamInviteDto {
    @NotBlank(message = "Email not must be blank")
    @Email(message = "Invalid email")
    private String email;
}
