package com.digdes.school.project.input;

import com.digdes.school.project.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "Проект")
public class ProjectDTO {
    @Schema(required = true, description = "Код проекта")
    private String code;
    @Schema(required = true, description = "Наименование")
    private String name;
    @Schema(description = "Описание")
    private String description;
}
