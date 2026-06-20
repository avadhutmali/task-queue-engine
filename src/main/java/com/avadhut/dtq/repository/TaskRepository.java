package com.avadhut.dtq.repository;

import com.avadhut.dtq.models.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class TaskRepository  {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Task> mapper = (rs, rowNum)->{
        Task task = Task.builder()
                .id(UUID.fromString(rs.getString("id")))
                .description(rs.getString("description"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .status(rs.getString("status"))
                .queueName(rs.getString("queue_name"))
                .payload(rs.getString("payload"))
            .build();

        return task;
    };

    public Task save(Task task){
        UUID id = UUID.randomUUID();
        String sql = """
                INSERT INTO tasks (id, description,status,queue_name,payload)
                VALUES(?,?,?,?,?::jsonb)
                RETURNING created_at
                """;

        LocalDateTime create_at = jdbcTemplate.queryForObject(
            sql,
            (rs,roNum)-> rs.getTimestamp("created_at").toLocalDateTime(),
            id, task.getDescription(),task.getStatus(),task.getQueueName(),task.getPayload()
        );

        task.setId(id);
        task.setCreatedAt(create_at);

        return task;
    }

    public List<Task> findAll(){
        String sql = "SELECT * FROM tasks ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, mapper);
    }

}
