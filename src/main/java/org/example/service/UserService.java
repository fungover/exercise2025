package org.example.service;

import org.example.api.dto.RegisterRequest;
import org.example.api.dto.UserView;
import org.example.domain.UserEntity;
import org.example.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository u, PasswordEncoder e) {
        this.users = u;
        this.encoder = e;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var u = users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(u.getUsername())
                .password(u.getPassword()).roles("USER").build();
    }

    @Transactional
    public UserView register(RegisterRequest req) {
        if (users.existsByUsername(req.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        var e = new UserEntity();
        e.setUsername(req.username());
        e.setPassword(encoder.encode(req.password()));
        var saved = users.save(e);
        return new UserView(saved.getId(), saved.getUsername());
    }

    public Long requireUserId(String username) {
        return users.findByUsername(username).map(UserEntity::getId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

