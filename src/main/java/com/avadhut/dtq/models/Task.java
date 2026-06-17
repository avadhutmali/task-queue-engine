package com.avadhut.dtq.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Task {
    private UUID id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
}
