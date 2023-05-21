package com.digdes.school.project;

import com.digdes.school.project.filters.EmployeeSearchFilter;
import com.digdes.school.project.filters.ProjectSearchFilter;
import com.digdes.school.project.input.ProjectDTO;
import com.digdes.school.project.model.Project;
import com.digdes.school.project.output.ProjectOutDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    Project save(ProjectDTO projectDTO);

    boolean change(Project project);

    boolean delete(UUID id);

    List<Project> search(ProjectSearchFilter searchFilter);

    ProjectOutDTO get(UUID id);
}
