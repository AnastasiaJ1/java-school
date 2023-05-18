package model;

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
public class Task {
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
