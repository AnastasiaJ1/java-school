package com.digdes.school.project.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectSearchFilter {
    private String code;
    private String name;
    private String description;
    private String status;
}
