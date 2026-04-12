package com.hardik.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

@Entity
@Table(name = "student_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cgpa;

    private String branch;

    private Integer graduationYear;

    private String resumeUrl;

    private String phone;

    //Owning side
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
