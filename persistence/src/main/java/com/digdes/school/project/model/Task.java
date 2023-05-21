package com.digdes.school.project.model;

import com.digdes.school.project.model.enums.TaskStatus;
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
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private UUID executor;
    @Column
    private Integer laborCostsHours;
    @Column
    private Date deadline;
    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Column
    private UUID Author;
    @Column
    private Date creationDate;
    @Column
    private Date changeDate;
    @Column
    private UUID projectId;
}
