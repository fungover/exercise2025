package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.token.TokenRequest;
import org.example.dto.token.UpdatedToken;
import org.example.dto.user.User;
import org.example.dto.user.UserCheck;
import org.example.dto.user.UserNew;
import org.example.service.TokenService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  public final UserService userService;
  public final TokenService tokenService;
  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  public UserController(UserService userService, TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @PostMapping("/user/register")
  public ResponseEntity<UserNew> register(@Valid @RequestBody User user) {
    log.info("New user created: {} {}", user.name(), user.email());
    return ResponseEntity.status(201).body(userService.registerUser(user));
  }

  @PostMapping("/user/login")
  public ResponseEntity<TokenRequest> login(@Valid @RequestBody UserCheck user) {
    log.info("User api check: {}", user.email());
    return ResponseEntity.ok(userService.loginUser(user.email(), user.password()));
  }

  @PutMapping("/user/refresh")
  public ResponseEntity<UpdatedToken> updateToken(@Valid @RequestBody TokenRequest request) {
    log.info("Token update request received");
    return ResponseEntity.ok(tokenService.updateToken(request.token(), request.refreshToken()));
  }

}
