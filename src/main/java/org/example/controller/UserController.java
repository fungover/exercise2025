package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.user.User;
import org.example.dto.user.UserApi;
import org.example.dto.user.UserCheck;
import org.example.dto.user.UserNew;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  public final UserService userService;
  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/user")
  public ResponseEntity<UserNew> addUser(@Valid @RequestBody User user) {
    log.info("New user created: {} {}", user.name(), user.email());
    return ResponseEntity.status(201).body(userService.addNewUser(user));
  }

  @GetMapping("/user/api")
  public ResponseEntity<UserApi> getUserApiByEmailAndPassword(@Valid @RequestBody UserCheck user) {
    log.info("User api check: {}", user.email());
    return ResponseEntity.ok(userService.getUserApiByEmailAndPassword(user.email(), user.password()));
  }

}
