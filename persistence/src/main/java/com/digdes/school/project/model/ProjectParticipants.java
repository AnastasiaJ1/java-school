package com.digdes.school.project.model;

import com.digdes.school.project.model.enums.Role;
import com.digdes.school.project.model.id.ProjectParticipantsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "project_participants")
@IdClass(ProjectParticipantsId.class)
public class ProjectParticipants {
    @Id
    @Column
    private UUID employeeId;
    @Id
    @Column
    private UUID projectId;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
}
