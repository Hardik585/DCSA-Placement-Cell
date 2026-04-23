package com.hardik.backend.dto;

import com.hardik.backend.model.JobEntity;

import java.util.List;

public record JobResponse<T>(
        List<JobEntity> jobs,
        Integer size,
        Long nextCursor,
        boolean hasNext) {
}
