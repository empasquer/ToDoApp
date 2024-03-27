package com.example.todoapp.services;

import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public List<Todo> getTodos() {
        return todoRepository.getTodos();
    }

    public List<String> getAllCategories() {
        return todoRepository.getAllCategories();
    }

    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public Todo findById(int id) {
        return todoRepository.findById(id);
    }

    public void deleteById(int id) {
        todoRepository.deleteById(id);
    }

    public void addTodo(Todo todo) {
        todoRepository.addTodo(todo);
    }

    public List<Todo> getTodosByCategory(String category) {
        return todoRepository.findByCategory(category);
    }

    public List<Todo> getSortedTodos(boolean ascending) {
        if (ascending) {
            // Sort todos from oldest to most recent
            return todoRepository.getTodosOrderByIdAsc();
        } else {
            // Sort todos from most recent to oldest
            return todoRepository.getTodosOrderByIdDesc();
        }
    }

    public List<Todo> getSortedTodosByCompletion(boolean ascending) {
        if (ascending) {
            // Sort todos from oldest to most recent
            return todoRepository.getTodosOrderByCompletedAsc();
        } else {
            // Sort todos from most recent to oldest
            return todoRepository.getTodosOrderByCompletedDesc();
        }
    }

    public List<Todo> getTodosByCompletion(boolean completed) {
        return todoRepository.getTodosByCompletion(completed);
    }
}
