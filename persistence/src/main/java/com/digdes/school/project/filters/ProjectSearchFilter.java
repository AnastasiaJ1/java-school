package com.digdes.school.project.filters;

import com.digdes.school.project.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Схема для поиска проектов")
public class ProjectSearchFilter {
    @Schema(description = "Код")
    private String code;
    @Schema(description = "Наименование")
    private String name;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Статус")
    private ProjectStatus status;
}
