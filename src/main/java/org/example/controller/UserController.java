package org.example.controller;

import org.example.dto.User;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  public final UserService userService;
  private static final Logger log = LoggerFactory.getLogger(NoteController.class);

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  public ResponseEntity<User> addUser(@RequestBody User user) {
    log.info("Received user: {}", user);
    return ResponseEntity.ok(userService.addNewUser(user));
  }
}
