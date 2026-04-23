package com.hardik.backend.service;

import com.hardik.backend.dto.JobResponse;
import com.hardik.backend.model.JobEntity;

import java.util.List;


public interface JobService {

    JobResponse<JobEntity> fetchNextJobs(Long cursor, Integer size) ;

}
