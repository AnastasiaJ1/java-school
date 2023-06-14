package com.digdes.school.project;

import com.digdes.school.project.filters.EmployeeSearchFilter;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.input.ProjectParticipantsDTO;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.model.ProjectParticipants;
import com.digdes.school.project.model.id.ProjectParticipantsId;
import com.digdes.school.project.output.EmployeeOutDTO;
import com.digdes.school.project.output.ProjectParticipantsOutDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectParticipantsService {
    ProjectParticipants create(ProjectParticipantsDTO projectParticipantsDTO, UUID projectId);

    boolean delete(ProjectParticipantsId id);


    List<ProjectParticipantsOutDTO> getAllProjectParticipants(UUID projectId);
}
