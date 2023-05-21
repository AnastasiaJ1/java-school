package com.digdes.school.project.repositories;

import com.digdes.school.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Task getById(UUID uuid);
}
