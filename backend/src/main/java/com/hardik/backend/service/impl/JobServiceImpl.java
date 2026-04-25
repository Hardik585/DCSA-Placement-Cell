package com.hardik.backend.service;

import com.hardik.backend.dto.JobFilterRequest;
import com.hardik.backend.dto.JobFilterResponse;
import com.hardik.backend.dto.JobResponse;
import com.hardik.backend.mapper.JobMapper;
import com.hardik.backend.model.JobEntity;
import com.hardik.backend.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public JobResponse<JobEntity> fetchNextJobs(Long cursor, Integer size) {

        //default page no =0 and size = 10
        Pageable pagable = PageRequest.of(0, size);

        //fetching the record
        List<JobEntity> jobs = jobRepository.fetchNextPage(cursor, pagable);

        //chekcing data left or not to retrieve
        boolean hasNext = jobs.size() == size;

        //set the nextCursor
        Long nextCursor = hasNext ? jobs.get(jobs.size() - 1).getId() : null;

        return new JobResponse<>(jobs, size, nextCursor, hasNext);

    }

    @Override
    public Page<JobFilterResponse> filterJobs(JobFilterRequest filterRequest, Pageable pageable) {
        Page<JobEntity> pages = jobRepository.searchJobs(filterRequest.getMinCgpa(), filterRequest.getCourse(), filterRequest.getKeyword(),pageable);
        return pages.map(job -> JobMapper.toJobFilterResponse(
                job,
                job.getCompany().getName()
        ));
    }
}
