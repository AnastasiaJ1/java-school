package com.digdes.school.project.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TaskOutDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID executor;
    private Integer laborCostsHours;
    private Date deadline;
    private String status;
    private UUID Author;
    private Date creationDate;
    private Date changeDate;
}
