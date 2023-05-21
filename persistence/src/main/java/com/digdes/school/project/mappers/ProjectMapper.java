package com.digdes.school.project.mappers;

import com.digdes.school.project.input.ProjectDTO;
import com.digdes.school.project.model.Project;
import com.digdes.school.project.model.enums.ProjectStatus;
import com.digdes.school.project.output.ProjectOutDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectMapper {
    private final ModelMapper modelMapper;

    public ProjectMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Project convertToEntity(ProjectDTO projectDTO) {
        Project project = modelMapper.map(projectDTO, Project.class);
        project.setId(UUID.randomUUID());
        project.setStatus(ProjectStatus.DRAFT);
        return project;
    }

    public ProjectOutDTO convertToDTO(Project project) {
        return modelMapper.map(project, ProjectOutDTO.class);
    }

}
