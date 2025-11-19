package org.example.controller;

import jakarta.validation.Valid;
import org.example.Authority;
import org.example.dto.UserDto;
import org.example.entities.CustomizedUser;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserDto customizedUser) {
        CustomizedUser newUser = new CustomizedUser();
        newUser.setUserName(customizedUser.userName());
        newUser.setPassword(passwordEncoder.encode(customizedUser.password()));
        newUser.getRoles().add(roleRepository.findByAuthority(Authority.USER));

        userRepository.save(newUser);
    }

}
