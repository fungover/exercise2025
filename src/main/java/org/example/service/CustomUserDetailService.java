package org.example.service;

import org.example.entities.CustomizedUser;
import org.example.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomizedUser customizedUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
            customizedUser.getUserName(),
            customizedUser.getPassword(),
            customizedUser.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getAuthority().name()))
                    .collect(Collectors.toSet())
        );

    }

    }