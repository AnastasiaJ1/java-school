package com.digdes.school.project.impl;

import com.digdes.school.project.TaskService;
import com.digdes.school.project.filters.TaskSearchFilter;
import com.digdes.school.project.input.TaskDTO;
import com.digdes.school.project.mappers.TaskMapper;
import com.digdes.school.project.model.Task;
import com.digdes.school.project.enums.TaskStatus;
import com.digdes.school.project.output.TaskOutDTO;
import com.digdes.school.project.repositories.TaskRepository;
import com.digdes.school.project.specifications.TaskSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskServiceImpl(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Task create(TaskDTO taskDTO, UUID taskId) {
        Task task = mapper.convertToEntity(taskDTO);
        task.setId(UUID.randomUUID());
        task.setCreationDate(new Date(System.currentTimeMillis()));
        task.setChangeDate(new Date(System.currentTimeMillis()));
        task.setStatus(TaskStatus.NEW);
        return repository.save(task);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean update(TaskDTO taskDTO, UUID employeeId, UUID taskId) {
        Task taskPrev = repository.findById(taskId).orElse(null);

        if (taskPrev == null)
            return false;
        int changeFlag = 0;
        if (taskDTO.getName() != null
                && !taskDTO.getName().equals(taskPrev.getName())) {
            taskPrev.setName(taskDTO.getName());
            changeFlag++;
        }
        if (taskDTO.getDescription() != null
                && !taskDTO.getDescription().equals(taskPrev.getDescription())) {
            taskPrev.setDescription(taskDTO.getDescription());
            changeFlag++;
        }
        if (taskDTO.getExecutor() != null
                && !taskDTO.getExecutor().equals(taskPrev.getExecutor())) {
            taskPrev.setExecutor(taskDTO.getExecutor());
            changeFlag++;
        }
        if (taskDTO.getLaborCostsHours() != null
                && !taskDTO.getLaborCostsHours().equals(taskPrev.getLaborCostsHours())) {
            taskPrev.setLaborCostsHours(taskDTO.getLaborCostsHours());
            changeFlag++;
        }
        if (taskDTO.getDeadline() != null
                && !taskDTO.getDeadline().equals(taskPrev.getDeadline())) {
            taskPrev.setDeadline(taskDTO.getDeadline());
            changeFlag++;
        }



        if (changeFlag > 0) {
            taskPrev.setAuthor(employeeId);
            taskPrev.setChangeDate(new Date(System.currentTimeMillis()));
            repository.save(taskPrev);
        }
        return changeFlag > 0;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    @Override
    public List<TaskOutDTO> search(TaskSearchFilter searchFilter) {
        return repository.findAll(TaskSpecification.getSpec(searchFilter))
                .stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TaskOutDTO getById(UUID id) {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int updateStatus(UUID id, TaskStatus status) {
        Task task = repository.findById(id).orElse(null);
        if(task == null) return 2;
        if(task.getStatus().compareTo(status) < 0){
            task.setStatus(status);
            repository.save(task);
            return 0;
        }
        return 1;
    }
}
