package com.avadhut.dtq.service;

import com.avadhut.dtq.dto.TaskRequest;
import com.avadhut.dtq.models.Task;
import com.avadhut.dtq.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }
    public Task setTask(TaskRequest taskRequest){
        Task task = Task.builder()
            .description(taskRequest.getDescription())
            .status("PENDING")
            .build();
        return taskRepository.save(task);
    }

}
