package com.digdes.school.project.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ProjectParticipantsDTO {

    private UUID employeeId;

    private UUID projectId;

    private String role;
}
