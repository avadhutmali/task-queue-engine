package com.avadhut.dtq;


import com.avadhut.dtq.dto.TaskRequest;
import com.avadhut.dtq.models.Task;
import com.avadhut.dtq.repository.TaskRepository;
import com.avadhut.dtq.service.TaskService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceTest {
    @Test
    void setTask_ShouldBuildTask(){
        TaskRepository mockRepo = mock(TaskRepository.class);
        TaskService taskService = new TaskService(mockRepo);

        TaskRequest request = new TaskRequest();
        request.setDescription("send_email");
        request.setQueueName("high_priority");
        request.setPayload("{\"durationMs\": 1200}");

        Task fakeTask = Task.builder()
                .id(UUID.randomUUID())
                .description("send_email")
                .queueName("high_priority")
                .payload("{\"durationMs\": 1200}")
                .status("PENDING")
                .build();

        when(mockRepo.save(any(Task.class))).thenReturn(fakeTask);
        Task result = taskService.setTask(request);

        assertEquals("send_email",result.getDescription());
        assertEquals("high_priority",result.getQueueName());
        assertEquals("PENDING", result.getStatus());

    }
}
