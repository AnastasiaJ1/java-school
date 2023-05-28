package com.digdes.school.project;

import com.digdes.school.project.filters.ProjectSearchFilter;
import com.digdes.school.project.input.ProjectDTO;
import com.digdes.school.project.model.Project;
import com.digdes.school.project.enums.ProjectStatus;
import com.digdes.school.project.output.ProjectOutDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    Project create(ProjectDTO projectDTO);

    boolean update(ProjectDTO projectDTO, UUID id);

    boolean delete(UUID id);

    List<ProjectOutDTO> search(ProjectSearchFilter searchFilter);

    ProjectOutDTO get(UUID id);
    int updateStatus(UUID id, ProjectStatus status);
}
