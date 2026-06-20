package com.avadhut.dtq.dto;

import lombok.Data;

@Data
public class TaskRequest {
    private String description;
    private String queueName;
    private String payload;
}
