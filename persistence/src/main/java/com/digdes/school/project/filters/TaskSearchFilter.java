package com.digdes.school.project.filters;

import com.digdes.school.project.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskSearchFilter {

    private String name;
    private String description;
    private UUID executor;
    private Integer laborCostsHours;
    private Date deadlineStart;
    private Date deadlineEnd;
    private TaskStatus status;
    private UUID Author;
    private Date creationDateStart;
    private Date creationDateEnd;
    private Date changeDateStart;
    private Date changeDateEnd;
    private UUID projectId;
}
