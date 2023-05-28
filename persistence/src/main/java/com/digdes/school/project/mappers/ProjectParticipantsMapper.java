package com.digdes.school.project.mappers;

import com.digdes.school.project.input.ProjectParticipantsDTO;
import com.digdes.school.project.model.Project;
import com.digdes.school.project.model.ProjectParticipants;
import com.digdes.school.project.output.ProjectParticipantsOutDTO;
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
    public ProjectParticipantsOutDTO convertToDTO(ProjectParticipants projectParticipants){
        return modelMapper.map(projectParticipants, ProjectParticipantsOutDTO.class);
    }
}
