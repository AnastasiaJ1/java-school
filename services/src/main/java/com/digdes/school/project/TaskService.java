package com.digdes.school.project;

import com.digdes.school.project.filters.EmployeeSearchFilter;
import com.digdes.school.project.input.TaskDTO;
import com.digdes.school.project.model.Task;
import com.digdes.school.project.output.TaskOutDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task save(TaskDTO taskDTO);

    boolean change(Task task);

    boolean deleteById(UUID id);

    List<Task> search(EmployeeSearchFilter searchFilter);

    TaskOutDTO getById(UUID id);
}
