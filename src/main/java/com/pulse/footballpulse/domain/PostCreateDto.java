package com.pulse.footballpulse.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostCreateDto {
    private String title;
    private String content;
    private String imageUrl;
    private List<String> tags;
}