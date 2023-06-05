package com.digdes.school.project.impl;

import com.digdes.school.project.TaskService;
import com.digdes.school.project.filters.TaskSearchFilter;
import com.digdes.school.project.input.TaskDTO;
import com.digdes.school.project.mappers.TaskMapper;
import com.digdes.school.project.model.Task;
import com.digdes.school.project.enums.TaskStatus;
import com.digdes.school.project.output.TaskIdOutDTO;
import com.digdes.school.project.output.TaskOutDTO;
import com.digdes.school.project.repositories.TaskRepository;
import com.digdes.school.project.specifications.TaskSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TaskIdOutDTO create(TaskDTO taskDTO, UUID taskId) {
        logger.info("creating task: " + taskDTO);
        Task task = mapper.convertToEntity(taskDTO);
        task.setId(UUID.randomUUID());
        task.setCreationDate(new Date(System.currentTimeMillis()));
        task.setChangeDate(new Date(System.currentTimeMillis()));
        task.setStatus(TaskStatus.NEW);
        return new TaskIdOutDTO(repository.save(task).getId());
    }

    @Override
    public boolean update(TaskDTO taskDTO, UUID employeeId, UUID taskId) {
        logger.info("updating task: " + taskDTO);
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
            logger.debug("task has been updated");
        } else {
            logger.debug("nothing to update");
        }
        return changeFlag > 0;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    @Override
    public List<TaskOutDTO> search(TaskSearchFilter searchFilter) {
        logger.info("searching task");
        return repository.findAll(TaskSpecification.getSpec(searchFilter))
                .stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TaskOutDTO getById(UUID id) {
        return null;
    }

    @Override
    public int updateStatus(UUID id, TaskStatus status) {
        logger.info("updating task status");
        Task task = repository.findById(id).orElse(null);
        if(task == null) {
            logger.debug("task not found");
            return 2;
        }
        if(task.getStatus().compareTo(status) < 0){
            logger.debug("status cannot be updated");
            task.setStatus(status);
            repository.save(task);
            return 0;
        }
        return 1;
    }
}
