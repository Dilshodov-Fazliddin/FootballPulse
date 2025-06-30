package com.pulse.footballpulse.domain.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private String message;
    private T data;
    private Integer status;
}
