package com.digdes.school.project.repositories;

import com.digdes.school.project.model.ProjectParticipants;
import com.digdes.school.project.model.id.ProjectParticipantsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectParticipantsRepository extends JpaRepository<ProjectParticipants, ProjectParticipantsId> {
    Optional<ProjectParticipants> getByProjectId(UUID id);
}
