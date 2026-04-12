package com.hardik.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRequest {
    private Long studentId;
    private Long jobId;
}