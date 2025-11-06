package org.example.services;

import org.example.config.SecurityUser;
import org.example.entities.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    /*private final SanitizationService sanitizationService;
    private final PasswordEncoder passwordEncoder;*/

    public UserService(UserRepository userRepository/*, SanitizationService sanitizationService, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1*/) {
        this.userRepository = userRepository;
        /*this.sanitizationService = sanitizationService;
        this.passwordEncoder = passwordEncoder1;*/
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
