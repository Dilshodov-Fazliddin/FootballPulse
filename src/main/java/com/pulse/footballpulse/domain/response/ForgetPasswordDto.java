package com.pulse.footballpulse.domain.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ForgetPasswordDto {
    private String mail;
    private Integer code;
}
