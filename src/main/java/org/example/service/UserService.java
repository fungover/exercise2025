package org.example.service;

import org.example.Authority;
import org.example.dto.UserDto;
import org.example.entities.CustomizedUser;
import org.example.entities.Role;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository  userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void createUser(UserDto customizedUser) {

        if(userRepository.findByUserName(customizedUser.userName()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        CustomizedUser newUser = new CustomizedUser();
        newUser.setUserName(customizedUser.userName());
        newUser.setPassword(passwordEncoder.encode(customizedUser.password()));
        Role userRole = roleRepository.findByAuthority(Authority.USER);

        if(userRole == null){
            throw new IllegalArgumentException("Role of user was not found");
        }
        newUser.getRoles().add(userRole);

        userRepository.save(newUser);
    }

}
