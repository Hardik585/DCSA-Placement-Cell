package com.hardik.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CompanyResponseDto {

    private Long cId;
    private String name;
    private String email;
    private String description;
    private String website;

}
