package com.digdes.school.project.repositories;

import com.digdes.school.project.model.ProjectParticipants;
import com.digdes.school.project.model.id.ProjectParticipantsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProjectParticipantsRepository extends JpaRepository<ProjectParticipants, ProjectParticipantsId> {
    List<ProjectParticipants> getByProjectId(UUID id);
    boolean existsByProjectId(UUID id);
}
