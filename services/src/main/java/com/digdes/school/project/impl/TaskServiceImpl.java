package com.digdes.school.project.impl;

import com.digdes.school.project.TaskService;
import com.digdes.school.project.filters.EmployeeSearchFilter;
import com.digdes.school.project.input.TaskDTO;
import com.digdes.school.project.model.Task;
import com.digdes.school.project.output.TaskOutDTO;

import java.util.List;
import java.util.UUID;

public class TaskServiceImpl implements TaskService {
    @Override
    public Task save(TaskDTO taskDTO) {
        return null;
    }

    @Override
    public boolean change(Task task) {
        return false;
    }

    @Override
    public boolean deleteById(UUID id) {
        return false;
    }

    @Override
    public List<Task> search(EmployeeSearchFilter searchFilter) {
        return null;
    }

    @Override
    public TaskOutDTO getById(UUID id) {
        return null;
    }
}
