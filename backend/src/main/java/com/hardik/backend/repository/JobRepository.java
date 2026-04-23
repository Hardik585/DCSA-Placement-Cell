package com.hardik.backend.repository;

import com.hardik.backend.model.JobEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

    @Query("SELECT j FROM JobEntity j WHERE (:cursor IS NULL OR j.id > :cursor) ORDER BY j.id ASC")
    List<JobEntity> fetchNextPage(@Param("cursor") Long cursor, Pageable pageable);

}
