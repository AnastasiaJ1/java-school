package com.digdes.school.project.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private String code;
    private String name;
    private String description;
    private String status;
}
