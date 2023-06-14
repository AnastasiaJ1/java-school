package com.digdes.school.project.filters;

import com.digdes.school.project.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Схема для поиска задач")
public class TaskSearchFilter {
    @Schema(description = "Наименование")
    private String name;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Исполнитель задачи")
    private UUID executor;
    @Schema(description = "Трудозатраты")
    private Integer laborCostsHours;
    @Schema(description = "Начало интервала дедлайна")
    private Date deadlineStart;
    @Schema(description = "Конец интервала дедлайна")
    private Date deadlineEnd;
    @Schema(description = "Статус")
    private TaskStatus status;
    @Schema(description = "Автор")
    private UUID Author;
    @Schema(description = "Начало интервала даты создания")
    private Date creationDateStart;
    @Schema(description = "Конец интервала даты создания")
    private Date creationDateEnd;
    @Schema(description = "Начало интервала даты изменения")
    private Date changeDateStart;
    @Schema(description = "Конец интервала даты создания")
    private Date changeDateEnd;
    @Schema(description = "Идентификатор проекта")
    private UUID projectId;
}
