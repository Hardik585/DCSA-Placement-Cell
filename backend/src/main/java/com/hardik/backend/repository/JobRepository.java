package com.hardik.backend.repository;

import com.hardik.backend.model.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

    @Query("SELECT j FROM JobEntity j WHERE (:cursor IS NULL OR j.id > :cursor) ORDER BY j.id ASC")
    List<JobEntity> fetchNextPage(@Param("cursor") Long cursor, Pageable pageable);

    @Query("""
                SELECT j FROM JobEntity j
                WHERE (COALESCE(:minCgpa, j.minCgpa) = j.minCgpa OR j.minCgpa <= :minCgpa)
                AND (COALESCE(:course, '') = '' OR LOWER(j.eligibleCourse) LIKE LOWER(CONCAT('%', :course, '%')))
                AND (COALESCE(:keyword, '') = '' OR\s
                LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
        // AND (j.deadline IS NULL OR j.deadline > CURRENT_TIMESTAMP)
    Page<JobEntity> searchJobs(
            Double minCgpa,
            String course,
            String keyword,
//            LocalDateTime deadline,
            Pageable pageable
    );

}
