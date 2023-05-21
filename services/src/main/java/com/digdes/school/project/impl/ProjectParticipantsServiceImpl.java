package com.digdes.school.project.impl;

import com.digdes.school.project.ProjectParticipantsService;
import com.digdes.school.project.input.ProjectParticipantsDTO;
import com.digdes.school.project.mappers.ProjectParticipantsMapper;
import com.digdes.school.project.model.ProjectParticipants;
import com.digdes.school.project.model.id.ProjectParticipantsId;
import com.digdes.school.project.repositories.ProjectParticipantsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectParticipantsServiceImpl implements ProjectParticipantsService {
    private final ProjectParticipantsMapper mapper;
    private final ProjectParticipantsRepository repository;

    public ProjectParticipantsServiceImpl(ProjectParticipantsMapper mapper, ProjectParticipantsRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ProjectParticipants save(ProjectParticipantsDTO projectParticipantsDTO) {
        return repository.save(mapper.convertToEntity(projectParticipantsDTO));
    }

    @Override
    public boolean delete(ProjectParticipantsId id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ProjectParticipants> getAllProjectParticipants(UUID projectId) {
        return repository.getByProjectId(projectId).stream().toList();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
