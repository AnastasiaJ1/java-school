package com.digdes.school.project.impl;

import com.digdes.school.project.ProjectService;
import com.digdes.school.project.filters.ProjectSearchFilter;
import com.digdes.school.project.input.ProjectDTO;
import com.digdes.school.project.mappers.ProjectMapper;
import com.digdes.school.project.model.Project;
import com.digdes.school.project.output.ProjectOutDTO;
import com.digdes.school.project.repositories.ProjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service

public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper mapper;
    private final ProjectRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ProjectServiceImpl(ProjectMapper mapper, ProjectRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Project save(ProjectDTO projectDTO) {
        return repository.save(mapper.convertToEntity(projectDTO));
    }

    @Override
    public boolean change(Project project) {
        Project projectPrev = repository.getById(project.getId());

        if (projectPrev == null)
            return false;
        int changeFlag = 0;
        if (project.getCode() != null
                && !project.getCode().equals(projectPrev.getCode())) {
            projectPrev.setCode(project.getCode());
            changeFlag++;
        }
        if (project.getName() != null
                && !project.getName().equals(projectPrev.getName())) {
            projectPrev.setName(project.getName());
            changeFlag++;
        }
        if (project.getDescription() != null
                && !project.getDescription().equals(projectPrev.getDescription())) {
            projectPrev.setDescription(project.getDescription());
            changeFlag++;
        }
        if (project.getStatus() != null
                && !project.getStatus().equals(projectPrev.getStatus())) {
            projectPrev.setStatus(project.getStatus());
            changeFlag++;
        }
        if (changeFlag > 0) repository.save(projectPrev);
        return changeFlag > 0;
    }

    @Override
    public boolean delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Project> search(ProjectSearchFilter searchFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = builder.createQuery(Project.class);
        Root<Project> r = query.from(Project.class);
        List<Predicate> predicate = new ArrayList<>();

        if (searchFilter.getCode() != null) {
            Predicate predicateCode = builder.equal(r.get("code"), searchFilter.getCode());
            predicate.add(predicateCode);
        }
        if (searchFilter.getName() != null) {
            Predicate predicateName = builder.equal(r.get("name"), searchFilter.getCode());
            predicate.add(predicateName);
        }
        if (searchFilter.getDescription() != null) {
            Predicate predicateDescription = builder.like(r.get("description"), "%" + searchFilter.getDescription() + "%");
            predicate.add(predicateDescription);
        }
        if (searchFilter.getStatus() != null) {
            Predicate predicateStatus = builder.equal(r.get("status"), searchFilter.getStatus());
            predicate.add(predicateStatus);
        }


        Predicate And = builder.and(predicate.toArray(new Predicate[predicate.size()]));


        query.where(And);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public ProjectOutDTO get(UUID id) {
        return mapper.convertToDTO(repository.getById(id));
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
