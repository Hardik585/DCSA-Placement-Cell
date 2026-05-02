package com.hardik.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String description;

    private String website;

    //Admin who created
    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    //One-to-Many Jobs
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<JobEntity> jobs;
}