package com.pulse.footballpulse.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailPostSubmissionDto {
    private String authorEmail;
    private String authorName;
    private String title;
    private String content;
    private List<String> attachmentUrls;
    private List<String> tags;
    private LocalDateTime submissionTime;
    private String emailSubject;
    private String emailBody;
}