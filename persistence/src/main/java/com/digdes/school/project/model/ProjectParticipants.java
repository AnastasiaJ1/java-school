package com.digdes.school.project.model;

import com.digdes.school.project.enums.Role;
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
    @Column(name = "employee_id")
    private UUID employeeId;
    @Id
    @Column(name = "project_id")
    private UUID projectId;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(targetEntity = Employee.class)
    @JoinColumn(name = "employee_id", insertable=false, updatable=false, referencedColumnName = "id")
    private Employee employee;
}
