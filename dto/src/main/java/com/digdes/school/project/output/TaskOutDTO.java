package com.digdes.school.project.output;

import java.util.Date;
import java.util.UUID;

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
