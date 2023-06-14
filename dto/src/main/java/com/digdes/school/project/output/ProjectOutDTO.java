package com.digdes.school.project.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProjectOutDTO {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private String status;
}
