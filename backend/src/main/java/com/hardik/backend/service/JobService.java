package com.hardik.backend.service;

import com.hardik.backend.dto.JobFilterRequest;
import com.hardik.backend.dto.JobFilterResponse;
import com.hardik.backend.dto.JobResponse;
import com.hardik.backend.model.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface JobService {

    JobResponse<JobEntity> fetchNextJobs(Long cursor, Integer size) ;

    Page<JobFilterResponse> filterJobs(JobFilterRequest filterRequest, Pageable pageable);
}
