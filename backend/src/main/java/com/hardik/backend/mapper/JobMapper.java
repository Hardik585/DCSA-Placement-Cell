package com.hardik.backend.mapper;

import com.hardik.backend.dto.JobFilterResponse;
import com.hardik.backend.model.JobEntity;

public class JobMapper {

    public static JobFilterResponse toJobFilterResponse(JobEntity job, String companyName) {
        return JobFilterResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .packageAmount(job.getPackageAmount())
                .minCgpa(job.getMinCgpa())
                .maxBacklog(job.getMaxBacklogs())
                .eligibleCourse(job.getEligibleCourse())
                .deadline(job.getDeadline())
                .companyName(companyName)
                .build();
    }
}
