package com.digdes.school.project.mappers;

import com.digdes.school.project.input.TaskDTO;
import com.digdes.school.project.model.Task;
import com.digdes.school.project.output.TaskOutDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final ModelMapper modelMapper;

    public TaskMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Task convertToEntity(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);

        return task;
    }

    public TaskOutDTO convertToDTO(Task task) {
        return modelMapper.map(task, TaskOutDTO.class);
    }

}
