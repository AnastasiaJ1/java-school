package com.digdes.school.project.input;

import com.digdes.school.project.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Задача")
public class TaskDTO {
    @Schema(required = true, description = "Наименование задачи")
    private String name;
    @Schema(description = "Описание задачи")
    private String description;
    @Schema(description = "Исполнитель задачи")
    private UUID executor;
    @Schema(required = true, description = "Трудозатраты")
    private Integer laborCostsHours;
    @Schema(required = true, description = "Крайний срок")
    private Date deadline;
    @Schema(required = true, description = "Статус задачи")
    private TaskStatus status;

}
