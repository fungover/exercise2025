package org.example.service.user;

import org.example.api.security.service.AuthenticationService;
import org.example.entities.User;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String createUser(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User user = User.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .authorities("ROLE_USER")
                .build();
        user.setApiKey(authenticationService.generateApiKey());

        userRepository.save(user);

        return "User created successfully";
    }
}
