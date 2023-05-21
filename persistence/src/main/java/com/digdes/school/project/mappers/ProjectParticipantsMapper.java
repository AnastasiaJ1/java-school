package com.digdes.school.project.mappers;

import com.digdes.school.project.input.ProjectParticipantsDTO;
import com.digdes.school.project.model.ProjectParticipants;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ProjectParticipantsMapper {
    private final ModelMapper modelMapper;

    public ProjectParticipantsMapper() {
        this.modelMapper = new ModelMapper();
    }

    public ProjectParticipants convertToEntity(ProjectParticipantsDTO projectParticipantsDTO) {
        return modelMapper.map(projectParticipantsDTO, ProjectParticipants.class);
    }
}
