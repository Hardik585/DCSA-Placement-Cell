package com.hardik.backend.service;

import com.hardik.backend.dto.JobResponse;
import com.hardik.backend.model.JobEntity;
import com.hardik.backend.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
