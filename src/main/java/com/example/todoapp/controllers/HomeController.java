package com.example.todoapp.controllers;

import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;
import com.example.todoapp.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoRepository todoRepository;

    // Handler for displaying all todos
    @GetMapping("/")
    public String index(Model model) {
        // Retrieve all todos
        List<Todo> todos = todoService.getTodos();
        // Add todos to the model
        model.addAttribute("todos", todos);
        return "/home/index";
    }

    // Handler for adding a todo
    @PostMapping("/addTodo")
    public String addTodo(@RequestParam String content, @RequestParam(required = false) String category) {
        // Create a new todo instance
        Todo todo = new Todo();
        todo.setContent(content);
        todo.setCategory(category);
        // Add the todo
        todoService.addTodo(todo);
        return "redirect:/";
    }

    // Handler for marking a todo as completed
    @PostMapping("/completeTodo")
    public String completeTask(@RequestParam("todo_id") int id) {
        Todo todo = todoService.findById(id);
        if (todo != null) {
            // Mark task as completed
            todo.setCompleted(true);
            todoService.save(todo);
        }
        return "redirect:/";
    }

    // Handler for deleting a todo
    @PostMapping("/deleteTodo")
    public String deleteTodo(@RequestParam int id) {
        // Delete task
        todoService.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/todos")
    public String getTodosByCategory(@RequestParam(required = false) String category, Model model) {
        List<Todo> todos;
        if (category != null && !category.isEmpty()) {
            todos = todoService.getTodosByCategory(category);
        } else {
            todos = todoService.getTodos();
        }
        model.addAttribute("todos", todos);
        return "home/index";
    }
}
