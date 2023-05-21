package com.digdes.school.project.input;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskDTO {
    private String name;
    private String description;
    private Long executor;
    private Integer laborCostsHours;
    private Date deadline;
    private String status;
    private Long Author;
    private UUID projectId;
}
