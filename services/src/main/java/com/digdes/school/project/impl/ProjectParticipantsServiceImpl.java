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

    public ProjectParticipantsServiceImpl(ProjectParticipantsMapper mapper, ProjectParticipantsRepository repository, EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ProjectParticipants create(ProjectParticipantsDTO projectParticipantsDTO, UUID projectId) {
        ProjectParticipants projectParticipants = mapper.convertToEntity(projectParticipantsDTO);
        projectParticipants.setProjectId(projectId);
        if(!employeeRepository.existsById(projectParticipants.getEmployeeId())
                || !projectRepository.existsById(projectParticipants.getProjectId()))
            return null;

        return repository.save(projectParticipants);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean delete(ProjectParticipantsId id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ProjectParticipantsOutDTO> getAllProjectParticipants(UUID projectId) {
        List<ProjectParticipants> projectParticipantsList = repository.getByProjectId(projectId);
        if(projectParticipantsList == null) return null;
        return projectParticipantsList
                .stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
