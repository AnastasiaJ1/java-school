package com.digdes.school.project.input;

import com.digdes.school.project.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "Команда проекта")
public class ProjectParticipantsDTO {
    @Schema(required = true, description = "Участник проекта")
    private UUID employeeId;
    @Schema(required = true, description = "Роль")
    private Role role;
}
