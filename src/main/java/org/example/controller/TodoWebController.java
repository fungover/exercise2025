package org.example.controller;

import org.example.service.TodoService;
import org.example.model.Todo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoWebController {

    private final TodoService todoService;

    public TodoWebController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String listTodos(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        return "todos";
    }

    @PostMapping
    public String addTodo(@RequestParam String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todoService.saveTodo(todo);
        return "redirect:/todos";
    }
}
