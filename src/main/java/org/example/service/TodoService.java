package org.example.service;

import org.example.model.Todo;
import org.example.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return repository.findById(id);
    }

    public Todo saveTodo(Todo todo) {
        return repository.save(todo);
    }
    public void deleteTodo(Long id) {
        repository.deleteById(id);
    }
}
