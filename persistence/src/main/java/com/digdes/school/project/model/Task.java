package com.digdes.school.project.model;

import com.digdes.school.project.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "executor")
    private UUID executor;
    @Column(name = "labor_costs_hours")
    private Integer laborCostsHours;
    @Column(name = "deadline")
    private Date deadline;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Column(name = "author")
    private UUID author;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "change_date")
    private Date changeDate;
    @Column(name = "project_id")
    private UUID projectId;


}
