package org.fungover.service;

import org.fungover.dto.Todo;
import org.fungover.entity.TodoEntity;
import org.fungover.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo create(Todo todoDTO) {
        TodoEntity entity = TodoMapper.todoEntity(todoDTO);
        entity.setId(null);
        TodoEntity saved = todoRepository.save(entity);
        return TodoMapper.todoDTO(saved);
    }

    public List<Todo> findAll() {
        return todoRepository.findAll().stream().map(TodoMapper::todoDTO).collect(Collectors.toList());
    }

    public Todo findById(Long id) {
        TodoEntity entity = todoRepository.findById(id).orElse(null); // will change
        return TodoMapper.todoDTO(entity);
    }

    public Todo update(Long id, Todo todoDTO) {
        TodoEntity existing = todoRepository.findById(id).orElse(null); // will change

        existing.setTitle(todoDTO.title());
        existing.setDescription(todoDTO.description());
        existing.setCompleted(todoDTO.completed());
        existing.setDueDate(todoDTO.dueDate());

        TodoEntity saved = todoRepository.save(existing);
        return TodoMapper.todoDTO(saved);
    }

    public void delete(Long id) {
        if (!todoRepository.existsById(id)) {
            // will add custom exception
        }
        todoRepository.deleteById(id);
    }
}
