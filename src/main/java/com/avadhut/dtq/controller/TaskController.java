package com.avadhut.dtq.controller;

import com.avadhut.dtq.dto.TaskRequest;
import com.avadhut.dtq.models.Task;
import com.avadhut.dtq.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(){
        List<Task> tasks = taskService.getAllTask();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> submitTask(@RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.setTask(taskRequest));
    }
}
