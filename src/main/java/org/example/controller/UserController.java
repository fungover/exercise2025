package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.User;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  public final UserService userService;
  private static final Logger log = LoggerFactory.getLogger(NoteController.class);

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/user")
  public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
    log.info("Received user: {}", user);
    return ResponseEntity.status(201).body(userService.addNewUser(user));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<User> getUserById(@PathVariable Long userId) {
    log.info("Received request for user with id: {}", userId);
    return ResponseEntity.ok(userService.getUserById(userId));
  }

  @GetMapping("/user/notes")
  public ResponseEntity<User> getUserById(@Valid @RequestBody User user) {
    log.info("Received user: {}", user);
    return ResponseEntity.ok(userService.findUserAndGetNotes(user.email(), user.password()));
  }

}
