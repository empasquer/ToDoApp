package com.example.todoapp.controllers;

import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;
import com.example.todoapp.services.TodoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Controller
public class HomeController {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<String> categories = todoService.getAllCategories();
        // Filter out empty or null categories
        categories = categories.stream()
                .filter(category -> category != null && !category.isEmpty())
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("todos", todoService.getTodos());
        return "home/index";
    }

    @GetMapping(value = {"/todos", "/todos/**"})
    public String getTodosByCategory(@RequestParam(required = false) String category, Model model) {
        List<Todo> todos;
        if (category != null && !category.isEmpty()) {
            // Get todos filtered by category
            todos = todoService.getTodosByCategory(category);
        } else {
            // Get all todos
            todos = todoService.getTodos();
        }

        // Add todos to the model
        model.addAttribute("todos", todos);

        // Get all categories
        List<String> categories = todoService.getAllCategories();
        // Filter out empty or null categories
        categories = categories.stream()
                .filter(cat -> cat != null && !cat.isEmpty())
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);

        return "/home/index";
    }

    @PostMapping("/addTodo")
    public String addTodo(@RequestParam String content,
                          @RequestParam(required = false) String category,
                          @RequestParam(required = false) String newCategory) {
        // Create a new todo instance
        Todo todo = new Todo();
        todo.setContent(content);

        // Determine the category
        if (newCategory != null && !newCategory.isEmpty()) {
            // If a new category is provided, use it
            todo.setCategory(newCategory);
        } else {
            // If an existing category is selected, use it
            todo.setCategory(category);
        }

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

    @PostMapping("/sort")
    public String sortNotes(Model model, HttpSession session, @RequestParam(required = false) String category,
                            @RequestParam(required = false) String sortByCompleted) {
        // Toggle sorting order based on sortByCompleted parameter
        boolean ascending = session.getAttribute("ascending") != null ? (boolean) session.getAttribute("ascending") : false;
        if (sortByCompleted != null && sortByCompleted.equals("true")) {
            ascending = !ascending;
        }
        session.setAttribute("ascending", ascending);

        // Get sorted todos based on the sorting order
        List<Todo> todos;
        if (sortByCompleted != null && sortByCompleted.equals("true")) {
            todos = todoService.getSortedTodosByCompletion(ascending);
        } else {
            todos = todoService.getSortedTodos(ascending);
        }
        model.addAttribute("todos", todos);
        model.addAttribute("ascending", ascending);

        // Get all categories
        List<String> categories = todoService.getAllCategories();
        // Filter out empty or null categories
        categories = categories.stream()
                .filter(cat -> cat != null && !cat.isEmpty())
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);

        return "home/index";
    }


}
