package com.pulse.footballpulse.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    private String mail;
    private String password;

}
