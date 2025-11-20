package org.fungover.mapper;

import org.fungover.dto.Todo;
import org.fungover.entity.TodoEntity;

public class TodoMapper {

    public static Todo todoDTO(TodoEntity todoEntity) {
        return new Todo(todoEntity.getId(), todoEntity.getTitle(), todoEntity.getDescription(), todoEntity.isCompleted(), todoEntity.getDueDate());
    }

    public static TodoEntity todoEntity(Todo todoDTO) {
        TodoEntity entity = new TodoEntity();
        entity.setId(todoDTO.id());
        entity.setTitle(todoDTO.title());
        entity.setDescription(todoDTO.description());
        entity.setCompleted(todoDTO.completed());
        entity.setDueDate(todoDTO.dueDate());
        return entity;
    }
}
