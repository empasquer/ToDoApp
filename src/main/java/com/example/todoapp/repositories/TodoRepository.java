package com.example.todoapp.repositories;

import com.example.todoapp.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Todo> getTodos() {
        String query = "SELECT * FROM todo";
        RowMapper rowMapper = new BeanPropertyRowMapper(Todo.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public void save(Todo todo) {
        if (findById(todo.getId()) != null) {
            // Task with the same ID exists in the database, perform an update
            String sql = "UPDATE todo SET content = ?, completed = ? WHERE id = ?";
            jdbcTemplate.update(sql, todo.getContent(), todo.isCompleted() ? 1 : 0, todo.getId());
        } else {
            // Task does not exist in the database, perform an insert
            String sql = "INSERT INTO todo (content, completed) VALUES (?, ?)";
            jdbcTemplate.update(sql, todo.getContent(), todo.isCompleted() ? 1 : 0);
        }
    }



    public Todo findById(int id) {
        String query = "SELECT * FROM todo WHERE id = ?;";
        RowMapper<Todo> rowMapper = new BeanPropertyRowMapper<>(Todo.class);
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
