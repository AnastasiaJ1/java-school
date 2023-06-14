package com.digdes.school.project.impl;

import com.digdes.school.project.ProjectParticipantsService;
import com.digdes.school.project.input.ProjectParticipantsDTO;
import com.digdes.school.project.mappers.ProjectParticipantsMapper;
import com.digdes.school.project.model.ProjectParticipants;
import com.digdes.school.project.model.id.ProjectParticipantsId;
import com.digdes.school.project.output.ProjectParticipantsOutDTO;
import com.digdes.school.project.repositories.EmployeeRepository;
import com.digdes.school.project.repositories.ProjectParticipantsRepository;
import com.digdes.school.project.repositories.ProjectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectParticipantsServiceImpl implements ProjectParticipantsService {
    private final ProjectParticipantsMapper mapper;
    private final ProjectParticipantsRepository repository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private static final Logger logger = LogManager.getLogger(ProjectParticipantsServiceImpl.class);

    public ProjectParticipantsServiceImpl(ProjectParticipantsMapper mapper, ProjectParticipantsRepository repository, EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectParticipants create(ProjectParticipantsDTO projectParticipantsDTO, UUID projectId) {
        logger.info("creating project participant: " + projectParticipantsDTO);
        ProjectParticipants projectParticipants = mapper.convertToEntity(projectParticipantsDTO);
        projectParticipants.setProjectId(projectId);
        if(!employeeRepository.existsById(projectParticipants.getEmployeeId())
                || !projectRepository.existsById(projectParticipants.getProjectId()))
            return null;

        return repository.save(projectParticipants);
    }

    @Override
    public boolean delete(ProjectParticipantsId id) {
        logger.info("deleting project participant");
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ProjectParticipantsOutDTO> getAllProjectParticipants(UUID projectId) {
        logger.info("get project team");
        List<ProjectParticipants> projectParticipantsList = repository.getByProjectId(projectId);
        if(projectParticipantsList == null) return null;
        return projectParticipantsList
                .stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
