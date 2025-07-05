package com.pulse.footballpulse.domain.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ForgetPasswordDto {
    @Email(message = "mail cannot be empty")
    private String mail;
    @NotBlank(message = "code cannot be empty")
    private Integer code;
}