package com.digdes.school.project;

import com.digdes.school.project.filters.TaskSearchFilter;
import com.digdes.school.project.input.TaskDTO;
import com.digdes.school.project.enums.TaskStatus;
import com.digdes.school.project.output.TaskIdOutDTO;
import com.digdes.school.project.output.TaskOutDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskIdOutDTO create(TaskDTO taskDTO, UUID taskId);

    boolean update(TaskDTO taskDTO, UUID employeeId, UUID taskId);

    boolean delete(UUID id);

    List<TaskOutDTO> search(TaskSearchFilter searchFilter);

    TaskOutDTO getById(UUID id);
    int updateStatus(UUID id, TaskStatus status);
}
