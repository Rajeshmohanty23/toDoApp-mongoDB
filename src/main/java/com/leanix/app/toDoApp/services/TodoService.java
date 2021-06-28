package com.leanix.app.toDoApp.services;

import com.leanix.app.toDoApp.models.TodoItem;
import com.leanix.app.toDoApp.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {
    private TodoItemRepository repository;
    private UserService userService;


    @Autowired
    TodoService(TodoItemRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public TodoItem createTodo(TodoItem todoItem, String email) {
        log.info(String.format("Adding a Todo for %s", email));

        todoItem.setOwner(getOwnerId(email));
        return repository.insert(todoItem);
    }

    public List<TodoItem> getTodos(String email) {
        log.info(String.format("Fetching Todo list for %s", email));

        return repository.findByOwner(getOwnerId(email));
    }

    public Optional<TodoItem> updateTodo(TodoItem todoItem, String email) {
        String id = todoItem.getId();
        log.info(String.format("Updating Todo item with id = %s, for %s", id, email));

        String owner = getOwnerId(email);
        TodoItem matchingItem = repository.findByIdAndOwner(id, owner);
        if (matchingItem != null) {
            todoItem.setOwner(owner);
            repository.save(todoItem);
            return Optional.of(todoItem);
        }
        return Optional.empty();
    }

    public Optional<TodoItem> deleteTodo(String id, String email) {
        log.info(String.format("Deleting Todo item with id = %s, for %s", id, email));

        TodoItem matchingItem = repository.findByIdAndOwner(id, getOwnerId(email));
        if (matchingItem != null) {
            repository.delete(matchingItem);
        }
        return Optional.ofNullable(matchingItem);
    }

    private String getOwnerId(String email) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return user.getId();
        }

        throw new RuntimeException(String.format("User not found - %s", email));
    }
}
