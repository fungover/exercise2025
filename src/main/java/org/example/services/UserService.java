package org.example.services;

import org.example.config.SecurityUser;
import org.example.entities.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    //default value for mustChangePassword is false
    public void createUser(String username, String rawPassword, String role) {
        createUser(username, rawPassword, role, false); //default
    }

    //if you want to initailize a user with a specific role, the password must be changed later
    public void createUser(String username, String rawPassword, String role, boolean mustChangePassword) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        user.setMustChangePassword(mustChangePassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByName(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }

    public void updatePassword(String username, String oldPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // check that old password is correct
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // check that new password matches confirm password
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password does not match confirm password");
        }
        // update password
        user.setMustChangePassword(false);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    /*@Bean
    public CommandLineRunner initAdmin(UserService userService) {
        return args -> {
            if (userService.findByName("admin") == null) {
                userService.createUser("admin", "adminpassword", "ROLE_ADMIN", true);
                System.out.println("Admin user created");
            }
        };
    }*/
}
