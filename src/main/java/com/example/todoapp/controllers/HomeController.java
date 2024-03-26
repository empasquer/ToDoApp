package com.example.todoapp.controllers;

import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;
import com.example.todoapp.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    TodoService todoService;

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute( "todos",todoService.getTodos());
        return "/home/index";
    }

    @PostMapping("/addTodo")
    public String addTodo(@RequestParam String content, @RequestParam(required = false) String category) {
        Todo todo = new Todo();
        todo.setContent(content);
        todo.setCategory(category);
        todoService.addTodo(todo);
        return "redirect:/";
    }


    @PostMapping("/completeTodo")
    public String completeTask(@RequestParam("todo_id") int id) {
        Todo todo = todoService.findById(id);
        if (todo != null) {
            todo.setCompleted(true); // Mark task as completed
            todoService.save(todo);
        }
        return "redirect:/";
    }

    @PostMapping("/deleteTodo")
    public String deleteTodo(@RequestParam int id) {
        todoService.deleteById(id); // Delete task
        return "redirect:/";
    }

}
