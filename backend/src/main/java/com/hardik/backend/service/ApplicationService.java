package com.hardik.backend.service;

public interface ApplicationService {

    public String applyToJob(Long studentId, Long jobId);

    public String reviewRequest(Long requestId, Long adminId, boolean approve);
}
