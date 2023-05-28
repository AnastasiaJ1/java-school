package com.digdes.school.project.impl;

import com.digdes.school.project.ProjectService;
import com.digdes.school.project.filters.ProjectSearchFilter;
import com.digdes.school.project.input.ProjectDTO;
import com.digdes.school.project.mappers.ProjectMapper;
import com.digdes.school.project.model.Project;
import com.digdes.school.project.enums.ProjectStatus;
import com.digdes.school.project.output.ProjectOutDTO;
import com.digdes.school.project.repositories.ProjectRepository;
import com.digdes.school.project.specifications.ProjectSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper mapper;
    private final ProjectRepository repository;


    public ProjectServiceImpl(ProjectMapper mapper, ProjectRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Project create(ProjectDTO projectDTO) {
        Project project = mapper.convertToEntity(projectDTO);
        project.setId(UUID.randomUUID());
        project.setStatus(ProjectStatus.DRAFT);
        return repository.save(project);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean update(ProjectDTO projectDTO, UUID id) {
        Project projectPrev = repository.findById(id).orElse(null);

        if (projectPrev == null)
            return false;
        int changeFlag = 0;
        if (projectDTO.getCode() != null
                && !projectDTO.getCode().equals(projectPrev.getCode())) {
            projectPrev.setCode(projectDTO.getCode());
            changeFlag++;
        }
        if (projectDTO.getName() != null
                && !projectDTO.getName().equals(projectPrev.getName())) {
            projectPrev.setName(projectDTO.getName());
            changeFlag++;
        }
        if (projectDTO.getDescription() != null
                && !projectDTO.getDescription().equals(projectPrev.getDescription())) {
            projectPrev.setDescription(projectDTO.getDescription());
            changeFlag++;
        }

        if (changeFlag > 0) repository.save(projectPrev);
        return changeFlag > 0;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ProjectOutDTO> search(ProjectSearchFilter searchFilter) {
        return repository.findAll(ProjectSpecification.getSpec(searchFilter))
                .stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectOutDTO get(UUID id) {
        return mapper.convertToDTO(repository.findById(id).orElse(null));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int updateStatus(UUID id, ProjectStatus status) {
        Project project = repository.findById(id).orElse(null);
        if(project == null) return 2;
        if(project.getStatus().compareTo(status) < 0){
            project.setStatus(status);
            repository.save(project);
            return 0;
        }
        return 1;
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
