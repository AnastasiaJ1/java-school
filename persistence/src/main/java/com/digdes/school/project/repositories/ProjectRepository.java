package com.digdes.school.project.repositories;

import com.digdes.school.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Project getById(UUID uuid);
}
