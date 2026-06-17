package com.avadhut.dtq.repository;

import com.avadhut.dtq.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
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
            .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
            .status(rs.getString("status"))
            .build();

        return task;
    };

    public Task save(Task task){
        UUID id = UUID.randomUUID();
        String sql = """
                INSERT INTO tasks (id, description,status)
                VALUES(?,?,?,?)
                RETURNING created_at
                """;
        jdbcTemplate.update(sql, id,task.getDescription(),task.getStatus(), task.getCreatedAt());

        LocalDateTime create_at = jdbcTemplate.queryForObject(
            sql,
            (rs,roNum)-> rs.getTimestamp("created_at").toLocalDateTime(),
            id, task.getDescription(),task.getStatus()
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
